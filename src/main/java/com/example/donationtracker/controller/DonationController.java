package com.example.donationtracker.controller;

import com.example.donationtracker.model.Block;
import com.example.donationtracker.model.Donation;
import com.example.donationtracker.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DonationController {
    
    private final BlockchainService blockchainService;
    
    @Autowired
    public DonationController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("donation", new Donation());
        return "index";
    }
    
    @PostMapping("/donate")
    public String addDonation(@ModelAttribute Donation donation, Model model) {
        Block newBlock = blockchainService.addBlock(donation);
        model.addAttribute("block", newBlock);
        return "redirect:/blockchain";
    }
    
    @GetMapping("/blockchain")
    public String viewBlockchain(Model model) {
        List<Block> blockchain = blockchainService.getBlockchain();
        boolean isValid = blockchainService.isBlockchainValid();
        
        model.addAttribute("blockchain", blockchain);
        model.addAttribute("isValid", isValid);
        
        return "blockchain";
    }
    
    @GetMapping("/donations")
    public String viewDonations(Model model) {
        List<Block> blockchain = blockchainService.getBlockchain();
        model.addAttribute("blocks", blockchain);
        return "donations";
    }
    
    @GetMapping("/rehash")
    public String rehashBlockchain() {
        blockchainService.rehashBlockchain();
        return "redirect:/blockchain";
    }
}
