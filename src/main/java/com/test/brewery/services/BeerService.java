

package com.test.brewery.services;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.test.brewery.web.model.BeerDto;
import com.test.brewery.web.model.BeerPagedList;
import com.test.brewery.web.model.BeerStyleEnum;


public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);

    BeerDto findBeerById(UUID beerId);
}
