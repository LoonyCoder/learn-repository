package com.lagou.springboot01_demo;

import com.lagou.controller.HelloController;
import com.lagou.pojo.Myproperties;
import com.lagou.pojo.Person;
import com.lagou.pojo.SimpleBean;
import com.lagou.pojo.Student;
import com.lagou.service.MyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)  //测试启动器，并加载Spring Boot测试注解
@SpringBootTest  //标记为SpringBoot单元测试类，并加载项目的ApplicationContext的上下文环境
class Springboot01DemoApplicationTests {

	@Autowired
	private HelloController helloController;

	@Autowired
	private Person person;

	@Autowired
	private Student student;

	@Autowired
	private Myproperties myproperties;

	@Autowired
	private MyService myService;

	@Test
	void contextLoads() {
		String demo = helloController.demo();
		System.out.println(demo);
	}


	//测试yml文件属性注入
	@Test
	void testPerson(){
		System.out.println(person);
	}

	//测试@Value注解属性注入
	@Test
	void testStudent(){
		System.out.println(student);
	}

	//测试自定义配置文件
	@Autowired
	private ApplicationContext applicationContext;
	@Test
	void testCustomProperties(){
		System.out.println(applicationContext.containsBean("iService"));
	}


	//测试自定义配置类
	@Test
	void testCustomConfigClass(){
		System.out.println(myproperties);
	}

	//测试参数间引用

	@Value("${tom.description}")
	private String description;
	@Test
	void testArgsReference(){
		System.out.println(description);
	}



	//测试自定义starter

	@Autowired
	private SimpleBean simpleBean;
	@Test
	void testCustomStarter(){
		System.out.println(simpleBean);
	}

}
