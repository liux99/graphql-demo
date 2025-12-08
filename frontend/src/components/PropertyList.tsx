import React from "react";
import { useQuery } from "@apollo/client";
import { GET_PROPERTIES } from "../graphql/queries";

type Property = {
  id: string;
  propertyName: string;
  city: string;
  state: string;
  postalCode: string;
  bedroomsTotal?: number;
  bathroomsTotal?: number;
  standardStatus: string;
};

type PageInfo = {
  hasNextPage: boolean;
  currentPage: number;
  totalPages: number;
};

type PropertiesData = {
  properties: {
    content: Property[];
    pageInfo: PageInfo;
  };
};

type PropertiesVars = {
  page: number;
  size: number;
  status?: string;
};

export const PropertyList: React.FC = () => {
  const { data, loading, error } = useQuery<PropertiesData, PropertiesVars>(GET_PROPERTIES, {
    variables: { page: 0, size: 20 }
  });

  if (loading) return <p>Loading properties...</p>;
  if (error) return <p>Error: {error.message}</p>;

  const properties = data?.properties.content ?? [];

  return (
    <div>
      <h2>Properties</h2>
      {properties.length === 0 && <p>No properties yet.</p>}
      <ul>
          {properties.map((p) => (
            <li key={p.id}>
              <strong>{p.propertyName}</strong> — {p.city}, {p.state}{" "}
              ({p.standardStatus})
            </li>
          ))}
      </ul>
      <div style={{ marginTop: 8 }}>
        Page {(data?.properties.pageInfo.currentPage ?? 0) + 1} of{" "}
        {data?.properties.pageInfo.totalPages ?? 1}
      </div>
    </div>
  );
};
