import { gql } from "@apollo/client";

export const GET_PROPERTIES = gql`
  query GetProperties($page: Int, $size: Int, $status: PropertyStatus) {
    properties(page: $page, size: $size, filter: { status: $status }) {
      pageInfo {
        hasNextPage
        currentPage
        totalPages
      }
      content {
        id
        ownerId
        propertyName
        city
        state
        postalCode
        bedroomsTotal
        bathroomsTotal
        status
      }
    }
  }
`;

export const GET_PROPERTY = gql`
  query GetProperty($id: ID!) {
    property(id: $id) {
      id
      propertyName
      city
      state
      postalCode
      bedroomsTotal
      bathroomsTotal
      status
      description
    }
  }
`;
