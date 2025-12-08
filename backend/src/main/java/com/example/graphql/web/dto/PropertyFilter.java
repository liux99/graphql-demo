package com.example.graphql.web.dto;

import com.example.graphql.model.PropertyStatus;

public record PropertyFilter(String city, String state, PropertyStatus status) {
}
