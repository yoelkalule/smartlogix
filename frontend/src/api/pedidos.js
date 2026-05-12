import api from './axios'

export const getPedidos = () => api.get('/api/pedidos')
export const crearPedido = (data, tipoCliente) =>
  api.post(`/api/pedidos?tipoCliente=${tipoCliente}`, data)
export const actualizarEstado = (id, estado) =>
  api.put(`/api/pedidos/${id}/estado?estado=${estado}`)