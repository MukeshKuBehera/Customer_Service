package com.customer.customerservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
	
	private Long id;
    private String customerId; // Unique identifier
    private String name;
    private String address;
    private String contactDetails;
    private boolean active; // Indicates whether the account is active
    private LocalDateTime deactivationTimestamp; // Timestamp of account deactivation


}
