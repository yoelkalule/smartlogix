import { useState } from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Inventario from './pages/Inventario'
import Pedidos from './pages/Pedidos'
import Envios from './pages/Envios'
import Notificaciones from './pages/Notificaciones'
import Sidebar from './components/Sidebar'

export default function App() {
  const [usuario, setUsuario] = useState(null)

  const handleLogin = (data) => {
    setUsuario(data)
  }

  const handleLogout = () => {
    setUsuario(null)
  }

  if (!usuario) {
    return <Login onLogin={handleLogin} />
  }

  return (
    <BrowserRouter>
      <div className="flex">
        <Sidebar usuario={usuario} onLogout={handleLogout} />
        <main className="flex-1 bg-gray-100 min-h-screen">
          <Routes>
            <Route path="/" element={<Navigate to="/dashboard" />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/inventario" element={<Inventario />} />
            <Route path="/pedidos" element={<Pedidos />} />
            <Route path="/envios" element={<Envios />} />
            <Route path="/notificaciones" element={<Notificaciones />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}