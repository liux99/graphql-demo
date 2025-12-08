 

# 🚀 GraphQL Property Demo (Spring Boot + React)

This project showcases a full-stack GraphQL application built on modern best practices.

**The Stack:**

  * **Backend:** Spring Boot 3 + Spring GraphQL + JPA (H2 for demo)
  * **Frontend:** React + TypeScript + Apollo Client
  * **Domain:** A simplified Property model (similar to a real estate platform)

It demonstrates DTO separation, schema-first design, patch mutations, pagination, type safety, and how to evolve a system away from REST without breaking clients.

-----

## 📌 1. Why GraphQL Instead of REST?

### ⭐ 1.1 Benefits

| Feature | GraphQL Approach | REST Approach |
| :--- | :--- | :--- |
| **1. Client-Driven Response Shape** | Frontend chooses only what it needs, reducing payload size. | Backend decides all fields. Requires multiple specialized endpoints. |
| **2. Fewer Network Calls** | Fetches property data, images, listings, and applications in one request. | Requires multiple sequential HTTP requests (e.g., `GET /properties`, `GET /listings`). |
| **3. Strong Typing** | Schema is the single source of truth (`.graphqls` → Java DTOs → React Types). | Prone to object mismatches (`bedroomsTotal` vs `bedrooms`). |
| **4. Easy Partial Updates (PATCH)** | Uses optional fields in `input PropertyPatchInput` for atomic, safe updates. | Requires custom backend logic to inspect fields and prevent overwriting existing data. |
| **5. Backward-Compatible Evolution**| Supports deprecating fields and adding new ones without versioning. | Often requires endpoint versioning (`/v1/properties`, `/v2/properties`). |

**Example of Client-Driven Query:**

```graphql
query {
  properties(page: 0, size: 10) {
    content {
      id
      propertyName
      city
      state
    }
  }
}
```

### ⚠️ 1.2 Challenges & Best Practices

| Challenge | Best Practice |
| :--- | :--- |
| **N+1 database queries** | Use service-layer batching, DataLoader, or join fetches. |
| **Schema bloat** | Use **domain language**, not table language (e.g., `bedroomsTotal`, not DB column names). |
| **Authorization** | Put security checks in the **service layer**, not the resolvers. |
| **Input validation** | Validate DTOs **before** mapping them to entities. |
| **Over-fetching DB fields** | Use projections or DTO-level queries to limit returned columns. |

-----

## 📁 2. Repository Structure

This is the simplified file structure for the monorepo approach:

```
realtes-graphql-demo/
├─ README.md
├─ backend/
│  ├─ pom.xml
│  ├─ src/main/java/com/example/graphql/
│  │  ├─ model/Property.java               # JPA Entity
│  │  ├─ dto/PropertyInput.java            # Backend DTOs
│  │  ├─ mapper/PropertyMapper.java        # Entity <-> DTO Conversion
│  │  ├─ service/PropertyService.java      # Business Logic (Transaction/Security)
│  │  └─ graphql/PropertyGraphQLController.java # Resolvers (Thin Layer)
│  └─ src/main/resources/graphql/schema.graphqls # 🌟 GraphQL Schema (Source of Truth)
└─ frontend/
   ├─ package.json
   ├─ src/
   │  ├─ apolloClient.ts
   │  ├─ graphql/                          # GraphQL Operations
   │  │  ├─ queries.ts
   │  │  └─ mutations.ts
   │  ├─ components/
   │  │  ├─ PropertyList.tsx               # Renders query results
   │  │  └─ PropertyForm.tsx               # Triggers mutations
   │  └─ App.tsx
```

-----

## ⚙️ 3. Running the Project

### ▶️ 3.1 Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

| Endpoint | URL | Description |
| :--- | :--- | :--- |
| **GraphQL API** | `http://localhost:8082/graphql` | The primary endpoint for all GraphQL requests. |
| **GraphiQL IDE** | `http://localhost:8082/graphiql` | Interactive testing environment for queries and mutations. |
| **H2 Console** | `http://localhost:8082/h2-console` | Database browser (if using H2). |

**Test Query:**

```graphql
query {
  properties(page: 0, size: 10) {
    content {
      id
      propertyName
      status
    }
  }
}
```

### ▶️ 3.2 Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

  * **Frontend URL:** `http://localhost:3000`
  * Displays a form (Mutation example) and a paginated list (Query example).

-----

## 🧩 4. GraphQL API Overview

### Queries

| Type | Description |
| :--- | :--- |
| **Pagination & Filtering** | `properties(page: Int, size: Int, filter: PropertyFilter)` |
| **Single Item** | `property(id: ID!)` |

**Example:**

```graphql
query {
  properties(page: 0, size: 20, filter: { status: AVAILABLE }) {
    pageInfo {
      currentPage
      totalPages
    }
    content {
      id
      propertyName
      status
    }
  }
}
```

### Mutations

| Type | Description |
| :--- | :--- |
| **Create** | `createProperty(input: PropertyInput!)` |
| **Update (PATCH)** | `updateProperty(input: PropertyPatchInput!)` |
| **Delete** | `deleteProperty(id: ID!)` |

**Example Update (PATCH):**

```graphql
mutation {
  updateProperty(input: {
    id: "PROPERTY_UUID_HERE"
    bedroomsTotal: 4
    status: LEASED
  }) {
    id
    bedroomsTotal
    status
  }
}
```

-----

## 🛠 5. Architecture Patterns & Best Practices

| Pattern | Description |
| :--- | :--- |
| **✔ 5.1 Consistent Naming** | The field name (`bedroomsTotal`) must be identical in the **Schema → DTOs → Entities → React Types**. This avoids 90% of mapping bugs. |
| **✔ 5.2 Centralized Mapping** | Use a single `PropertyMapper` class to handle `DTO ↔ Entity` conversions. This keeps resolvers and services clean. |
| **✔ 5.3 Thin Resolver Layer** | Resolvers must only delegate to the service layer. Business logic, security, and transactions belong in the service. |
| **✔ 5.4 Use Input Types for PATCH** | `PropertyPatchInput` uses optional fields to naturally support partial updates, simplifying backend code. |
| **✔ 5.5 Standard Pagination** | Use the `PropertyPage` and nested `PageInfo` types to adhere to common GraphQL standards (like Relay). |

**Pagination Example:**

```graphql
type PropertyPage {
  content: [Property!]!
  pageInfo: PageInfo!
}
type PageInfo {
  hasNextPage: Boolean!
  currentPage: Int!
  totalPages: Int!
}
```

-----

## 📘 6. Development Workflow (Recommended)

1.  **Write GraphQL Schema First**
    *(Schema defines the domain vocabulary and contract.)*
2.  **Generate/Write DTOs**
    *(DTO names and fields match the schema exactly.)*
3.  **Implement Mapper**
    *(Handles all transformation logic.)*
4.  **Implement Service Layer**
    *(Encapsulates paging, filtering, security, and business rules.)*
5.  **Implement GraphQL Resolvers**
    *(Thin controller-like layer delegates to the service.)*
6.  **Implement React UI**
    *(Apollo Client auto-generates typed responses for consumption.)*

-----

## 🎯 7. Extending Toward a Real Platform

This demo provides a solid foundation to build out a complex platform by adding:

  * Rental Listings, Applications, and Payments
  * Users / Tenants / Multi-tenant isolation
  * Presigned S3 image fetching
  * Field-level access control

GraphQL makes this evolution easier than traditional REST due to its organized schema and native support for non-breaking changes.
