package com.example.graphql.mapper;

import com.example.graphql.dto.PropertyInput;
import com.example.graphql.dto.PropertyPatchInput;
import com.example.graphql.dto.PropertyResponse;
import com.example.graphql.model.Property;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PropertyMapper {

    public Property toEntity(PropertyInput input) {
        if (input == null) return null;

        return Property.builder()
                .ownerId(input.getOwnerId())
                .propertyName(input.getPropertyName())
                .city(input.getCity())
                .state(input.getState())
                .postalCode(input.getPostalCode())
                .bedroomsTotal(input.getBedroomsTotal())
                .bathroomsTotal(input.getBathroomsTotal())
                .livingArea(input.getLivingArea())
                .lotSizeAcres(input.getLotSizeAcres())
                .yearBuilt(input.getYearBuilt())
                .status(input.getStatus())
                .features(input.getFeatures() != null ? input.getFeatures() : Map.of())
                .amenities(input.getAmenities())
                .build();
    }

    public void applyPatch(Property property, PropertyPatchInput patch) {
        if (patch.getPropertyName() != null) property.setPropertyName(patch.getPropertyName());
        if (patch.getCity() != null) property.setCity(patch.getCity());
        if (patch.getState() != null) property.setState(patch.getState());
        if (patch.getPostalCode() != null) property.setPostalCode(patch.getPostalCode());
        if (patch.getBedroomsTotal() != null) property.setBedroomsTotal(patch.getBedroomsTotal());
        if (patch.getBathroomsTotal() != null) property.setBathroomsTotal(patch.getBathroomsTotal());
        if (patch.getLivingArea() != null) property.setLivingArea(patch.getLivingArea());
        if (patch.getLotSizeAcres() != null) property.setLotSizeAcres(patch.getLotSizeAcres());
        if (patch.getYearBuilt() != null) property.setYearBuilt(patch.getYearBuilt());
        if (patch.getStatus() != null) property.setStatus(patch.getStatus());
        if (patch.getFeatures() != null) property.setFeatures(patch.getFeatures());
        if (patch.getAmenities() != null) property.setAmenities(patch.getAmenities());
    }

    public PropertyResponse toResponse(Property entity) {
        if (entity == null) return null;

        return PropertyResponse.builder()
                .id(entity.getId())
                .ownerId(entity.getOwnerId())
                .propertyName(entity.getPropertyName())
                .unparsedAddress(entity.getUnparsedAddress())
                .city(entity.getCity())
                .state(entity.getState())
                .postalCode(entity.getPostalCode())
                .bedroomsTotal(entity.getBedroomsTotal())
                .bathroomsTotal(entity.getBathroomsTotal())
                .livingArea(entity.getLivingArea())
                .lotSizeAcres(entity.getLotSizeAcres())
                .yearBuilt(entity.getYearBuilt())
                .standardStatus(entity.getStatus())
                .features(entity.getFeatures() != null ? entity.getFeatures() : Map.of())
                .amenities(entity.getAmenities())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
