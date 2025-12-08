import React, { useState } from "react";
import { useMutation } from "@apollo/client";
import { CREATE_PROPERTY } from "../graphql/mutations";
import { GET_PROPERTIES } from "../graphql/queries";

export const PropertyForm: React.FC = () => {
  const [propertyName, setPropertyName] = useState("");
  const [city, setCity] = useState("Plano");
  const [state, setState] = useState("TX");
  const [postalCode, setPostalCode] = useState("75025");

  const [createProperty, { loading, error }] = useMutation(CREATE_PROPERTY, {
    refetchQueries: [{ query: GET_PROPERTIES }]
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    await createProperty({
      variables: {
        input: {
          ownerId: "00000000-0000-0000-0000-000000000001", // demo
          propertyName,
          city,
          state,
          postalCode,
          status: "AVAILABLE"
        }
      }
    });

    setPropertyName("");
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 16 }}>
      <h2>Add Property</h2>
      <div>
        <label>
          Name:{" "}
          <input
            value={propertyName}
            onChange={(e) => setPropertyName(e.target.value)}
          />
        </label>
      </div>
      <div>
        <label>
          City:{" "}
          <input value={city} onChange={(e) => setCity(e.target.value)} />
        </label>
      </div>
      <div>
        <label>
          State:{" "}
          <input value={state} onChange={(e) => setState(e.target.value)} />
        </label>
      </div>
      <div>
        <label>
          Postal Code:{" "}
          <input
            value={postalCode}
            onChange={(e) => setPostalCode(e.target.value)}
          />
        </label>
      </div>
      <button type="submit" disabled={loading}>
        {loading ? "Saving..." : "Create Property"}
      </button>
      {error && <p style={{ color: "red" }}>Error: {error.message}</p>}
    </form>
  );
};
