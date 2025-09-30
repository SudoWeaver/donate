package com.example.donationtracker.service;

import com.example.donationtracker.model.Block;
import com.example.donationtracker.model.Donation;
import com.example.donationtracker.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlockchainService {
    
    private final BlockRepository blockRepository;
    
    @Autowired
    public BlockchainService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
        
        // Check if blockchain needs to be initialized with genesis block
        if (blockRepository.count() == 0) {
            createGenesisBlock();
        }
    }
    
    @Transactional
    private void createGenesisBlock() {
        Donation genesisDonation = new Donation("Genesis", java.math.BigDecimal.ZERO, "Genesis Block");
        
        Block genesisBlock = new Block(0, genesisDonation, "0");
        blockRepository.save(genesisBlock);
    }
    
    @Transactional
    public Block addBlock(Donation donation) {
        // Get the previous block
        Block previousBlock = blockRepository.findFirstByOrderByIndexDesc()
            .orElseThrow(() -> new RuntimeException("Blockchain not initialized with genesis block"));
        
        // Create a new block with the donation
        Block newBlock = new Block(previousBlock.getIndex() + 1, donation, previousBlock.getHash());
        
        // Save and return the new block (donation will be saved automatically due to cascade)
        return blockRepository.save(newBlock);
    }
    
    public List<Block> getBlockchain() {
        return blockRepository.findAll();
    }
    
    public boolean isBlockchainValid() {
        List<Block> blocks = blockRepository.findAll();
        
        for (int i = 1; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);
            
            // Check if current block's hash is valid
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            
            // Check if current block's previous hash matches the previous block's hash
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        
        return true;
    }
    
    public Optional<Block> getBlockByIndex(int index) {
        return blockRepository.findByIndex(index);
    }
}
