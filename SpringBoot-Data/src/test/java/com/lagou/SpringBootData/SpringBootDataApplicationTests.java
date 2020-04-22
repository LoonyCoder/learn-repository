package com.lagou.SpringBootData;

import com.lagou.mapper.ArticleMapper;
import com.lagou.mapper.CommentMapper;
import com.lagou.pojo.Address;
import com.lagou.pojo.Article;
import com.lagou.pojo.Comment;
import com.lagou.pojo.Person;
import com.lagou.repository.CommentRepository;
import com.lagou.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootDataApplicationTests {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private ArticleMapper articleMapper;


	//测试注解方式整合mybatis
	@Test
	void contextLoads() {
		Comment comment = commentMapper.findById(1);
		System.out.println(comment);
	}

	//测试xml方式整合mybatis
	@Test
	void selectArticle() {
		Article article = articleMapper.selectArticle(1);
		System.out.println(article);
	}




	//测试整合jpa
	@Autowired
	private CommentRepository commentRepository;
	@Test
	void jpaTest() {
		List<Comment> list = commentRepository.findAll();
		for (Comment comment : list) {
			System.out.println(comment);
		}
	}


	//测试整合redis
	@Autowired
	private PersonRepository personRepository;
	@Test
	void savePersonTest(){
		Person person = new Person();
		person.setFirstName("张");
		person.setLastName("三");
		Address address = new Address();
		address.setCity("北京");
		address.setCountry("中国");
		person.setAddress(address);

		//向redis数据库中添加了数据
		personRepository.save(person);
	}

	@Test
	void selectPerson(){
		List<Person> list = personRepository.findByAddressCity("北京");
		for (Person person : list) {
			System.out.println(person);
		}
	}
}
