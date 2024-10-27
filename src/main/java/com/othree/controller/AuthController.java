package com.othree.controller;

import com.othree.entity.Account;
import com.othree.entity.Customer;
import com.othree.service.AccountService;
import com.othree.service.CustomerService;
import com.othree.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("fullname") String fullname,
            @RequestParam("phone") String phone,
            @RequestParam("walletKey") String walletKey) {

        logger.info("Register endpoint hit");

        try {
            logger.info("Registering user with username: {}", username);

            // Create Account object
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);

            // Create Customer object
            Customer customer = new Customer();
            customer.setEmail(email);
            customer.setFullname(fullname);
            customer.setPhone(phone);
            customer.setWalletkey(walletKey);

            // Link Customer with Account
            account.setCustomer(customer);
            customer.setAccount(account);

            accountService.registerNewAccount(account);
            logger.info("User registered successfully");
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
