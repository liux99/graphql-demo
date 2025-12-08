import { gql } from "@apollo/client";

export const CREATE_PROPERTY = gql`
  mutation CreateProperty($input: PropertyInput!) {
    createProperty(input: $input) {
      id
      propertyName
      city
      state
      postalCode
      standardStatus
    }
  }
`;

export const UPDATE_PROPERTY = gql`
  mutation UpdateProperty($input: PropertyPatchInput!) {
    updateProperty(input: $input) {
      id
      propertyName
      city
      state
      postalCode
      standardStatus
    }
  }
`;

export const DELETE_PROPERTY = gql`
  mutation DeleteProperty($id: ID!) {
    deleteProperty(id: $id)
  }
`;
