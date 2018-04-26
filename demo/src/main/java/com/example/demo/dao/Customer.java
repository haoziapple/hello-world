package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-04 14:05
 */
//@Entity
@AllArgsConstructor
@Data
@ToString
@Document(collection = "t_customer")
public class Customer {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String firstName;
    private String lastName;
    private Date birth;
    @CreatedDate
    private Date createDate;

    private Long timeStamp;
    private int age;

    protected  Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
