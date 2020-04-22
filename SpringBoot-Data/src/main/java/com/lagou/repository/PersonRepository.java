package com.lagou.repository;

import com.lagou.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person,Integer> {



    //根据城市信息查询对应的人
    List<Person> findByAddressCity(String name);

}
