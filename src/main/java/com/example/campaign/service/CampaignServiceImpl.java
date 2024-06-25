package com.example.campaign.service;

import com.example.campaign.model.Campaign;
import com.example.campaign.model.Channel;
import com.example.campaign.model.Communication;
import com.example.campaign.repository.CampaignRepository;
import com.example.campaign.repository.CommunicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CampaignServiceImpl implements CampaignService {
    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    CommunicationRepository communicationRepository;

    @Override
    public Campaign create(Campaign request){
        try {
            boolean validCampaign = validateCampaign(request);
            if (!validCampaign){
                throw new RuntimeException("Invalid Campaign");
            }
            //TODO need to complete
            request = saveCampaign(request);
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
            Campaign campaign = campaignRepository.getById(id);
            if(campaign==null){
                log.info("No campaign found");
            }
            return campaign;
        }catch (Exception e){
            log.error("get campaign failed");
           throw e;
        }
    }

    @Override
    public boolean evaluateCampaign(String campaignId){
        try {
            Campaign campaign = getCampaign(campaignId);
            if(campaign!=null){
                return validateCampaign(campaign);
            }else {
                throw new RuntimeException("No campaign id found with campaignId"+ campaignId);
            }
        }catch (Exception e){
            log.error("evaluate campaign failed");
            throw e;
        }
    }


    private boolean validateCampaign(Campaign campaign){
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
        boolean validCampaign = campaign.checkRules();
        boolean validComms = validateComms(campaign);

        return validComms && validCampaign;
    }

    private boolean validateComms(Campaign campaign){
        boolean validComms = true;
        for (Communication communication: campaign.getCommunicationList()){
            if(communication.getChannel().equals(Channel.SMS)){
                validComms = communication.validateSMS();
            }else if(communication.getChannel().equals(Channel.EMAIL)){
                validComms = communication.validateEMAIL();
            }

            if (!validComms){
                break;
            }
        }
        return validComms;
    }

    private Campaign saveCampaign(Campaign campaign){
        for (Communication comm: campaign.getCommunicationList()
             ) {
            comm.setCampaign(campaign);
        }
        return campaignRepository.save(campaign);
    }
}
