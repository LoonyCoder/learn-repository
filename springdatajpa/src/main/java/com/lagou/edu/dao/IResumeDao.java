package com.lagou.edu.dao;


import com.lagou.edu.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 一个符合spring data jpa要求的dao层接口需要继承JpaRepository和JpaSpecificationExecutor
 * JpaRepository<操作的实体类类型,主键类型>
 *     封装了基本的crud操作
 * JpaSpecificationExecutor<操作的实体类类型>
 *     封装了复杂的查询（分页，排序等）
 *
 */
public interface IResumeDao extends JpaRepository<Resume,Long> , JpaSpecificationExecutor<Resume> {

    @Query("from Resume where  id = ?1")
    public Resume findByJpql(Long id);

    @Query("from Resume where  id = ?1 and name = ?2")
    public Resume findByJpql2(Long id,String name);

    @Query(value = "select * from tb_resume where name like ?1 and address like ?2",nativeQuery = true)
    //nativeQuery标识为true使用原生sql语句,默认是false
    public Resume findByJpql3(String name,String address);


    /**
     * 方法命名规则查询
     * 按照name模糊查询（like）
     * 方法名以findBy开头
     *      - 属性名（首字母大写）
     *      - 查询方式（模糊查询、等价查询等）,如果不写查询方式，默认是等价查询
     *
     * 多字段用And连接
     * 其中addresss默认为等价查询，因为后面没有标识like
     * public List<Resume> findByNameLikeAndAddress(String name,String addresss);
     *
     * 当然，类似于deleteBy...   countBy... 等等都支持。
     */

    public List<Resume> findByNameLike(String name);




}
