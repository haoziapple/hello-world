package com.example.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-04 14:18
 */
//public interface CustomerRepository extends CrudRepository<Customer, Long>{
public interface CustomerRepository extends MongoRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

}
