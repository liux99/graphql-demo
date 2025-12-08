package com.example.graphql.service;

import com.example.graphql.dto.*;
import com.example.graphql.mapper.PropertyMapper;
import com.example.graphql.model.Property;
import com.example.graphql.model.PropertyStatus;
import com.example.graphql.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public PropertyPageResponse getProperties(int page, int size, PropertyStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Property> entityPage;

        if (status != null) {
            entityPage = propertyRepository.findByStatus(status, pageable);
        } else {
            entityPage = propertyRepository.findAll(pageable);
        }

        var content = entityPage.getContent().stream()
                .map(propertyMapper::toResponse)
                .toList();

        PageInfo pageInfo = new PageInfo(
                entityPage.hasNext(),
                entityPage.getNumber(),
                entityPage.getTotalPages()
        );

        return new PropertyPageResponse(content, pageInfo);
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
