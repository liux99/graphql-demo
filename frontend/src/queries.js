import { gql } from '@apollo/client';

export const GET_PROPERTIES = gql`
  query Properties($page: Int, $size: Int, $filter: PropertyFilter) {
    properties(page: $page, size: $size, filter: $filter) {
      pageInfo {
        hasNextPage
        currentPage
        totalPages
      }
      content {
        id
        propertyName
        city
        state
        postalCode
        bedroomsTotal
        bathroomsTotal
        livingArea
        amenities
        status
      }
    }
  }
`;

export const CREATE_PROPERTY = gql`
  mutation CreateProperty($input: PropertyInput!) {
    createProperty(input: $input) {
      id
      propertyName
      status
    }
  }
`;

export const UPDATE_PROPERTY = gql`
  mutation UpdateProperty($input: PropertyPatchInput!) {
    updateProperty(input: $input) {
      id
      status
    }
  }
`;

export const DELETE_PROPERTY = gql`
  mutation DeleteProperty($id: ID!) {
    deleteProperty(id: $id)
  }
`;
