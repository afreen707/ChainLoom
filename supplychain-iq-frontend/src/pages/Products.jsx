import { useEffect, useState } from 'react';
import { getProducts, deleteProduct } from '../api/productApi';

function Products() {
  const [products, setProducts] = useState([]);

  const loadProducts = async () => {
    const res = await getProducts();
    setProducts(res.data);
  };

  useEffect(() => { loadProducts(); }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this product?')) {
      await deleteProduct(id);
      loadProducts();
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Products</h1>
        <p>Track inventory levels and reorder points</p>
      </div>

      <div className="table-card">
        <table>
          <thead>
            <tr><th>Name</th><th>SKU</th><th>Stock</th><th>Reorder At</th><th>Price</th><th>Status</th><th></th></tr>
          </thead>
          <tbody>
            {products.map((p) => {
              const isLow = p.currentStock <= p.reorderThreshold;
              return (
                <tr key={p.id} className={isLow ? 'low-stock-row' : ''}>
                  <td><strong>{p.name}</strong></td>
                  <td>{p.sku}</td>
                  <td>{p.currentStock}</td>
                  <td>{p.reorderThreshold}</td>
                  <td>₹{p.unitPrice}</td>
                  <td>
                    <span className={`badge ${isLow ? 'badge-danger' : 'badge-success'}`}>
                      {isLow ? 'Low Stock' : 'In Stock'}
                    </span>
                  </td>
                  <td><button className="btn-danger" onClick={() => handleDelete(p.id)}>Delete</button></td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Products;