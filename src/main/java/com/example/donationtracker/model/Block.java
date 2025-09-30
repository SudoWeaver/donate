package com.example.donationtracker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Entity
public class Block {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int index;
    private LocalDateTime timestamp;
    private String previousHash;
    private String hash;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Donation donation;
    
    // Default constructor required by JPA
    public Block() {
        this.timestamp = LocalDateTime.now();
    }
    
    public Block(int index, Donation donation, String previousHash) {
        this.index = index;
        this.donation = donation;
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now();
        this.hash = calculateHash();
    }
    
    // Calculate the hash of this block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Create a deterministic string representation of the donation that doesn't include the ID
            // (since the ID is assigned by the database and changes the hash)
            String donationData = donation.getDonorName() + "|" + donation.getAmount() + "|" + donation.getMessage();
            String dataToHash = index + donationData + previousHash + timestamp.toString();
            byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getPreviousHash() {
        return previousHash;
    }
    
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
    
    public String getHash() {
        return hash;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public Donation getDonation() {
        return donation;
    }
    
    public void setDonation(Donation donation) {
        this.donation = donation;
    }
}
