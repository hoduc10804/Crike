package com.othree.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.othree.dao.ImageNFTDao;
import com.othree.entity.ImageNFT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PayController {
    @Autowired
    private ImageNFTDao dao;

    @GetMapping("/thanhtoan")
    public String showPayPage(Model model, @RequestParam("id") Integer id) {
        ImageNFT nft = dao.findById(id).orElse(null);
        model.addAttribute("nft", nft);
        return "Othree/thanhtoan";
    }

    @PostMapping("/pay/submit")
    @ResponseBody
    public Map<String, String> handlePayment(@RequestParam("id") Integer id, @RequestParam("recipient") String keyWallet) {
        Map<String, String> response = new HashMap<>();
        ImageNFT nft = dao.findById(id).orElse(null);
        if (nft != null) {
            nft.setKeyWallet(keyWallet);
            dao.save(nft);
            response.put("message", "Payment successful!");
        } else {
            response.put("message", "Payment failed. NFT not found!");
        }
        return response;
    }
}
