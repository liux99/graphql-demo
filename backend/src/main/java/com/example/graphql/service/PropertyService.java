package com.example.graphql.service;

import com.example.graphql.dto.*;
import com.example.graphql.mapper.PropertyMapper;
import com.example.graphql.model.Property;
import com.example.graphql.repository.PropertyRepository;
import com.example.graphql.controller.PropertyController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public PropertyPageResponse getProperties(int page, int size, PropertyController.PropertyFilterInput filter) {
        int pageSize = Math.max(size, 1);
        var allProperties = propertyRepository.findAll(Sort.by("createdAt").descending());

        var filtered = allProperties.stream()
                .filter(property -> filter == null || matchesFilter(property, filter))
                .toList();

        int fromIndex = Math.min(page * pageSize, filtered.size());
        int toIndex = Math.min(fromIndex + pageSize, filtered.size());
        var pagedContent = filtered.subList(fromIndex, toIndex).stream()
                .map(propertyMapper::toResponse)
                .toList();

        int totalPages = (int) Math.ceil((double) filtered.size() / pageSize);
        PageInfo pageInfo = new PageInfo(
                toIndex < filtered.size(),
                page,
                Math.max(totalPages, 1)
        );

        return new PropertyPageResponse(pagedContent, pageInfo);
    }

    private boolean matchesFilter(Property property, PropertyController.PropertyFilterInput filter) {
        boolean matchesCity = filter.city() == null || filter.city().equalsIgnoreCase(property.getCity());
        boolean matchesState = filter.state() == null || filter.state().equalsIgnoreCase(property.getState());
        boolean matchesStatus = filter.status() == null || filter.status() == property.getStatus();
        return matchesCity && matchesState && matchesStatus;
    }

    public PropertyResponse getProperty(UUID id) {
        return propertyRepository.findById(id)
                .map(propertyMapper::toResponse)
                .orElse(null);
    }

    public PropertyResponse createProperty(PropertyInput input) {
        Property entity = propertyMapper.toEntity(input);
        Property saved = propertyRepository.save(entity);
        return propertyMapper.toResponse(saved);
    }

    public PropertyResponse updateProperty(PropertyPatchInput patch) {
        Property entity = propertyRepository.findById(patch.getId())
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        propertyMapper.applyPatch(entity, patch);

        Property saved = propertyRepository.save(entity);
        return propertyMapper.toResponse(saved);
    }

    public boolean deleteProperty(UUID id) {
        if (!propertyRepository.existsById(id)) {
            return false;
        }
        propertyRepository.deleteById(id);
        return true;
    }
}
