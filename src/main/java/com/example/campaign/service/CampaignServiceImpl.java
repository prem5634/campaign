package com.example.campaign.service;

import com.example.campaign.model.Campaign;
import com.example.campaign.repository.CampaignRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CampaignServiceImpl implements CampaignService {
    @Autowired
    CampaignRepository campaignRepository;

    @Override
    public Campaign create(Campaign request){
        try {
            boolean validCampaign = checkCampaign(request);
            if (!validCampaign){
                throw new RuntimeException("Invalid Campaign");
            }
            //TODO need to complete
            saveCampaign(request);
            log.info("saved campaign : {}", request);

            return request;
        }catch (Exception e){
            log.info("Saving campaign failed");
            log.error(e.getClass()+": "+e.getMessage());
            throw  e;
        }
    }

    @Override
    public Campaign getCampaign(String id){
        try {
            Campaign campaign = campaignRepository.findById(id).get();
            if(campaign==null){
                log.info("No campaign found");
            }
            return campaign;
        }catch (Exception e){
            log.error("Saving campaign failed");
           throw e;
        }
    }

    @Override
    public boolean evaluateCampaign(String campaignId){
        try {
            Campaign campaign = getCampaign(campaignId);
            if(campaign!=null){
                return campaign.checkRules();
            }else {
                throw new RuntimeException("No campaign id found with campaignId"+ campaignId);
            }
        }catch (Exception e){
            log.error("evaluate campaign failed");
            throw e;
        }
    }


    private boolean checkCampaign(Campaign campaign){
        if(campaign==null){
            return false;
        }
        if(campaign.getName()==null || campaign.getName().isBlank()){
            return false;
        }
        if(campaign.getDescription()==null || campaign.getDescription().isBlank()){
            return false;
        }
        if(campaign.getCommunicationList()==null || campaign.getCommunicationList().isEmpty()){
            return false;
        }
        return campaign.checkRules();
    }

    //TODO need to implement in memory Database and solve errors
    private Campaign saveCampaign(Campaign campaign){

        return campaignRepository.save(campaign);
    }
}
