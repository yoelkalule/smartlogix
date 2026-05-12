import { Link, useLocation } from 'react-router-dom'

const menu = [
  { path: '/dashboard', label: 'Dashboard', icon: '🏠' },
  { path: '/inventario', label: 'Inventario', icon: '🏭' },
  { path: '/pedidos', label: 'Pedidos', icon: '📦' },
  { path: '/envios', label: 'Envios', icon: '🚚' },
  { path: '/notificaciones', label: 'Notificaciones', icon: '🔔' },
]

export default function Sidebar({ usuario, onLogout }) {
  const location = useLocation()

  return (
    <div className="w-64 min-h-screen bg-purple-900 text-white flex flex-col">
      <div className="p-6 border-b border-purple-700">
        <h1 className="text-xl font-bold">SmartLogix</h1>
        <p className="text-purple-300 text-sm mt-1">{usuario?.nombre}</p>
        <span className="text-xs bg-purple-700 px-2 py-1 rounded-full">{usuario?.rol}</span>
      </div>

      <nav className="flex-1 p-4 space-y-1">
        {menu.map((item) => (
          <Link
            key={item.path}
            to={item.path}
            className={`flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              location.pathname === item.path
                ? 'bg-purple-700 text-white'
                : 'text-purple-200 hover:bg-purple-800'
            }`}
          >
            <span>{item.icon}</span>
            <span>{item.label}</span>
          </Link>
        ))}
      </nav>

      <div className="p-4 border-t border-purple-700">
        <button
          onClick={onLogout}
          className="w-full text-purple-300 hover:text-white text-sm py-2"
        >
          Cerrar sesion
        </button>
      </div>
    </div>
  )
}