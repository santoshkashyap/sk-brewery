package com.test.brewery.web.controllers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.test.brewery.services.BeerService;
import com.test.brewery.web.model.BeerDto;
import com.test.brewery.web.model.BeerPagedList;
import com.test.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    BeerDto beerDto;

    private static final UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder().beerName("Mango bobs")
            .beerStyle(BeerStyleEnum.ALE)
            .id(uuid)
            .price(new BigDecimal("12.99"))
            .quantityOnHand(4)
            .version(1)
            .upc(342786910L)
            .createdDate(OffsetDateTime.now())
            .lastModifiedDate(OffsetDateTime.now())
            .build();

    }

    @AfterEach
    void tearDown() {
        reset(beerService);
    }

    @Test
    void testBeerById() throws Exception {
        when(beerService.findBeerById(any())).thenReturn(beerDto);

        mockMvc.perform(get("/api/v1/beer/{beerId}", beerDto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(beerDto.getId().toString())))
            .andExpect(jsonPath("$.beerName", is("Mango bobs")));

    }

    @DisplayName("List Ops - ")
    @Nested
    public class TestListOperations {

        @Captor
        ArgumentCaptor<String> beerNameCaptor;

        @Captor
        ArgumentCaptor<BeerStyleEnum> beerStyleEnumCaptor;

        @Captor
        ArgumentCaptor<PageRequest> pageRequestCaptor;

        BeerPagedList beerPagedList;

        @BeforeEach
        void setUp() {
            List<BeerDto> beers = new ArrayList<>();
            beers.add(beerDto);
            beers.add(BeerDto.builder().id(UUID.randomUUID())
                .version(1)
                .beerName("Beer4")
                .upc(123123123122L)
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityOnHand(66)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build());

            beerPagedList = new BeerPagedList(beers, PageRequest.of(1, 1), 2L);

            when(beerService.listBeers(beerNameCaptor.capture(), beerStyleEnumCaptor.capture(),
                pageRequestCaptor.capture())).thenReturn(beerPagedList);
        }

        @DisplayName("Test list of beers - no parameters")
        @Test
        void testListBeers() throws Exception {
            mockMvc.perform(get("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].beerName", is("Mango bobs")));
        }

    }

}