package com.example.campaign.service;


import com.example.campaign.model.Campaign;

public interface CampaignService {

    Campaign create(Campaign request);

    Campaign getCampaign(String id);

    boolean evaluateCampaign(String campaignId);
}
