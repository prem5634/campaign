package com.example.campaign.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Campaign {
    //In any campaign, there should be campaignId, name, description etc

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 64)
    private String id = UUID.randomUUID().toString();

    @Column
    private String name;

    @Column
    private String description;

    //Taking List than Set as no uniques communicationCampaign is mentioned
    @OneToMany(mappedBy = "corporateMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Communication> communicationList;

    public boolean checkRules(){

    //A needs to be sent before B and C
    //C can only be sent after A and B.

    //-> A, B, C come in order and D can be anywhere

        boolean a = false,b=false,c =false;
        if(communicationList == null || communicationList.isEmpty()){
            return false;
        }
        for (Communication comm: communicationList
        ) {
            switch (comm.getCommunicationId()) {
                case "A" -> {
                    a = true;
                    break;
                }
                case "B" -> {
                    if (a) {
                        b = true;
                    }
                    break ;
                }
                case "C" -> {
                    if (a && b) {
                        c = true;
                    }
                    break ;
                }
                case "D" ->{
                    break;
                }
            }
        }
        return a&&b&&c;
    }

}
