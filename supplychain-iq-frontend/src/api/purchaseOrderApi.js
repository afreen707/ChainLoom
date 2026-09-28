import api from './axiosConfig';

export const getPurchaseOrders = () => api.get('/purchase-orders');
export const createPurchaseOrder = (data) => api.post('/purchase-orders', data);
export const updateOrderStatus = (id, status) => api.patch(`/purchase-orders/${id}/status`, { status });
export const deletePurchaseOrder = (id) => api.delete(`/purchase-orders/${id}`);