package com.example.graphql.web.dto;

public record PageInfo(boolean hasNextPage, int currentPage, int totalPages) {
}
