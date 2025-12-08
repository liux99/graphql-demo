package com.example.graphql.controller;

import com.example.graphql.dto.PropertyInput;
import com.example.graphql.dto.PropertyPageResponse;
import com.example.graphql.dto.PropertyPatchInput;
import com.example.graphql.dto.PropertyResponse;
import com.example.graphql.model.PropertyStatus;
import com.example.graphql.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @QueryMapping
    public PropertyPageResponse properties(@Argument int page,
                                           @Argument int size,
                                           @Argument(name = "filter") PropertyFilterInput filter) {
        return propertyService.getProperties(page, size, filter);
    }

    @QueryMapping
    public PropertyResponse property(@Argument UUID id) {
        return propertyService.getProperty(id);
    }

    @MutationMapping
    public PropertyResponse createProperty(@Argument PropertyInput input) {
        return propertyService.createProperty(input);
    }

    @MutationMapping
    public PropertyResponse updateProperty(@Argument PropertyPatchInput input) {
        return propertyService.updateProperty(input);
    }

    @MutationMapping
    public Boolean deleteProperty(@Argument UUID id) {
        return propertyService.deleteProperty(id);
    }

    // local record to map filter input
    public record PropertyFilterInput(String city, String state, PropertyStatus status) {}
}
