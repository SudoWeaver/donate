package com.example.donationtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Donation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String donorName;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String message;
    
    // Default constructor required by JPA
    public Donation() {
        this.timestamp = LocalDateTime.now();
    }
    
    public Donation(String donorName, BigDecimal amount, String message) {
        this.donorName = donorName;
        this.amount = amount;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDonorName() {
        return donorName;
    }
    
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", donorName='" + donorName + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
