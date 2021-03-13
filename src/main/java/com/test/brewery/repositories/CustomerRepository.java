
package com.test.brewery.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.brewery.domain.Customer;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
