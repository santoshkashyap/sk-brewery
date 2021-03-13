package com.test.brewery.web.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.test.brewery.domain.Customer;
import com.test.brewery.repositories.CustomerRepository;
import com.test.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CustomerRepository repository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = repository.findAll().get(0);
    }

    @Test
    void testListOrders() {
        BeerOrderPagedList pagedList = restTemplate
            .getForObject("/api/v1/customers/{customerId}/orders", BeerOrderPagedList.class,
                customer.getId());
        System.out.println(pagedList);
        assertNotNull(pagedList);
        assertThat(pagedList).hasSize(1);


    }

}
