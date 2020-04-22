import com.lagou.edu.dao.IResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {

    //要测试ioc中哪个对象直接注入接口
    @Autowired
    private IResumeDao resumeDao;


    /**
     * dao层接口调用分成两块：
     *      1.基础的增删改查
     *      2.针对查询详细分析使用
     */
    @Test
    public void testFindById(){
        //早期版本：dao.findOne(id);
        /**
         * 打印sql:
         * Hibernate: select resume0_.id as id1_0_0_,
         * resume0_.address as address2_0_0_,
         * resume0_.name as name3_0_0_,
         * resume0_.phone as phone4_0_0_
         * from tb_resume resume0_ where resume0_.id=?
         */
        Optional<Resume> optional = resumeDao.findById(1L);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testFindOne(){
        Resume resume = new Resume();
        resume.setId(1L);
        resume.setName("张三");
        Example<Resume> example = Example.of(resume);
        Optional<Resume> optional = resumeDao.findOne(example);
        Resume resume1 = optional.get();
        System.out.println(resume1);
    }


    @Test
    public void testSave(){
        //新增和更新都使用save方法，通过主键有无类区分
        //没有主键就是新增，有主键就是更新
        Resume resume = new Resume();
        resume.setId(4L);
        resume.setName("loonycoder");
        resume.setAddress("望京");
        resume.setPhone("1399999999");
        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }


    @Test
    public void testDelete(){
        resumeDao.deleteById(4L);
    }


    @Test
    public void testFindAll(){
        List<Resume> all = resumeDao.findAll();
        for (Resume resume : all) {
            System.out.println(resume);
        }
    }


    /*
     * ====================针对查询的使用进行分析====================
     * 方式一：调用继承的接口中的方法 findOne()、findById()等
     * 方式二：可以引入jpql（jpa查询语言）语句进行查询，类似于sql，只不过sql操作的是表数据，jpql操作的是对象和属性，比如 from Resume where id = xxx
     * 方式三：可以引入原生的sql语句
     * 方式四：可以在接口中自定义方法，而且不必引入sql或者jpql语句，这种方式叫做方法命名规则查询，
     *        也就是说定义的接口方法名是按照一定规则形成的，那么框架就能够理解我们的意图
     *
     * 方式五：动态查询
     *        service层传入dao层的条件不确定，把service拿到的条件封装成一个对象传递给dao层，这个对象就叫做specification（对条件的一个封装）
     *
     *           //根据条件查询单个对象
     *           Optional<T> findOne(@Nullable Specification<T> var1);
     *           //根据条件查询所有
     *           List<T> findAll(@Nullable Specification<T> var1);
     *           //根据条件查询并分页
     *           Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
     *           //根据条件查询并排序
     *           List<T> findAll(@Nullable Specification<T> var1, Sort var2);
     *           //根据条件统计
     *           long count(@Nullable Specification<T> var1);
     *
     *           interface Specification<T>
     *               toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);
     *                  Root:根属性（查询所需要的任何属性都可以从根对象中获取）
     *                  CriteriaQuery:自定义查询方式 一般用不上
     *                  CriteriaBuilder:查询构造器 封装了很多的查询条件（like = 等等）
     */


    @Test
    public void testJpql(){
        Resume byJpql = resumeDao.findByJpql(1L);
        System.out.println(byJpql);
        Resume resume = resumeDao.findByJpql2(1L, "张三");
        System.out.println(resume);


    }


    @Test
    public void testSql(){
        Resume resume = resumeDao.findByJpql3("张%","北%");
        System.out.println(resume);
    }


    @Test
    public void testMethodName(){
        List<Resume> resumes = resumeDao.findByNameLike("张%");
        for (Resume resume : resumes) {
            System.out.println(resume);
        }

    }



    //动态查询，查询单个对象
    @Test
    public void testSpecification(){
        /**
         * 动态条件封装
         * 匿名内部类
         *
         * toPredicate：动态组装查询条件
         *
         *      借助于两个参数完成条件拼装：select + from tb_resume where name = '张三'
         *      Root:获取需要查询的对象属性
         *      CriteriaBuilder:构建查询条件，内部封装了很多查询条件（模糊查询，精准查询）
         *      要求：根据name（"张三"）查询简历
         */

        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //获取到name属性
                Path<Object> name = root.get("name");
                //使用CriteriaBuilder针对name属性构建条件（精准查询）
                Predicate predicate = criteriaBuilder.equal(name, "张三");

                return predicate;
            }
        };
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }


    /**
     * 多条件动态查询
     * 要求：根据name（"张三"）,address以"北"开头查询简历
     */

    @Test
    public void testSpecificationMultiCon(){

        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //获取到name属性
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");
                //条件1：使用CriteriaBuilder针对name属性构建条件（精准查询）

                Predicate predicate1 = criteriaBuilder.equal(name, "张三");

                //条件2：address以"北"开头

                Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北%");
                Predicate predicate = criteriaBuilder.and(predicate1, predicate2);
                return predicate;
            }
        };
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }


    /**
     * 排序查询
     */
    @Test
    public void testFindBySort(){
        //倒序
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Resume> resumes = resumeDao.findAll(sort);
        for (Resume resume : resumes) {
            System.out.println(resume);
        }
    }


    /**
     * 分页查询
     */
    @Test
    public void testFindByPage(){
        //参数：1.当前页码（0开始），2.每页显示数量
//        Pageable pageable = new PageRequest(0,2);
        Pageable pageable = PageRequest.of(0,2);
        Page<Resume> all = resumeDao.findAll(pageable);
        System.out.println(all);
    }








}
