import { useState, useEffect } from 'react'
import { getPedidos, crearPedido } from '../api/pedidos'

export default function Pedidos() {
  const [pedidos, setPedidos] = useState([])
  const [form, setForm] = useState({ usuarioId: 1, productoId: 1, cantidad: 1, montoTotal: 0, tipo: 'NACIONAL', destino: '' })
  const [tipoCliente, setTipoCliente] = useState('NORMAL')
  const [mostrarForm, setMostrarForm] = useState(false)

  useEffect(() => { cargar() }, [])

  const cargar = async () => {
    const res = await getPedidos()
    setPedidos(res.data)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    await crearPedido({ ...form, cantidad: parseInt(form.cantidad), montoTotal: parseFloat(form.montoTotal), usuarioId: parseInt(form.usuarioId), productoId: parseInt(form.productoId) }, tipoCliente)
    setMostrarForm(false)
    cargar()
  }

  const estadoColor = (estado) => {
    const colores = { PENDIENTE: 'bg-yellow-100 text-yellow-800', CONFIRMADO: 'bg-blue-100 text-blue-800', EN_TRANSITO: 'bg-orange-100 text-orange-800', ENTREGADO: 'bg-green-100 text-green-800', CANCELADO: 'bg-red-100 text-red-800' }
    return colores[estado] || 'bg-gray-100 text-gray-800'
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-800">Pedidos</h2>
        <button onClick={() => setMostrarForm(!mostrarForm)} className="bg-purple-700 text-white px-4 py-2 rounded-lg hover:bg-purple-800">
          {mostrarForm ? 'Cancelar' : '+ Nuevo Pedido'}
        </button>
      </div>

      {mostrarForm && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow mb-6 grid grid-cols-2 gap-4">
          <input className="border rounded-lg px-3 py-2" placeholder="ID Usuario" type="number" value={form.usuarioId} onChange={(e) => setForm({ ...form, usuarioId: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="ID Producto" type="number" value={form.productoId} onChange={(e) => setForm({ ...form, productoId: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Cantidad" type="number" value={form.cantidad} onChange={(e) => setForm({ ...form, cantidad: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Monto Total" type="number" value={form.montoTotal} onChange={(e) => setForm({ ...form, montoTotal: e.target.value })} required />
          <select className="border rounded-lg px-3 py-2" value={form.tipo} onChange={(e) => setForm({ ...form, tipo: e.target.value })}>
            <option>NACIONAL</option>
            <option>INTERNACIONAL</option>
          </select>
          <select className="border rounded-lg px-3 py-2" value={tipoCliente} onChange={(e) => setTipoCliente(e.target.value)}>
            <option value="NORMAL">Sin descuento</option>
            <option value="CLIENTE">Cliente 10%</option>
            <option value="EMPRESA">Empresa 25%</option>
          </select>
          <input className="border rounded-lg px-3 py-2 col-span-2" placeholder="Destino" value={form.destino} onChange={(e) => setForm({ ...form, destino: e.target.value })} required />
          <button type="submit" className="col-span-2 bg-purple-700 text-white rounded-lg py-2 hover:bg-purple-800">Crear Pedido</button>
        </form>
      )}

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-purple-700 text-white">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Tipo</th>
              <th className="p-3 text-left">Destino</th>
              <th className="p-3 text-left">Monto</th>
              <th className="p-3 text-left">Estado</th>
              <th className="p-3 text-left">Observaciones</th>
            </tr>
          </thead>
          <tbody>
            {pedidos.map((p) => (
              <tr key={p.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{p.id}</td>
                <td className="p-3">{p.tipo}</td>
                <td className="p-3">{p.destino}</td>
                <td className="p-3">${p.montoTotal.toLocaleString()}</td>
                <td className="p-3"><span className={`px-2 py-1 rounded-full text-xs font-medium ${estadoColor(p.estado)}`}>{p.estado}</span></td>
                <td className="p-3 text-xs text-gray-500">{p.observaciones}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}