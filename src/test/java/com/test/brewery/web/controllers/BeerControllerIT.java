package com.test.brewery.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.test.brewery.web.model.BeerPagedList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testListBeer() {
        BeerPagedList beers = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        assertThat(beers.getContent()).hasSize(3);
    }
}
