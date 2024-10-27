package com.othree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.Transaction;

public interface TransactionDao extends JpaRepository<Transaction, Integer> {

}
