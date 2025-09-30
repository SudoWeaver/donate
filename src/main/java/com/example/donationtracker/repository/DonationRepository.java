package com.example.donationtracker.repository;

import com.example.donationtracker.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    // Custom query methods can be added here if needed
}
