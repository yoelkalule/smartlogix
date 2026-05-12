import api from './axios'

export const getEnvios = () => api.get('/api/envios')
export const crearEnvio = (data) => api.post('/api/envios', data)
export const actualizarEstadoEnvio = (id, estado) =>
  api.put(`/api/envios/${id}/estado?estado=${estado}`)