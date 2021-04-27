package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerNumber;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, CustomerNumber> {

    @Aggregation({"{$match: {}}", "{$project: {value: \"$_id\", _id:0}}"})
    List<CustomerNumber> findCustomerNumbers();
}
