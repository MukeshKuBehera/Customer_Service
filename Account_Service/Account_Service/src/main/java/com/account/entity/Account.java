package com.account.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private String accountNumber;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "customerId") // Name of the foreign key column in the Account table
    private Customer customer;

    
}
