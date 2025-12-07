package com.example.graphql.config;

import com.example.graphql.model.Property;
import com.example.graphql.model.PropertyStatus;
import com.example.graphql.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Map;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedProperties(PropertyRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            Property modernLoft = new Property();
            modernLoft.setOwnerId(1L);
            modernLoft.setPropertyName("Modern Loft");
            modernLoft.setCity("Austin");
            modernLoft.setState("TX");
            modernLoft.setPostalCode("73301");
            modernLoft.setBedroomsTotal(2);
            modernLoft.setBathroomsTotal(2.0);
            modernLoft.setLivingArea(1400);
            modernLoft.setLotSizeAcres(0.25);
            modernLoft.setYearBuilt(2018);
            modernLoft.setStatus(PropertyStatus.AVAILABLE);
            modernLoft.setAmenities(List.of("Roof Deck", "Gym"));
            modernLoft.setFeatures(Map.of("energyStar", true, "parkingSpots", 1));

            Property craftsman = new Property();
            craftsman.setOwnerId(2L);
            craftsman.setPropertyName("Craftsman Bungalow");
            craftsman.setCity("Seattle");
            craftsman.setState("WA");
            craftsman.setPostalCode("98101");
            craftsman.setBedroomsTotal(3);
            craftsman.setBathroomsTotal(1.5);
            craftsman.setLivingArea(1700);
            craftsman.setLotSizeAcres(0.18);
            craftsman.setYearBuilt(1948);
            craftsman.setStatus(PropertyStatus.LEASED);
            craftsman.setAmenities(List.of("Fireplace", "Garden"));
            craftsman.setFeatures(Map.of("walkScore", 92));

            Property lakeHouse = new Property();
            lakeHouse.setOwnerId(3L);
            lakeHouse.setPropertyName("Lake House");
            lakeHouse.setCity("Madison");
            lakeHouse.setState("WI");
            lakeHouse.setPostalCode("53703");
            lakeHouse.setBedroomsTotal(4);
            lakeHouse.setBathroomsTotal(3.5);
            lakeHouse.setLivingArea(2800);
            lakeHouse.setLotSizeAcres(1.2);
            lakeHouse.setYearBuilt(2005);
            lakeHouse.setStatus(PropertyStatus.COMING_SOON);
            lakeHouse.setAmenities(List.of("Dock", "Sauna", "Deck"));
            lakeHouse.setFeatures(Map.of("waterfront", true, "hoaFees", 350));

            repository.saveAll(List.of(modernLoft, craftsman, lakeHouse));
        };
    }
}
