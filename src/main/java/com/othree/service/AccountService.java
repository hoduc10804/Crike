package com.othree.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.othree.dao.AccountDao;
import com.othree.dao.AuthoritiesDao;
import com.othree.dao.RoleDao;
import com.othree.entity.Account;
import com.othree.entity.Authorities;
import com.othree.entity.Role;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountDao accountRepository;

    @Autowired
    private RoleDao roleRepository;

    @Autowired
    private AuthoritiesDao authoritiesRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }

    public Account findByUsernameAndPassword(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional
    public Account registerNewAccount(Account account) {
        try {
            logger.info("Saving account for username: {}", account.getUsername());
            Account savedAccount = accountRepository.save(account);

            logger.info("Assigning default role to new account");
            Role userRole = roleRepository.findByRolename("USER");
            Authorities authority = new Authorities();
            authority.setAccount(savedAccount);
            authority.setRole(userRole);
            authoritiesRepository.save(authority);

            logger.info("Account registered successfully");
            return savedAccount;
        } catch (Exception e) {
            logger.error("Error registering new account: {}", e.getMessage(), e);
            throw e;
        }
    }
}
