package com.example.campaign;

import com.example.campaign.model.Campaign;
import com.example.campaign.model.Channel;
import com.example.campaign.model.Communication;
import com.example.campaign.repository.CampaignRepository;
import com.example.campaign.repository.CommunicationRepository;
import com.example.campaign.service.CampaignService;
import com.example.campaign.service.CampaignServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TestService {

    @InjectMocks
    CampaignServiceImpl campaignService;

    @Mock
    CampaignRepository campaignRepository;

    @Mock
    CommunicationRepository communicationRepository;

    @Test
    void getAllCampaigns(){
        Campaign campaign = new Campaign();
        campaign.setName("test campaign");
        campaign.setDescription("test description");


        List<Communication> communicationList = new ArrayList<>();

        Communication comm1 = new Communication();
        comm1.setRank("1");
        comm1.setChannel(Channel.EMAIL);
        comm1.setMessage("test1");
        comm1.setSubject("sub testing");
        comm1.setCommunicationId("A");
        comm1.setCampaign(campaign);
        communicationList.add(comm1);

        Communication comm2 = new Communication();
        comm2.setRank("2");
        comm2.setChannel(Channel.SMS);
        comm2.setMessage("test2");
        comm2.setCommunicationId("B");
        comm2.setCampaign(campaign);
        communicationList.add(comm2);

        Communication comm3 = new Communication();
        comm3.setRank("3");
        comm3.setChannel(Channel.SMS);
        comm3.setMessage("test3");
        comm3.setCommunicationId("D");
        comm3.setCampaign(campaign);
        communicationList.add(comm3);

        Communication comm4 = new Communication();
        comm4.setRank("4");
        comm4.setChannel(Channel.SMS);
        comm4.setMessage("test4");
        comm4.setCommunicationId("C");
        comm4.setCampaign(campaign);
        communicationList.add(comm4);

        campaign.setCommunicationList(communicationList);

//        Mockito.when(campaignRepository.findByName("test campaign")).thenReturn(campaign);

        when(campaignRepository.getById("cbd62330-b2c0-48fb-9835-0adb3f944b5a")).thenReturn(campaign);
        boolean result = campaignService.evaluateCampaign("cbd62330-b2c0-48fb-9835-0adb3f944b5a");

        assertTrue(result);

    }
}
