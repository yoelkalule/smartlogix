import api from './axios'

export const login = (data) => api.post('/api/usuarios/login', data)
export const registrar = (data) => api.post('/api/usuarios/registrar', data)