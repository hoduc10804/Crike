package com.othree.entity;



import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionsId;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionsDate = new Date();

    private String transactionsdetails;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private Account account;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private Set<TransactionsDetail> transactionsDetails;

    // Getters and Setters
}
