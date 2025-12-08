package com.example.graphql.dto;

import com.example.graphql.model.PropertyStatus;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class PropertyInput {
    private UUID ownerId;
    private String propertyName;
    private String city;
    private String state;
    private String postalCode;
    private Integer bedroomsTotal;
    private Float bathroomsTotal;
    private Integer livingArea;
    private Float lotSizeAcres;
    private Integer yearBuilt;
    private PropertyStatus status;
    private Map<String, Object> features;
    private String[] amenities;
}
