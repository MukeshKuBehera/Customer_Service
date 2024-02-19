package com.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE c.customerId = :customerId")
	Customer findByCustomerId(@Param("customerId") String customerId);

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c "
			+ "WHERE LOWER(c.email) = LOWER(:email) AND (:active IS NULL OR c.active = :active)")
	boolean existsByContactDetailsIgnoreCaseAndActive(@Param("email") String email, @Param("active") Boolean active);

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c "
			+ "WHERE LOWER(c.email) = LOWER(:email) AND c.active = true")
	boolean existsByEmailAndActiveInDatabase(@Param("email") String email);

}
