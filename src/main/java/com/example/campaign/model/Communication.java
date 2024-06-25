package com.example.campaign.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Communication {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 64)
    private String id;

    private String rank;

    private String communicationId;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private String message;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Override
    public String toString() {
        return "Communication{" +
                "id='" + id + '\'' +
                ", rank='" + rank + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", channel=" + channel +
                ", message='" + message + '\'' +
                ", campaign=" + campaign.getId() +
                '}';
    }



    public boolean validateSMS(){
        if (channel.equals(Channel.SMS)){
            return message!= null && !message.isBlank();
        }
        else
            return false;
    }

    public boolean validateEMAIL(){
        if (channel.equals(Channel.EMAIL)){
            return  subject != null && !subject.isBlank() && message != null && !message.isBlank();
        }
        else
            return false;
    }
}
