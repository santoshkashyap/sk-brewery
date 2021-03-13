

package com.test.brewery.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.test.brewery.domain.BeerOrderLine;


public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID> {
}
