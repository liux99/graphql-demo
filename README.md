# GraphQL Demo

A full-stack sample that demonstrates GraphQL best practices with a Spring Boot backend (H2 in-memory database) and a React/Apollo frontend. The project shows how to model a property listing domain, add pagination and filtering, and exercise mutations from a simple UI.

## Project structure
- `backend/` – Spring Boot 3 GraphQL service with JPA and H2. GraphiQL is enabled at `/graphiql`.
- `frontend/` – React + Vite + Apollo Client UI that calls the GraphQL API.

## Backend
1. Navigate to the backend folder and build or run:
   ```bash
   cd backend
   ./mvnw spring-boot:run # or mvn spring-boot:run if the wrapper is not available
   ```
2. The API will start on `http://localhost:8080/graphql` and pre-seed three properties into the in-memory H2 database.
3. Open GraphiQL at `http://localhost:8080/graphiql` and run queries like:
   ```graphql
   query {
     properties(page: 0, size: 5, filter: { city: "Austin" }) {
       pageInfo { currentPage totalPages hasNextPage }
       content { id propertyName city state status amenities features }
     }
   }
   ```
   ```graphql
   mutation {
     createProperty(input: {
       ownerId: 9
       propertyName: "Downtown Condo"
       city: "Austin"
       state: "TX"
       postalCode: "73301"
       status: AVAILABLE
       amenities: ["Pool", "Concierge"]
       features: { parkingSpots: 2 }
     }) {
       id
       propertyName
     }
   }
   ```

## Frontend
1. Install dependencies (an internet connection to npm is required):
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
2. The app expects the backend at `http://localhost:8080/graphql`. Update `src/main.jsx` if you run the server elsewhere.
3. Use the UI to filter properties, create a new one, toggle status, or delete it—all operations call the GraphQL API.

## Notes on GraphQL best practices
- The schema models inputs separately for creation vs. patch updates to avoid partial updates with `null` overwriting values.
- Pagination returns both content and `PageInfo` to drive UI navigation.
- A custom `JSONObject` scalar is registered to safely pass rich feature metadata without loosening the schema entirely.
- Data seeding illustrates how to keep an in-memory store predictable for demos and tests.
