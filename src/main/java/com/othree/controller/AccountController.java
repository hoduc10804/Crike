package com.othree.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.othree.service.AccountService;
import com.othree.entity.Account;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String accounts(Model model) {
        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", new Account()); // For creating a new account
        return "Othree/accounts"; // Make sure this path is correct
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("account") Account account) {
        accountService.save(account);
        return "redirect:/accounts";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") Account account) {
        try {
            if (accountService.findById(account.getAccountId()).isPresent()) {
                accountService.save(account);
            }
        } catch (Exception e) {
            System.out.println("Update failed");
        }
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String editAccount(@PathVariable("id") Integer accountId, Model model) {
        Account account = accountService.findById(accountId).orElse(null);
        if (account == null) {
            // Handle account not found case
            return "redirect:/accounts"; // or a 404 page
        }
        model.addAttribute("account", account);
        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        return "Othree/accounts"; // Make sure this path is correct
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer accountId) {
        accountService.deleteById(accountId);
        return "redirect:/accounts";
    }

    @PostMapping("/reset")
    public String reset(Model model) {
        return "redirect:/accounts";
    }
}

