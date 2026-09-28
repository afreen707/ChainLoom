import api from './axiosConfig';

export const getSuppliers = () => api.get('/suppliers');
export const getSupplierById = (id) => api.get(`/suppliers/${id}`);
export const createSupplier = (data) => api.post('/suppliers', data);
export const updateSupplier = (id, data) => api.put(`/suppliers/${id}`, data);
export const deleteSupplier = (id) => api.delete(`/suppliers/${id}`);
export const uploadSupplierDocument = (id, file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post(`/suppliers/${id}/document`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getSupplierDocumentUrl = (id) => api.get(`/suppliers/${id}/document`);