import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Suppliers from './pages/Suppliers';
import Products from './pages/Products';
import PurchaseOrders from './pages/PurchaseOrders';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <div className="app-shell">
        <Navbar />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/suppliers" element={<Suppliers />} />
            <Route path="/products" element={<Products />} />
            <Route path="/orders" element={<PurchaseOrders />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;