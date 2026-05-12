import api from './axios'

export const getProductos = () => api.get('/api/inventario/productos')
export const crearProducto = (data) => api.post('/api/inventario/productos', data)
export const eliminarProducto = (id) => api.delete(`/api/inventario/productos/${id}`)