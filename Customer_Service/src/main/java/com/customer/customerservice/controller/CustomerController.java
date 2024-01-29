package com.customer.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customerservice.dto.Customer;
import com.customer.customerservice.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		try {
			Customer createdCustomer = customerService.createCustomer(customer);
			return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
		} catch (Exception e) {
			// Handle exceptions and return appropriate error messages
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Object> getCustomerDetails(@PathVariable Long customerId) {
		try {
			Customer customerDetails = customerService.getCustomerDetails(customerId);

			if (customerDetails != null) {
				return new ResponseEntity<>(customerDetails, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
		try {
			Customer customer = customerService.updateCustomer(customerId, updatedCustomer);

			if (customer != null) {
				return new ResponseEntity<>(customer, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId) {
		try {
			boolean deleted = customerService.deleteCustomer(customerId);

			if (deleted) {
				return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@Secured("ROLE_ADMIN")
	@PostMapping("/register")
    public ResponseEntity<Object> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer registeredCustomer = customerService.registerCustomer(customer);
            return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	// @Secured("ROLE_USER")
	@PutMapping("/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable String customerId,
			@RequestBody Customer updatedCustomer) {
		try {
			Customer updated = customerService.updateCustomer(customerId, updatedCustomer);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//@Secured("ROLE_ADMIN")
	 @DeleteMapping("/{customerId}")
	    public ResponseEntity<Object> deactivateCustomer(@PathVariable String customerId) {
	        try {
	            boolean deactivated = customerService.deactivateCustomer(customerId);

	            if (deactivated) {
	                return new ResponseEntity<>("Customer deactivated successfully", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
	            }
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}