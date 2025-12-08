 
🚀 GraphQL Property Demo (Spring Boot + React)

This project is a full-stack GraphQL demo using:

Backend: Spring Boot 3 + Spring GraphQL + JPA (H2)

Frontend: React + TypeScript + Apollo Client

Domain: A simplified Property model (similar to the Realtes platform)

It demonstrates modern GraphQL best practices, including DTO separation, schema-first design, patch mutations, pagination, type safety, and how to evolve a real system away from REST without breaking clients.

📌 1. Why GraphQL Instead of REST?
⭐ 1.1 Benefits
1. Client-driven response shape

REST requires the backend to decide which fields are returned.
GraphQL lets the frontend choose only what it needs:

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


No need for multiple REST endpoints like:

/properties/list-view

/properties/detail-view

/properties/minimal

2. Fewer network calls

A traditional REST UI for Realtes might do:

GET /properties
GET /properties/{id}/images
GET /listings?propertyId={id}
GET /applications?propertyId={id}


GraphQL can fetch everything in one query.

3. Strong typing across frontend & backend

The GraphQL schema becomes the single source of truth:

schema.graphqls → Java DTOs → Entities → React Types


This eliminates object mismatches such as:

bedroomsTotal vs bedrooms

zip vs postalCode

status vs propertyStatus

4. Easy partial updates (PATCH)

GraphQL supports optional fields in input types:

input PropertyPatchInput {
  id: ID!
  propertyName: String
  bedroomsTotal: Int
  status: PropertyStatus
}


Backend safely applies only provided fields → avoids overwriting existing data.

5. Backward-compatible evolution

GraphQL supports:

Deprecating fields without breaking clients

Adding new fields anytime

Having multiple views of the same entity

REST often requires:

/v1/properties

/v2/properties

GraphQL needs none of that.

⚠️ 1.2 Challenges & Best Practices
Challenge	Best Practice
N+1 database queries	Use service-layer batching, DataLoader, join fetches
Schema bloat	Use domain language, not table language (e.g., bedroomsTotal, not DB column names)
Authorization	Put security in service layer, not resolvers
Input validation	Validate DTOs before mapping to entities
Over-fetching DB fields	Use projections or DTO-level queries
📁 2. Repository Structure
realtes-graphql-demo/
├─ README.md
├─ backend/
│  ├─ pom.xml
│  ├─ src/main/java/com/example/graphql/
│  │  ├─ GraphqlDemoApplication.java
│  │  ├─ model/Property.java
│  │  ├─ dto/PropertyInput.java
│  │  ├─ dto/PropertyPatchInput.java
│  │  ├─ dto/PropertyResponse.java
│  │  ├─ dto/PageInfo.java
│  │  ├─ dto/PropertyPageResponse.java
│  │  ├─ mapper/PropertyMapper.java
│  │  ├─ repository/PropertyRepository.java
│  │  ├─ service/PropertyService.java
│  │  └─ graphql/PropertyGraphQLController.java
│  └─ src/main/resources/graphql/schema.graphqls
└─ frontend/
   ├─ package.json
   ├─ src/
   │  ├─ apolloClient.ts
   │  ├─ graphql/
   │  │  ├─ queries.ts
   │  │  └─ mutations.ts
   │  ├─ components/
   │  │  ├─ PropertyList.tsx
   │  │  └─ PropertyForm.tsx
   │  ├─ App.tsx
   │  └─ main.tsx


Backend uses schema-first GraphQL.
Frontend uses Apollo Client for declarative GraphQL operations.

⚙️ 3. Running the Project
▶️ 3.1 Backend (Spring Boot)
cd backend
mvn spring-boot:run


Backend runs at:

GraphQL endpoint: http://localhost:8082/graphql

GraphiQL IDE: http://localhost:8082/graphiql

H2 console: http://localhost:8082/h2-console

Try running this query in GraphiQL:

query {
  properties(page: 0, size: 10) {
    content {
      id
      propertyName
      city
      status
    }
  }
}

▶️ 3.2 Frontend (React + Vite)
cd frontend
npm install
npm run dev


Frontend starts at:

http://localhost:3000

You will see:

A form to create new properties (GraphQL mutation)

A paginated property list (GraphQL query)

🧩 4. GraphQL API Overview
Queries
query {
  properties(page: 0, size: 20, filter: { status: AVAILABLE }) {
    pageInfo {
      currentPage
      totalPages
      hasNextPage
    }
    content {
      id
      propertyName
      city
      state
      status
    }
  }
}

query {
  property(id: "PROPERTY_UUID_HERE") {
    id
    propertyName
    city
  }
}

Mutations
Create
mutation {
  createProperty(input: {
    ownerId: "00000000-0000-0000-0000-000000000001"
    propertyName: "Test Home"
    city: "Plano"
    state: "TX"
    postalCode: "75025"
    bedroomsTotal: 3
    bathroomsTotal: 2.5
    status: AVAILABLE
  }) {
    id
    propertyName
    status
  }
}

Update (PATCH)
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

Delete
mutation {
  deleteProperty(id: "PROPERTY_UUID_HERE")
}

🛠 5. Architecture Patterns & Best Practices
✔ 5.1 Keep naming consistent across all layers

If you choose bedroomsTotal, use it:

In GraphQL schema

In DTOs

In entities

In React types

This alignment reduces 90% of mapping bugs.

✔ 5.2 Centralize mapping into a Mapper class

PropertyMapper handles:

DTO → Entity

PatchInput → Entity

Entity → Response

This keeps:

Resolvers thin

Services clean

Entities unchanged

✔ 5.3 Resolver layer should be thin

Resolvers should:

Accept arguments

Delegate to service

Return DTOs

Business logic belongs in the service, not the resolver.

✔ 5.4 Use Input types for PATCH updates

REST PATCH requires custom code.
GraphQL PATCH naturally uses optional fields.

✔ 5.5 Pagination uses PageInfo

A standard pattern:

type PropertyPage {
  content: [Property!]!
  pageInfo: PageInfo!
}

type PageInfo {
  hasNextPage: Boolean!
  currentPage: Int!
  totalPages: Int!
}


Matches Relay & industry conventions.

📘 6. Development Workflow (Recommended)
Step 1 — Write GraphQL Schema First

The schema defines the domain vocabulary.

Step 2 — Generate/Write DTOs

DTO names & fields match the schema exactly.

Step 3 — Implement Mapper

Handles all transformations.

Step 4 — Implement Service Layer

Encapsulates:

Paging

Filtering

Security

Business rules

Step 5 — Implement GraphQL Resolvers

Thin controller-like layer.

Step 6 — Implement React UI

Apollo Client auto-generates typed responses (in real project, use GraphQL Codegen).

🎯 7. Extending Toward Realtes

This demo gives you a foundation to add:

Rental Listings

Applications

Payments

Users / Tenants

Multi-tenant isolation

Presigned S3 image fetching

Field-level access control

GraphQL makes this easier than REST because the schema organizes your domain clearly and evolves without versioning.