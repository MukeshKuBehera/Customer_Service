package com.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.entity.Customer;
import com.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	private List<Customer> customers = new ArrayList<>();

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer getCustomerDetails(String customerId) {
		return findCustomerById(customerId);
	}

	public Customer updateCustomer(String customerId, Customer updatedCustomer) {
		Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findByCustomerId(customerId))
				.or(() -> customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst());

		return customerOptional.map(existingCustomer -> {
			existingCustomer.setName(updatedCustomer.getName());
			existingCustomer.setAddress(updatedCustomer.getAddress());
			existingCustomer.setContactDetails(updatedCustomer.getContactDetails());

			return customerRepository.save(existingCustomer);
		}).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

	public boolean deleteCustomer(String customerId) {

		for (Customer customer : customers) {
			if (customer.getCustomerId().equals(customerId)) {
				customers.remove(customer);
				return true;
			}
		}

		throw new IllegalArgumentException("Invalid customer information");
	}

	public Customer registerCustomer(Customer customer) {
		if (!isValidCustomer(customer)) {
			throw new IllegalArgumentException("Invalid customer information");
		}

		if (isDuplicateEmail(customer.getEmail())) {
			throw new IllegalArgumentException("Customer with the same email already exists");
		}

		customer.setCustomerId(CustomerService.generateCustomerId());
		customer.setActive(true);

		customers.add(customer);

		customerRepository.saveAll(customers);

		return customer;
	}

	private boolean isDuplicateEmail(String email) {
		boolean isDuplicateInMemory = customers.stream()
				.anyMatch(c -> c.getEmail().equalsIgnoreCase(email) && c.isActive());

		if (!isDuplicateInMemory) {

			isDuplicateInMemory = customerRepository.existsByEmailAndActiveInDatabase(email);

		}

		return isDuplicateInMemory;
	}

	private boolean isValidCustomer(Customer customer) {
		return customer != null && customer.getName() != null && !customer.getName().isEmpty()
				&& customer.getAddress() != null && !customer.getAddress().isEmpty()
				&& customer.getContactDetails() != null && !customer.getContactDetails().isEmpty()
				&& customer.getEmail() != null && !customer.getEmail().isEmpty();
	}

	public static String generateCustomerId() {
		String randomUUID = UUID.randomUUID().toString();

		String customerId = "CUST-" + randomUUID.substring(0, 8);

		return customerId;
	}

	private Customer findCustomerById(String customerId) {

		Optional<Customer> inMemoryResult = customers.stream().filter(c -> c.getCustomerId().equals(customerId))
				.findFirst();

		if (inMemoryResult.isPresent()) {
			return inMemoryResult.get();
		} else {

			Optional<Customer> databaseResult = Optional.ofNullable(customerRepository.findByCustomerId(customerId));

			return databaseResult.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
		}
	}

	public boolean deactivateCustomer(String customerId) {
		Customer existingCustomer = findCustomerById(customerId);

		existingCustomer.setActive(false);
		existingCustomer.setDeactivationTimestamp(LocalDateTime.now());
		customerRepository.save(existingCustomer);
		return true;
	}

}
