package com.othree.controller;

import com.othree.entity.ImageNFT;
import com.othree.service.ImageNFTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageNFTController {

    @Autowired
    private ImageNFTService imageNFTService;

    @GetMapping
    public String images(Model model) {
        List<ImageNFT> imageNFTs = imageNFTService.findAll();
        model.addAttribute("imageNFTs", imageNFTs);
        model.addAttribute("imageNFT", new ImageNFT()); // For creating a new image NFT
        return "Othree/images";
    }

    @PostMapping("/create")
    public String createImageNFT(@ModelAttribute("imageNFT") ImageNFT imageNFT) {
        imageNFTService.save(imageNFT);
        return "redirect:/images";
    }

    @PostMapping("/update")
    public String updateImageNFT(@ModelAttribute("imageNFT") ImageNFT imageNFT) {
        try {
            if (imageNFTService.findById(imageNFT.getNftId()).isPresent()) {
                imageNFTService.save(imageNFT);
            }
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
        return "redirect:/images";
    }

    @GetMapping("/edit/{id}")
    public String editImageNFT(@PathVariable("id") Integer nftId, Model model) {
        ImageNFT imageNFT = imageNFTService.findById(nftId).orElse(null);
        if (imageNFT == null) {
            return "redirect:/images"; // Handle not found
        }
        model.addAttribute("imageNFT", imageNFT);
        List<ImageNFT> imageNFTs = imageNFTService.findAll();
        model.addAttribute("imageNFTs", imageNFTs);
        return "Othree/images";
    }

    @PostMapping("/delete/{id}")
    public String deleteImageNFT(@PathVariable("id") Integer nftId) {
        imageNFTService.deleteById(nftId);
        return "redirect:/images";
    }
}
