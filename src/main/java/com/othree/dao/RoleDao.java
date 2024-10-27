package com.othree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
	Role findByRolename(String rolename);

}
