package com.example.campaign.Controller;

import com.example.campaign.model.Campaign;
import com.example.campaign.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class Test {
    @GetMapping("/test")
    public String test(){
        return "service is up";
    }

    @Autowired
    CampaignService campaignService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Campaign request){

        try {
            if(request!=null ){
                Campaign campaign = campaignService.create(request);
                return ResponseEntity.ok(campaign);
//                return ResponseEntity.ok("Campaign "+campaign.getCampaignId()+ " created successfully");
            }else {
                throw new RuntimeException("Invalid Request Object: object cannot be null");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get/{campaignId}")
    public ResponseEntity<?> get(@PathVariable String campaignId){

        try {
            if(campaignId!=null && !campaignId.isBlank() ){
                Campaign campaign = campaignService.getCampaign(campaignId);
                return ResponseEntity.ok(campaign);
            }else {
                throw new RuntimeException("Invalid campaignId");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/evaluate/{campaignId}")
    public ResponseEntity<?> evaluate(@PathVariable String campaignId){

        try {
            if(campaignId!=null && !campaignId.isBlank()){
                boolean campaignEval = campaignService.evaluateCampaign(campaignId);
                if(campaignEval){
                    return ResponseEntity.ok("com/example/campaign " + campaignId+" has passed check");
                }else {
                    return ResponseEntity.ok("com/example/campaign " + campaignId+" has failed check");
                }
            }else {
                throw new RuntimeException("Invalid campaignId");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
