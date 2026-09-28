import { useEffect, useState } from 'react';
import {
  getSuppliers,
  createSupplier,
  deleteSupplier,
  uploadSupplierDocument,
  getSupplierDocumentUrl,
} from '../api/supplierApi';

function Suppliers() {
  const [suppliers, setSuppliers] = useState([]);
  const [form, setForm] = useState({ name: '', email: '', phone: '', address: '' });
  const [error, setError] = useState('');
  const [uploadingId, setUploadingId] = useState(null);

  const loadSuppliers = async () => {
    const res = await getSuppliers();
    setSuppliers(res.data);
  };

  useEffect(() => { loadSuppliers(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createSupplier(form);
      setForm({ name: '', email: '', phone: '', address: '' });
      loadSuppliers();
    } catch (err) {
      setError(err.response?.data?.errors ? JSON.stringify(err.response.data.errors) : 'Error creating supplier');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Delete this supplier?')) {
      await deleteSupplier(id);
      loadSuppliers();
    }
  };

  const handleUpload = async (id, file) => {
    if (!file) return;
    setUploadingId(id);
    try {
      await uploadSupplierDocument(id, file);
      alert('Document uploaded to S3');
    } catch (err) {
      alert('Upload failed. Check that the file is under 10MB and the backend is running.');
    } finally {
      setUploadingId(null);
    }
  };

  const handleView = async (id) => {
    try {
      const res = await getSupplierDocumentUrl(id);
      if (res.data.url) {
        window.open(res.data.url, '_blank');
      } else {
        alert(res.data.message || 'No document uploaded yet');
      }
    } catch (err) {
      alert('Could not fetch the document link');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Suppliers</h1>
        <p>Manage your supplier network, reliability scores and documents</p>
      </div>

      <div className="form-card">
        <h3>Add New Supplier</h3>
        <form onSubmit={handleSubmit} className="form-grid">
          {error && <p className="error">{error}</p>}
          <div>
            <label className="field-label">Name</label>
            <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
          </div>
          <div>
            <label className="field-label">Email</label>
            <input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required />
          </div>
          <div>
            <label className="field-label">Phone</label>
            <input value={form.phone} onChange={(e) => setForm({ ...form, phone: e.target.value })} />
          </div>
          <div>
            <label className="field-label">Address</label>
            <input value={form.address} onChange={(e) => setForm({ ...form, address: e.target.value })} />
          </div>
          <button type="submit">Add Supplier</button>
        </form>
      </div>

      <div className="table-card">
        <table>
          <thead>
            <tr><th>Name</th><th>Email</th><th>Phone</th><th>Reliability</th><th>Document</th><th></th></tr>
          </thead>
          <tbody>
            {suppliers.map((s) => (
              <tr key={s.id}>
                <td><strong>{s.name}</strong></td>
                <td>{s.email}</td>
                <td>{s.phone}</td>
                <td>
                  <span className={`badge ${s.reliabilityScore >= 0.8 ? 'badge-success' : s.reliabilityScore >= 0.6 ? 'badge-warning' : 'badge-danger'}`}>
                    {(s.reliabilityScore * 100).toFixed(0)}%
                  </span>
                </td>
                <td>
                  <div className="doc-actions">
                    <label className="btn-secondary">
                      {uploadingId === s.id ? 'Uploading...' : 'Upload'}
                      <input
                        type="file"
                        hidden
                        onChange={(e) => handleUpload(s.id, e.target.files[0])}
                      />
                    </label>
                    <button className="btn-secondary" onClick={() => handleView(s.id)}>View</button>
                  </div>
                </td>
                <td><button className="btn-danger" onClick={() => handleDelete(s.id)}>Delete</button></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Suppliers;