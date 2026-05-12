import { useState, useEffect } from 'react'
import { getEnvios, crearEnvio } from '../api/envios'

export default function Envios() {
  const [envios, setEnvios] = useState([])
  const [form, setForm] = useState({ pedidoId: '', destino: '', transportista: '' })
  const [mostrarForm, setMostrarForm] = useState(false)

  useEffect(() => { cargar() }, [])

  const cargar = async () => {
    const res = await getEnvios()
    setEnvios(res.data)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    await crearEnvio({ ...form, pedidoId: parseInt(form.pedidoId) })
    setMostrarForm(false)
    cargar()
  }

  const estadoColor = (estado) => {
    const colores = { PENDIENTE: 'bg-yellow-100 text-yellow-800', ASIGNADO: 'bg-blue-100 text-blue-800', EN_RUTA: 'bg-orange-100 text-orange-800', ENTREGADO: 'bg-green-100 text-green-800', FALLIDO: 'bg-red-100 text-red-800' }
    return colores[estado] || 'bg-gray-100 text-gray-800'
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-800">Envios</h2>
        <button onClick={() => setMostrarForm(!mostrarForm)} className="bg-purple-700 text-white px-4 py-2 rounded-lg hover:bg-purple-800">
          {mostrarForm ? 'Cancelar' : '+ Nuevo Envio'}
        </button>
      </div>

      {mostrarForm && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow mb-6 grid grid-cols-3 gap-4">
          <input className="border rounded-lg px-3 py-2" placeholder="ID Pedido" type="number" value={form.pedidoId} onChange={(e) => setForm({ ...form, pedidoId: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Destino" value={form.destino} onChange={(e) => setForm({ ...form, destino: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Transportista" value={form.transportista} onChange={(e) => setForm({ ...form, transportista: e.target.value })} required />
          <button type="submit" className="col-span-3 bg-purple-700 text-white rounded-lg py-2 hover:bg-purple-800">Crear Envio</button>
        </form>
      )}

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-purple-700 text-white">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Pedido</th>
              <th className="p-3 text-left">Destino</th>
              <th className="p-3 text-left">Transportista</th>
              <th className="p-3 text-left">Tracking</th>
              <th className="p-3 text-left">Estado</th>
            </tr>
          </thead>
          <tbody>
            {envios.map((e) => (
              <tr key={e.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{e.id}</td>
                <td className="p-3">#{e.pedidoId}</td>
                <td className="p-3">{e.destino}</td>
                <td className="p-3">{e.transportista}</td>
                <td className="p-3 font-mono text-xs">{e.numeroTracking}</td>
                <td className="p-3"><span className={`px-2 py-1 rounded-full text-xs font-medium ${estadoColor(e.estado)}`}>{e.estado}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}