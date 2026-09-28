import { useEffect, useState } from 'react';
import { getPurchaseOrders, updateOrderStatus } from '../api/purchaseOrderApi';

function PurchaseOrders() {
  const [orders, setOrders] = useState([]);

  const loadOrders = async () => {
    const res = await getPurchaseOrders();
    setOrders(res.data);
  };

  useEffect(() => { loadOrders(); }, []);

  const handleStatusChange = async (id, newStatus) => {
    await updateOrderStatus(id, newStatus);
    loadOrders();
  };

  return (
    <div>
      <div className="page-header">
        <h1>Purchase Orders</h1>
        <p>Track order status from placement to delivery</p>
      </div>

      <div className="table-card">
        <table>
          <thead>
            <tr><th>Order #</th><th>Quantity</th><th>Order Date</th><th>Expected Delivery</th><th>Status</th></tr>
          </thead>
          <tbody>
            {orders.map((o) => (
              <tr key={o.id}>
                <td><strong>{o.orderNumber}</strong></td>
                <td>{o.quantity}</td>
                <td>{o.orderDate}</td>
                <td>{o.expectedDeliveryDate}</td>
                <td>
                  <select value={o.status} onChange={(e) => handleStatusChange(o.id, e.target.value)}>
                    <option value="PENDING">PENDING</option>
                    <option value="APPROVED">APPROVED</option>
                    <option value="SHIPPED">SHIPPED</option>
                    <option value="DELIVERED">DELIVERED</option>
                    <option value="CANCELLED">CANCELLED</option>
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default PurchaseOrders;