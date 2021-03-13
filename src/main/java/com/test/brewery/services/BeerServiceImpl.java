

package com.test.brewery.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.test.brewery.domain.Beer;
import com.test.brewery.repositories.BeerRepository;
import com.test.brewery.web.mappers.BeerMapper;
import com.test.brewery.web.model.BeerDto;
import com.test.brewery.web.model.BeerPagedList;
import com.test.brewery.web.model.BeerStyleEnum;


@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle,
        PageRequest pageRequest) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository
                .findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
            .getContent()
            .stream()
            .map(beerMapper::beerToBeerDto)
            .collect(Collectors.toList()),
            PageRequest
                .of(beerPage.getPageable().getPageNumber(),
                    beerPage.getPageable().getPageSize()),
            beerPage.getTotalElements());
        return beerPagedList;
    }

    @Override
    public BeerDto findBeerById(UUID beerId) {
        Optional<com.test.brewery.domain.Beer> beerOptional = beerRepository
            .findById(beerId);

        if (beerOptional.isPresent()) {
            return beerMapper.beerToBeerDto(beerOptional.get());
        } else {
            //todo add error handling
            throw new RuntimeException("Not Found");
        }
    }
}
