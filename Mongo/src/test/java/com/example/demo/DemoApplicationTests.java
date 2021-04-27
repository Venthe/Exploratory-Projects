package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerNumber;
import com.example.demo.model.Other;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void contextLoads() {
        customerRepository.deleteAll();
        mongoOperations.dropCollection(Account.class);

        final Customer customer = createSampleCustomer();
        customerRepository.save(customer);

        Customer byId = customerRepository.findById(customer.getCustomerNumber()).orElseThrow();

        log.info("", byId);
        Assertions.assertEquals(customer.getCustomerNumber(), byId.getCustomerNumber());

        final List<CustomerNumber> collect = customerRepository.findCustomerNumbers();
        log.info("", collect);
    }

    private Customer createSampleCustomer() {
        final Customer customer = new Customer();
        customer.setCustomerNumber(new CustomerNumber("13"));
        customer.setOther(new Other("31"));
        customer.setAccounts(Stream.of(Account.create("assa")).collect(toSet()));
        return customer;
    }

}
