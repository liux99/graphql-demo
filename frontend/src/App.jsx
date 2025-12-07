import React, { useMemo, useState } from 'react';
import { useMutation, useQuery } from '@apollo/client';
import { CREATE_PROPERTY, DELETE_PROPERTY, GET_PROPERTIES, UPDATE_PROPERTY } from './queries';

const initialForm = {
  ownerId: '10',
  propertyName: '',
  city: '',
  state: '',
  postalCode: '',
  bedroomsTotal: '',
  bathroomsTotal: '',
  livingArea: '',
  lotSizeAcres: '',
  yearBuilt: '',
  status: 'AVAILABLE',
  amenities: '',
  features: ''
};

function App() {
  const [page, setPage] = useState(0);
  const [form, setForm] = useState(initialForm);
  const [filter, setFilter] = useState({ city: '', state: '', status: '' });

  const variables = useMemo(() => ({
    page,
    size: 5,
    filter: {
      city: filter.city || null,
      state: filter.state || null,
      status: filter.status || null
    }
  }), [page, filter]);

  const { data, loading, refetch } = useQuery(GET_PROPERTIES, { variables });
  const [createProperty] = useMutation(CREATE_PROPERTY, {
    onCompleted: () => {
      setForm(initialForm);
      refetch();
    }
  });
  const [updateProperty] = useMutation(UPDATE_PROPERTY, { onCompleted: () => refetch() });
  const [deleteProperty] = useMutation(DELETE_PROPERTY, { onCompleted: () => refetch() });

  const pageInfo = data?.properties.pageInfo;
  const content = data?.properties.content || [];

  const handleSubmit = (event) => {
    event.preventDefault();
    createProperty({
      variables: {
        input: {
          ownerId: Number(form.ownerId),
          propertyName: form.propertyName,
          city: form.city,
          state: form.state,
          postalCode: form.postalCode,
          bedroomsTotal: form.bedroomsTotal ? Number(form.bedroomsTotal) : null,
          bathroomsTotal: form.bathroomsTotal ? Number(form.bathroomsTotal) : null,
          livingArea: form.livingArea ? Number(form.livingArea) : null,
          lotSizeAcres: form.lotSizeAcres ? Number(form.lotSizeAcres) : null,
          yearBuilt: form.yearBuilt ? Number(form.yearBuilt) : null,
          status: form.status,
          amenities: form.amenities ? form.amenities.split(',').map((item) => item.trim()) : [],
          features: form.features ? JSON.parse(form.features) : null
        }
      }
    });
  };

  const toggleStatus = (property) => {
    const nextStatus = property.status === 'AVAILABLE' ? 'PENDING' : 'AVAILABLE';
    updateProperty({ variables: { input: { id: property.id, status: nextStatus } } });
  };

  const handleDelete = (id) => deleteProperty({ variables: { id } });

  const pageLabel = pageInfo ? `Page ${pageInfo.currentPage + 1} of ${pageInfo.totalPages}` : 'Page 1';

  return (
    <main className="container">
      <header>
        <h1>GraphQL Property Explorer</h1>
        <p>Query, filter, and mutate data against the Spring Boot GraphQL API running on H2.</p>
      </header>

      <section className="panel">
        <h2>Create a property</h2>
        <form className="grid" onSubmit={handleSubmit}>
          {[
            ['propertyName', 'Name'],
            ['city', 'City'],
            ['state', 'State (e.g. TX)'],
            ['postalCode', 'Postal code'],
            ['ownerId', 'Owner Id']
          ].map(([key, label]) => (
            <label key={key}>
              <span>{label}</span>
              <input
                required
                value={form[key]}
                onChange={(e) => setForm({ ...form, [key]: e.target.value })}
              />
            </label>
          ))}

          {[
            ['bedroomsTotal', 'Bedrooms'],
            ['bathroomsTotal', 'Bathrooms'],
            ['livingArea', 'Living area (sqft)'],
            ['lotSizeAcres', 'Lot size (acres)'],
            ['yearBuilt', 'Year built']
          ].map(([key, label]) => (
            <label key={key}>
              <span>{label}</span>
              <input
                type="number"
                value={form[key]}
                onChange={(e) => setForm({ ...form, [key]: e.target.value })}
              />
            </label>
          ))}

          <label>
            <span>Status</span>
            <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
              {['AVAILABLE', 'LEASED', 'INACTIVE', 'COMING_SOON', 'PENDING'].map((status) => (
                <option key={status} value={status}>
                  {status}
                </option>
              ))}
            </select>
          </label>

          <label className="full-row">
            <span>Amenities (comma separated)</span>
            <input
              value={form.amenities}
              onChange={(e) => setForm({ ...form, amenities: e.target.value })}
              placeholder="Roof Deck, Gym"
            />
          </label>

          <label className="full-row">
            <span>Features (JSON)</span>
            <textarea
              value={form.features}
              onChange={(e) => setForm({ ...form, features: e.target.value })}
              placeholder='{"energyStar": true}'
            />
          </label>

          <button type="submit" className="primary">Create property</button>
        </form>
      </section>

      <section className="panel">
        <h2>Browse properties</h2>
        <div className="filters">
          <input
            placeholder="City"
            value={filter.city}
            onChange={(e) => setFilter({ ...filter, city: e.target.value })}
          />
          <input
            placeholder="State"
            value={filter.state}
            onChange={(e) => setFilter({ ...filter, state: e.target.value })}
          />
          <select
            value={filter.status}
            onChange={(e) => setFilter({ ...filter, status: e.target.value })}
          >
            <option value="">Any status</option>
            {['AVAILABLE', 'LEASED', 'INACTIVE', 'COMING_SOON', 'PENDING'].map((status) => (
              <option key={status} value={status}>
                {status}
              </option>
            ))}
          </select>
          <button type="button" onClick={() => refetch(variables)}>Apply filter</button>
        </div>

        {loading && <p>Loading...</p>}
        {!loading && (
          <>
            <div className="cards">
              {content.map((property) => (
                <article key={property.id} className="card">
                  <div className="card-header">
                    <h3>{property.propertyName}</h3>
                    <span className={`status status-${property.status.toLowerCase()}`}>{property.status}</span>
                  </div>
                  <p className="muted">
                    {property.city}, {property.state} {property.postalCode}
                  </p>
                  <p>{property.bedroomsTotal ?? 0} bd • {property.bathroomsTotal ?? 0} ba • {property.livingArea ?? 0} sqft</p>
                  {property.amenities?.length ? (
                    <p className="muted">Amenities: {property.amenities.join(', ')}</p>
                  ) : null}
                  <div className="card-actions">
                    <button type="button" onClick={() => toggleStatus(property)}>
                      Toggle status
                    </button>
                    <button type="button" className="danger" onClick={() => handleDelete(property.id)}>
                      Delete
                    </button>
                  </div>
                </article>
              ))}
            </div>
            <div className="pagination">
              <button disabled={page === 0} onClick={() => setPage((prev) => Math.max(prev - 1, 0))}>
                Previous
              </button>
              <span>{pageLabel}</span>
              <button disabled={!pageInfo?.hasNextPage} onClick={() => setPage((prev) => prev + 1)}>
                Next
              </button>
            </div>
          </>
        )}
      </section>
    </main>
  );
}

export default App;
