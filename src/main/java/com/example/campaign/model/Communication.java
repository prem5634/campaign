package com.example.campaign.model;

import com.example.campaign.model.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Communication {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 64)
    private String id = UUID.randomUUID().toString();

    private String rank;

    private String communicationId;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private String message;
}
