package com.example.graphql.web.dto;

import com.example.graphql.model.Property;
import java.util.List;

public record PropertyPage(List<Property> content, PageInfo pageInfo) {
}
