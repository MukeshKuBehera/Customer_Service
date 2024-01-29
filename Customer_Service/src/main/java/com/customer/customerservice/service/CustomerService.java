package com.customer.customerservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.customer.customerservice.dto.Customer;

@Service
public class CustomerService {
	
	// Assume a list of customers (in-memory storage for simplicity)
		private List<Customer> customers = new ArrayList<>();
		
		public CustomerService(List<Customer> customers) {
	        this.customers = customers;
	    }


	public Customer createCustomer(Customer customer) {
		// Logic to save the customer in the database
		// (You would typically use a repository or an ORM framework for this)
		// For simplicity, let's assume we're just returning the customer for this
		// example
		return customer;
	}

	

	/*
	 * public CustomerService() { // Initialize with some sample data
	 * customers.add(new Customer(1L, "John Doe", "123 Main St",
	 * "john.doe@example.com")); customers.add(new Customer(2L, "Jane Smith",
	 * "456 Oak St", "jane.smith@example.com","")); }
	 */

	public Customer getCustomerDetails(Long customerId) {
		// Logic to retrieve customer details from the in-memory storage (or a database)
		// For simplicity, let's filter the customer by ID (assuming customer IDs are
		// unique)
		return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst().orElse(null);
	}

	public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
		// Logic to update customer information in the in-memory storage (or a database)
		// For simplicity, let's assume we're updating based on the customer ID
		for (Customer customer : customers) {
			if (customer.getId().equals(customerId)) {
				// Update the customer information
				customer.setName(updatedCustomer.getName());
				customer.setAddress(updatedCustomer.getAddress());
				customer.setContactDetails(updatedCustomer.getContactDetails());

				// Return the updated customer
				return customer;
			}
		}

		// If the customer is not found, return null (or throw an exception)
		return null;
	}

	public boolean deleteCustomer(Long customerId) {
		// Logic to delete customer from the in-memory storage (or a database)
		// For simplicity, let's assume we're deleting based on the customer ID
		for (Customer customer : customers) {
			if (customer.getId().equals(customerId)) {
				// Remove the customer from the list
				customers.remove(customer);
				return true;
			}
		}

		// If the customer is not found, return false (or throw an exception)
		return false;
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
        String uniqueCustomerId = generateUniqueCustomerId();

        // Assign the unique identifier to the customer
        customer.setCustomerId(uniqueCustomerId);
        customer.setActive(true); // Newly registered accounts are active by default

        // Save the customer to the in-memory storage (or a database)
        customers.add(customer);

        // Return the registered customer
        return customer;
    }
	private boolean isDuplicateEmail(String email) {
        // Check if a customer with the same email already exists
        return customers.stream()
                .anyMatch(c -> c.getContactDetails().equalsIgnoreCase(email) && c.isActive());
    }


	private boolean isValidCustomer(Customer customer) {
        // Implement your validation logic here
        // For simplicity, let's assume all fields are required
        return customer != null &&
                customer.getName() != null &&
                !customer.getName().isEmpty() &&
                customer.getAddress() != null &&
                !customer.getAddress().isEmpty() &&
                customer.getContactDetails() != null &&
                !customer.getContactDetails().isEmpty();
    }

    private String generateUniqueCustomerId() {
        // Generate a unique customer ID using UUID (you can use a different strategy)
        return UUID.randomUUID().toString();
    }

    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        // Find the customer by ID
        Customer existingCustomer = findCustomerById(customerId);

        // Validate customer information
        if (!isValidCustomer(updatedCustomer)) {
            // You can throw an exception or return an error response
            throw new IllegalArgumentException("Invalid customer information");
        }

        // Update customer information
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setContactDetails(updatedCustomer.getContactDetails());

        // Save the updated customer to the in-memory storage (or a database)
        // (In a real-world scenario, you might use a database update operation)
        return existingCustomer;
    }
    
    
    private Customer findCustomerById(String customerId) {
        // Find the customer by ID in the in-memory storage (or a database)
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }


    public boolean deactivateCustomer(String customerId) {
        // Find the customer by ID
        Customer existingCustomer = findCustomerById(customerId);

        // Deactivate the customer account
        existingCustomer.setActive(false);
        existingCustomer.setDeactivationTimestamp(LocalDateTime.now());

        // In a real-world scenario, you might want to perform additional cleanup or archiving actions

        return true;
    }


}
