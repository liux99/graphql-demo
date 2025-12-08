package com.example.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfo {
    private boolean hasNextPage;
    private int currentPage;
    private int totalPages;
}
