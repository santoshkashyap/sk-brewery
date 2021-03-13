
package com.test.brewery.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.brewery.domain.Beer;
import com.test.brewery.domain.BeerInventory;


public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    List<BeerInventory> findAllByBeer(Beer beer);
}
