import { Link, useLocation } from 'react-router-dom';

function Navbar() {
  const location = useLocation();

  const links = [
    { path: '/', label: 'Dashboard', icon: '◈' },
    { path: '/suppliers', label: 'Suppliers', icon: '🏢' },
    { path: '/products', label: 'Products', icon: '📦' },
    { path: '/orders', label: 'Purchase Orders', icon: '📋' },
  ];

  return (
    <nav className="sidebar">
      <div className="sidebar-brand">
        <div className="brand-icon">SC</div>
        <div>
          <h2>SupplyChain IQ</h2>
          <p className="brand-sub">Risk Monitoring</p>
        </div>
      </div>
      <div className="sidebar-links">
        {links.map((link) => (
          <Link
            key={link.path}
            to={link.path}
            className={`sidebar-link ${location.pathname === link.path ? 'active' : ''}`}
          >
            <span className="link-icon">{link.icon}</span>
            {link.label}
          </Link>
        ))}
      </div>
    </nav>
  );
}

export default Navbar;