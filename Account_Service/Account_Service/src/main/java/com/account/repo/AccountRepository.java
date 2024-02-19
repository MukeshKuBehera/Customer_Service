package com.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.entity.Account;
import com.account.entity.Customer;

public interface AccountRepository extends JpaRepository<Account,String>{
	
	@Query("SELECT c FROM Customer c WHERE c.customerId = :customerId")
	Customer findByCustomerId(@Param("customerId") String customerId);

}
