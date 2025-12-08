package com.example.graphql.dto;

import com.example.graphql.model.PropertyStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class PropertyResponse {
    private UUID id;
    private UUID ownerId;
    private String propertyName;
    private String unparsedAddress;
    private String city;
    private String state;
    private String postalCode;
    private Integer bedroomsTotal;
    private Float bathroomsTotal;
    private Integer livingArea;
    private Float lotSizeAcres;
    private Integer yearBuilt;
    private PropertyStatus standardStatus;
    private Map<String, Object> features;
    private String[] amenities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
