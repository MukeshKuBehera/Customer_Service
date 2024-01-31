package com.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.customer.entity.Customer;

@Service
public class CustomerService {

	// Assume a list of customers (in-memory storage for simplicity)
	private List<Customer> customers = new ArrayList<>();

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	// get customer details
	public Customer getCustomerDetails(String customerId) {

		System.out.println(customerId);
		/*
		 * return customers.stream().filter(c ->
		 * c.getCustomerId().equals(customerId)).findFirst() .orElseThrow(() -> new
		 * IllegalArgumentException("Customer not found"));
		 */
		return findCustomerById(customerId);
	}

	public Customer updateCustomer(String customerId, Customer updatedCustomer) {
		// Logic to update customer information in the in-memory storage (or a database)
		// // For simplicity, let's assume we're updating based on the
		for (Customer customer : customers) {
			if (customer.getCustomerId().equals(customerId)) {
				// Update the customer information
				customer.setName(updatedCustomer.getName());
				customer.setAddress(updatedCustomer.getAddress());
				customer.setContactDetails(updatedCustomer.getContactDetails());

				// Return the updated customer
				return customer;
			}
		}

		// If the customer is not found, return null (or throw an exception)
		throw new IllegalArgumentException("Invalid customer information");
	}

	public boolean deleteCustomer(String customerId) {
		// Logic to delete customer
		// For simplicity, let's assume we're deleting based on the customer ID
		for (Customer customer : customers) {
			if (customer.getCustomerId().equals(customerId)) {
				// Remove the customer from the list
				customers.remove(customer);
				return true;
			}
		}

		// If the customer is not found, return false (or throw an exception)
		throw new IllegalArgumentException("Invalid customer information");
	}

	public Customer registerCustomer(Customer customer) {
		// Validate customer information
		if (!isValidCustomer(customer)) {
			throw new IllegalArgumentException("Invalid customer information");
		}

		// Check for duplicate registrations (using email as an example)
		if (isDuplicateEmail(customer.getContactDetails())) {
			throw new IllegalArgumentException("Customer with the same email already exists");
		}

		// Generate a unique customer identifier
		// String uniqueCustomerId = generateCustomerId();

		// Assign the unique identifier to the customer
		customer.setCustomerId(CustomerService.generateCustomerId());
		customer.setActive(true); // Newly registered accounts are active by default

		// Save the customer to the in-memory storage (or a database)
		customers.add(customer);

		// Return the registered customer
		return customer;
	}

	private boolean isDuplicateEmail(String email) {
		// Check if a customer with the same email already exists
		return customers.stream().anyMatch(c -> c.getContactDetails().equalsIgnoreCase(email) && c.isActive());
	}

	private boolean isValidCustomer(Customer customer) {
		// Implement your validation logic here
		// For simplicity, let's assume all fields are required
		return customer != null && customer.getName() != null && !customer.getName().isEmpty()
				&& customer.getAddress() != null && !customer.getAddress().isEmpty()
				&& customer.getContactDetails() != null && !customer.getContactDetails().isEmpty()
				&& customer.getEmail() != null && !customer.getEmail().isEmpty();
	}

	public static String generateCustomerId() {
		// Generate a random UUID (Universally Unique Identifier)
		String randomUUID = UUID.randomUUID().toString();

		// Extract a portion of the UUID to use as the customer ID
		String customerId = "CUST-" + randomUUID.substring(0, 8);
		// Long customerId=Long.parseLong(cstId);
		// System.out.println(customerId);

		return customerId;
	}

	private Customer findCustomerById(String customerId) {
		// Find the customer by ID in the in-memory storage (or a database)
		return customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

	public boolean deactivateCustomer(String customerId) {
		// Find the customer id
		Customer existingCustomer = findCustomerById(customerId);

		// Deactivate the customer account existingCustomer.setActive(false);
		existingCustomer.setDeactivationTimestamp(LocalDateTime.now());

		// In a real-world scenario, you might want to perform additional cleanup or
		// archiving actions

		return true;
	}

}
