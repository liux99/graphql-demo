import React from "react";
import { PropertyForm } from "./components/PropertyForm";
import { PropertyList } from "./components/PropertyList";

export const App: React.FC = () => {
  return (
    <div style={{ padding: 24 }}>
      <h1>GraphQL Property Demo</h1>
      <PropertyForm />
      <PropertyList />
    </div>
  );
};
