package com.example.graphql.service;

import com.example.graphql.model.Property;
import com.example.graphql.model.PropertyStatus;
import com.example.graphql.repository.PropertyRepository;
import com.example.graphql.web.dto.PropertyFilter;
import com.example.graphql.web.dto.PropertyInput;
import com.example.graphql.web.dto.PropertyPatchInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository repository;

    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

    public Page<Property> getProperties(int page, int size, PropertyFilter filter) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByFilter(filter != null ? filter.city() : null,
                filter != null ? filter.state() : null,
                filter != null ? filter.status() : null,
                pageable);
    }

    public Optional<Property> getProperty(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Property createProperty(PropertyInput input) {
        Property property = new Property();
        applyInput(property, input);
        property.setStatus(Optional.ofNullable(input.status()).orElse(PropertyStatus.AVAILABLE));
        return repository.save(property);
    }

    @Transactional
    public Property updateProperty(PropertyPatchInput input) {
        Property property = repository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        if (input.propertyName() != null) property.setPropertyName(input.propertyName());
        if (input.city() != null) property.setCity(input.city());
        if (input.state() != null) property.setState(input.state());
        if (input.postalCode() != null) property.setPostalCode(input.postalCode());
        if (input.bedroomsTotal() != null) property.setBedroomsTotal(input.bedroomsTotal());
        if (input.bathroomsTotal() != null) property.setBathroomsTotal(input.bathroomsTotal());
        if (input.livingArea() != null) property.setLivingArea(input.livingArea());
        if (input.lotSizeAcres() != null) property.setLotSizeAcres(input.lotSizeAcres());
        if (input.yearBuilt() != null) property.setYearBuilt(input.yearBuilt());
        if (input.status() != null) property.setStatus(input.status());
        if (input.amenities() != null) property.setAmenities(input.amenities());
        if (input.features() != null) property.setFeatures(input.features());

        return repository.save(property);
    }

    @Transactional
    public boolean deleteProperty(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    private void applyInput(Property property, PropertyInput input) {
        property.setOwnerId(input.ownerId());
        property.setPropertyName(input.propertyName());
        property.setCity(input.city());
        property.setState(input.state());
        property.setPostalCode(input.postalCode());
        property.setBedroomsTotal(input.bedroomsTotal());
        property.setBathroomsTotal(input.bathroomsTotal());
        property.setLivingArea(input.livingArea());
        property.setLotSizeAcres(input.lotSizeAcres());
        property.setYearBuilt(input.yearBuilt());
        property.setAmenities(input.amenities());
        property.setFeatures(input.features());
    }
}
