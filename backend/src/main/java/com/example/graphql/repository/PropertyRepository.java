package com.example.graphql.repository;

import com.example.graphql.model.Property;
import com.example.graphql.model.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    @Query("SELECT p FROM Property p WHERE (:city IS NULL OR LOWER(p.city) = LOWER(:city)) " +
            "AND (:state IS NULL OR LOWER(p.state) = LOWER(:state)) " +
            "AND (:status IS NULL OR p.status = :status)")
    Page<Property> findByFilter(@Param("city") String city,
                                @Param("state") String state,
                                @Param("status") PropertyStatus status,
                                Pageable pageable);
}
