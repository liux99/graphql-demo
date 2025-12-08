package com.example.graphql.web;

import com.example.graphql.model.Property;
import com.example.graphql.service.PropertyService;
import com.example.graphql.web.dto.PageInfo;
import com.example.graphql.web.dto.PropertyFilter;
import com.example.graphql.web.dto.PropertyInput;
import com.example.graphql.web.dto.PropertyPage;
import com.example.graphql.web.dto.PropertyPatchInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Controller
@Validated
public class PropertyGraphqlController {

    private final PropertyService service;

    public PropertyGraphqlController(PropertyService service) {
        this.service = service;
    }

    @QueryMapping
    public PropertyPage properties(@Argument int page, @Argument int size, @Argument PropertyFilter filter) {
        var pageResult = service.getProperties(page, size, filter);
        var pageInfo = new PageInfo(pageResult.hasNext(), pageResult.getNumber(), pageResult.getTotalPages());
        return new PropertyPage(pageResult.getContent(), pageInfo);
    }

    @QueryMapping
    public Property property(@Argument Long id) {
        Optional<Property> property = service.getProperty(id);
        return property.orElse(null);
    }

    @MutationMapping
    public Property createProperty(@Argument PropertyInput input) {
        return service.createProperty(input);
    }

    @MutationMapping
    public Property updateProperty(@Argument PropertyPatchInput input) {
        return service.updateProperty(input);
    }

    @MutationMapping
    public boolean deleteProperty(@Argument Long id) {
        return service.deleteProperty(id);
    }
}
