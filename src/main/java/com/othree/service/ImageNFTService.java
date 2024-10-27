package com.othree.service;

import com.othree.entity.ImageNFT;
import com.othree.dao.ImageNFTDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageNFTService {

    @Autowired
    private ImageNFTDao imageNFTRepository;

    public List<ImageNFT> findAll() {
        return imageNFTRepository.findAll();
    }

    public Optional<ImageNFT> findById(Integer id) {
        return imageNFTRepository.findById(id);
    }

    public ImageNFT save(ImageNFT imageNFT) {
        return imageNFTRepository.save(imageNFT);
    }

    public void deleteById(Integer id) {
        imageNFTRepository.deleteById(id);
    }
}
