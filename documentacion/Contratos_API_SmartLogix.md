# Contratos REST de SmartLogix

## 1. Descripción general

SmartLogix es una solución logística basada en microservicios. El frontend React consume el API Gateway mediante REST. El API Gateway redirige las solicitudes hacia los microservicios de usuarios, inventario, pedidos, envíos y notificaciones.

URL base del API Gateway:

http://localhost:9000

## 2. Servicios

| Servicio | Puerto | Responsabilidad |
|---|---:|---|
| API Gateway | 9000 | Punto de entrada para el frontend |
| ms-usuarios | 8080 | Login, registro, roles y sesiones |
| ms-inventario | 8081 | Productos y control de stock |
| ms-pedidos | 8082 | Creación y administración de pedidos |
| ms-envios | 8083 | Envíos, tracking y estados |
| ms-notificaciones | 8084 | Notificaciones EMAIL, SMS y PUSH |
| PostgreSQL | 5432 | Persistencia de datos |

## 3. Contratos de usuarios

### POST /api/usuarios/login

Entrada:

```json
{
  "email": "cliente@smartlogix.com",
  "password": "cliente123"
}