package com.example.campaign.repository;

import com.example.campaign.model.Campaign;
import org.springframework.data.repository.CrudRepository;

public interface CampaignRepository extends CrudRepository<Campaign, String> {
}
