package com.othree.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.othree.entity.ImageNFT;

public interface ImageNFTDao extends JpaRepository<ImageNFT, Integer> {
	  List<ImageNFT> findAll();

}
