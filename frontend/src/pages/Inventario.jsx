import { useState, useEffect } from 'react'
import { getProductos, crearProducto, eliminarProducto } from '../api/inventario'

export default function Inventario() {
  const [productos, setProductos] = useState([])
  const [form, setForm] = useState({ nombre: '', descripcion: '', precio: '', stock: '', categoria: 'ELECTRONICA' })
  const [mostrarForm, setMostrarForm] = useState(false)

  useEffect(() => {
    cargar()
  }, [])

  const cargar = async () => {
    const res = await getProductos()
    setProductos(res.data)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    await crearProducto({ ...form, precio: parseFloat(form.precio), stock: parseInt(form.stock) })
    setForm({ nombre: '', descripcion: '', precio: '', stock: '', categoria: 'ELECTRONICA' })
    setMostrarForm(false)
    cargar()
  }

  const handleEliminar = async (id) => {
    await eliminarProducto(id)
    cargar()
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-800">Inventario</h2>
        <button
          onClick={() => setMostrarForm(!mostrarForm)}
          className="bg-purple-700 text-white px-4 py-2 rounded-lg hover:bg-purple-800"
        >
          {mostrarForm ? 'Cancelar' : '+ Agregar Producto'}
        </button>
      </div>

      {mostrarForm && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow mb-6 grid grid-cols-2 gap-4">
          <input className="border rounded-lg px-3 py-2" placeholder="Nombre" value={form.nombre} onChange={(e) => setForm({ ...form, nombre: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Descripcion" value={form.descripcion} onChange={(e) => setForm({ ...form, descripcion: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Precio" type="number" value={form.precio} onChange={(e) => setForm({ ...form, precio: e.target.value })} required />
          <input className="border rounded-lg px-3 py-2" placeholder="Stock" type="number" value={form.stock} onChange={(e) => setForm({ ...form, stock: e.target.value })} required />
          <select className="border rounded-lg px-3 py-2" value={form.categoria} onChange={(e) => setForm({ ...form, categoria: e.target.value })}>
            <option>ELECTRONICA</option>
            <option>ROPA</option>
            <option>ALIMENTOS</option>
            <option>HERRAMIENTAS</option>
            <option>OTROS</option>
          </select>
          <button type="submit" className="bg-purple-700 text-white rounded-lg hover:bg-purple-800">Guardar</button>
        </form>
      )}

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-purple-700 text-white">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Nombre</th>
              <th className="p-3 text-left">Categoria</th>
              <th className="p-3 text-left">Precio</th>
              <th className="p-3 text-left">Stock</th>
              <th className="p-3 text-left">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {productos.map((p) => (
              <tr key={p.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{p.id}</td>
                <td className="p-3">{p.nombre}</td>
                <td className="p-3">{p.categoria}</td>
                <td className="p-3">${p.precio.toLocaleString()}</td>
                <td className="p-3">{p.stock}</td>
                <td className="p-3">
                  <button onClick={() => handleEliminar(p.id)} className="text-red-500 hover:text-red-700 text-xs">Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}