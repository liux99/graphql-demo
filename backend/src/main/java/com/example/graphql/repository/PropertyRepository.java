package com.example.graphql.repository;

import com.example.graphql.model.Property;
import com.example.graphql.model.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    Page<Property> findByOwnerId(UUID ownerId, Pageable pageable);

    Page<Property> findByOwnerIdAndStatus(UUID ownerId, PropertyStatus status, Pageable pageable);

    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
}
