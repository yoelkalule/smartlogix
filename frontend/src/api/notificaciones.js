import api from './axios'

export const getNotificaciones = () => api.get('/api/notificaciones')
export const enviarNotificacion = (pedidoId, tipo, mensaje) =>
  api.post(`/api/notificaciones/factory?pedidoId=${pedidoId}&tipo=${tipo}&mensaje=${mensaje}`)