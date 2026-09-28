import { useEffect, useState } from 'react';
import { getSuppliers } from '../api/supplierApi';
import { getProducts, getLowStockProducts } from '../api/productApi';
import { getPurchaseOrders } from '../api/purchaseOrderApi';

function Dashboard() {
  const [stats, setStats] = useState({ suppliers: 0, products: 0, lowStock: 0, orders: 0 });
  const [lowStockItems, setLowStockItems] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const [suppliersRes, productsRes, lowStockRes, ordersRes] = await Promise.all([
          getSuppliers(),
          getProducts(),
          getLowStockProducts(),
          getPurchaseOrders(),
        ]);
        setStats({
          suppliers: suppliersRes.data.length,
          products: productsRes.data.length,
          lowStock: lowStockRes.data.length,
          orders: ordersRes.data.length,
        });
        setLowStockItems(lowStockRes.data);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
      }
    }
    fetchData();
  }, []);

  return (
    <div>
      <div className="page-header">
        <h1>Dashboard</h1>
        <p>Overview of your supply chain activity</p>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon">🏢</div>
          <div className="stat-label">Suppliers</div>
          <h3>{stats.suppliers}</h3>
        </div>
        <div className="stat-card">
          <div className="stat-icon">📦</div>
          <div className="stat-label">Products</div>
          <h3>{stats.products}</h3>
        </div>
        <div className="stat-card alert">
          <div className="stat-icon">⚠️</div>
          <div className="stat-label">Low Stock Alerts</div>
          <h3>{stats.lowStock}</h3>
        </div>
        <div className="stat-card">
          <div className="stat-icon">📋</div>
          <div className="stat-label">Purchase Orders</div>
          <h3>{stats.orders}</h3>
        </div>
      </div>

      {lowStockItems.length > 0 && (
        <div className="alert-section">
          <h2>⚠️ Items Needing Reorder</h2>
          <ul>
            {lowStockItems.map((item) => (
              <li key={item.id}>
                <span>{item.name}</span>
                <span className="stock-badge">{item.currentStock} left · reorder at {item.reorderThreshold}</span>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default Dashboard;