package com.example.graphql.web.dto;

import com.example.graphql.model.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public record PropertyPatchInput(
        @NotNull Long id,
        String propertyName,
        String city,
        String state,
        String postalCode,
        Integer bedroomsTotal,
        Double bathroomsTotal,
        Integer livingArea,
        Double lotSizeAcres,
        Integer yearBuilt,
        PropertyStatus status,
        List<String> amenities,
        Map<String, Object> features
) {
}
