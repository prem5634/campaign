package com.example.campaign.repository;

import com.example.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, String> {
    Campaign findByName(String name);
}
