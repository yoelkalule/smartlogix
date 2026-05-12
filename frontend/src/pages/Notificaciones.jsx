import { useState, useEffect } from 'react'
import { getNotificaciones, enviarNotificacion } from '../api/notificaciones'

export default function Notificaciones() {
  const [notificaciones, setNotificaciones] = useState([])
  const [pedidoId, setPedidoId] = useState('')
  const [tipo, setTipo] = useState('EMAIL')
  const [mensaje, setMensaje] = useState('')

  useEffect(() => { cargar() }, [])

  const cargar = async () => {
    const res = await getNotificaciones()
    setNotificaciones(res.data)
  }

  const handleEnviar = async (e) => {
    e.preventDefault()
    await enviarNotificacion(pedidoId, tipo, mensaje)
    setPedidoId('')
    setMensaje('')
    cargar()
  }

  const tipoColor = (tipo) => {
    const colores = { EMAIL: 'bg-blue-100 text-blue-800', SMS: 'bg-green-100 text-green-800', PUSH: 'bg-purple-100 text-purple-800' }
    return colores[tipo] || 'bg-gray-100 text-gray-800'
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Notificaciones</h2>

      <form onSubmit={handleEnviar} className="bg-white p-6 rounded-xl shadow mb-6 grid grid-cols-3 gap-4">
        <input className="border rounded-lg px-3 py-2" placeholder="ID Pedido" type="number" value={pedidoId} onChange={(e) => setPedidoId(e.target.value)} required />
        <select className="border rounded-lg px-3 py-2" value={tipo} onChange={(e) => setTipo(e.target.value)}>
          <option>EMAIL</option>
          <option>SMS</option>
          <option>PUSH</option>
        </select>
        <input className="border rounded-lg px-3 py-2" placeholder="Mensaje" value={mensaje} onChange={(e) => setMensaje(e.target.value)} required />
        <button type="submit" className="col-span-3 bg-purple-700 text-white rounded-lg py-2 hover:bg-purple-800">Enviar Notificacion</button>
      </form>

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-purple-700 text-white">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Pedido</th>
              <th className="p-3 text-left">Tipo</th>
              <th className="p-3 text-left">Mensaje</th>
              <th className="p-3 text-left">Fecha</th>
              <th className="p-3 text-left">Enviado</th>
            </tr>
          </thead>
          <tbody>
            {notificaciones.map((n) => (
              <tr key={n.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{n.id}</td>
                <td className="p-3">#{n.pedidoId}</td>
                <td className="p-3"><span className={`px-2 py-1 rounded-full text-xs font-medium ${tipoColor(n.tipo)}`}>{n.tipo}</span></td>
                <td className="p-3">{n.mensaje}</td>
                <td className="p-3 text-xs text-gray-500">{new Date(n.fechaEnvio).toLocaleString()}</td>
                <td className="p-3">{n.enviado ? 'Si' : 'No'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}