package com.othree.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer,Integer>{

}
