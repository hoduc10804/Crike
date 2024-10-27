package com.othree.controller;

import com.othree.entity.Customer;
import com.othree.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String customers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer()); // For creating a new customer
        return "Othree/customers";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) {
        try {
            if (customerService.findById(customer.getAccountId()).isPresent()) {
                customerService.save(customer);
            }
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Integer customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        if (customer == null) {
            return "redirect:/customers"; // Handle not found
        }
        model.addAttribute("customer", customer);
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "Othree/customers";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer customerId) {
        customerService.deleteById(customerId);
        return "redirect:/customers";
    }

    @PostMapping("/reset")
    public String reset(Model model) {
        return "redirect:/customers";
    }
}
