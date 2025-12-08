package com.example.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PropertyPageResponse {
    private List<PropertyResponse> content;
    private PageInfo pageInfo;
}
