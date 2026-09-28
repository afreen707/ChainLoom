import api from './axiosConfig';

export const getProducts = () => api.get('/products');
export const getLowStockProducts = () => api.get('/products/low-stock');
export const createProduct = (data) => api.post('/products', data);
export const updateProduct = (id, data) => api.put(`/products/${id}`, data);
export const deleteProduct = (id) => api.delete(`/products/${id}`);