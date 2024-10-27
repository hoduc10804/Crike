package com.othree.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.othree.dao.ImageNFTDao;
import com.othree.entity.ImageNFT;

@Controller
public class HomeController {

    @Autowired
    private ImageNFTDao dao;

    @GetMapping("/index")
    public String index() {
        return "Othree/index";
    }
    
    @GetMapping("/listnft")
    public String list() {
        return "Othree/listNFT";
    }

    @GetMapping("/contact")
    public String contact() {
        return "Othree/contact";
    }

    @GetMapping("/about")
    public String about() {
        return "Othree/about";
    }

    @GetMapping("/faq")
    public String faq() {
        return "Othree/faq";
    }

    @GetMapping("/feature")
    public String feature() {
        return "Othree/feature";
    }
    
    @GetMapping("/myNFTs")
    public String myNFTs() {
        return "Othree/myNFTs";
    }
    
    @GetMapping("/transfer")
    public String transfer() {
        return "Othree/transfer";
    }
    
    @GetMapping("/transferSOL")
    public String transferSOL() {
        return "Othree/transferSOL";
    }

    @GetMapping("/service")
    public String service() {
        return "Othree/service";
    }

    @GetMapping("/createNFT")
    public String createNFT() {
        return "Othree/createNFT";
    }

    @GetMapping("/register")
    public String register() {
        return "Othree/register";
    }

    @GetMapping("/login")
    public String login() {
        return "Othree/login";
    }

    @GetMapping("/marketplace")
    public String getNFTMarketplace(Model model) {
        List<ImageNFT> nfts = dao.findAll();
        model.addAttribute("nfts", nfts);
        return "Othree/marketplace";
    }

    @GetMapping("/productDetail/{id}")
    public String getProductDetail(@PathVariable("id") Integer id, Model model) {
        ImageNFT nft = dao.findById(id).get();
        model.addAttribute("nft", nft);
        return "Othree/productDetail";
    }
}
