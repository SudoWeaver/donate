package com.example.donationtracker.repository;

import com.example.donationtracker.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    // Find the latest block by highest index
    Optional<Block> findFirstByOrderByIndexDesc();
    
    // Find block by index
    Optional<Block> findByIndex(int index);
}
