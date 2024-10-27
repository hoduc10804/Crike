package com.othree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.Account;

public interface AccountDao extends JpaRepository<Account,Integer> {

	Account findByUsernameAndPassword(String username, String password);

	Account findByUsername(String username);

}
