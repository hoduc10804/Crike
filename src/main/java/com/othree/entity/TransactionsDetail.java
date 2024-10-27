package com.othree.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TransactionsDetail")
public class TransactionsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionsDetailId;

    @ManyToOne
    @JoinColumn(name = "transactionsId")
    private Transaction transaction;

    private String transactionsdetails;
    private String image;
    private float price;

    // Getters and Setters
}
