package com.test.brewery.web.controllers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.test.brewery.services.BeerOrderService;
import com.test.brewery.web.model.BeerOrderDto;
import com.test.brewery.web.model.BeerOrderPagedList;
import com.test.brewery.web.model.OrderStatusEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerTest {

    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    private final UUID uuid= UUID.randomUUID();

    BeerOrderPagedList beerOrderDtos;

    @BeforeEach
    void setUp() {
        BeerOrderDto beerDto1 = BeerOrderDto.builder()
            .orderStatus(OrderStatusEnum.NEW)
            .id(UUID.randomUUID())
            .customerRef("A123")
            .version(1)
            .beerOrderLines(new ArrayList<>())
            .createdDate(OffsetDateTime.now())
            .lastModifiedDate(OffsetDateTime.now())
            .build();

        BeerOrderDto beerDto2 = BeerOrderDto.builder()
            .orderStatus(OrderStatusEnum.READY)
            .id(UUID.randomUUID())
            .customerRef("A123")
            .version(1)
            .beerOrderLines(new ArrayList<>())
            .createdDate(OffsetDateTime.now())
            .lastModifiedDate(OffsetDateTime.now())
            .build();
        beerOrderDtos = new BeerOrderPagedList(Arrays.asList(beerDto1, beerDto2),
            Pageable.unpaged(),
            24L);
        when(beerOrderService.listOrders(any(), any())).thenReturn(beerOrderDtos);
        when(beerOrderService.getOrderById(any(), any())).thenReturn(beerDto2);

    }

    @AfterEach
    void tearDown() {
        reset(beerOrderService);
    }

    @Test
    void listOrders() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{customerId}/orders", uuid))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void getOrder() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{customerId}/orders/{orderId}", uuid, uuid))
            .andExpect(status().isOk());

    }
}