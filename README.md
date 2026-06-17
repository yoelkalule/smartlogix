# SmartLogix

**Integrante**

- Aaron Galaz
- Jose Palacios
- Diego Navarrete

## Sistema de Gestión Logística basado en Microservicios

SmartLogix es una plataforma desarrollada para gestionar el proceso logístico completo de una empresa, permitiendo administrar usuarios, inventario, pedidos, envíos y notificaciones mediante una arquitectura de microservicios.

El proyecto fue desarrollado utilizando **Spring Boot**, **Spring Cloud Gateway**, **Docker**, **MySQL**, **React** y diversas herramientas de calidad de software como **JUnit**, **Mockito** y **JaCoCo**.

---

# Tabla de Contenidos

- Descripción
- Arquitectura
- Microservicios
- Tecnologías utilizadas
- Patrones de diseño
- Estructura del proyecto
- Instalación
- Ejecución
- Pruebas unitarias
- Cobertura de código
- Docker
- Autores

---

# Descripción

El sistema permite administrar de forma independiente los distintos procesos de una empresa logística.

Entre sus principales funcionalidades se encuentran:

- Administración de usuarios.
- Gestión de productos e inventario.
- Registro y administración de pedidos.
- Gestión de envíos.
- Generación de notificaciones.
- Centralización del acceso mediante API Gateway.

La arquitectura de microservicios permite desacoplar cada módulo del sistema, facilitando su mantenimiento, escalabilidad y futuras ampliaciones.

---

# Arquitectura

El sistema está compuesto por seis microservicios independientes.

```
                Frontend (React + Vite)
                        │
                        ▼
                 API Gateway (Spring Cloud)
                        │
 ┌──────────────┬───────────────┬───────────────┬───────────────┬──────────────┐
 ▼              ▼               ▼               ▼               ▼
Usuarios   Inventario      Pedidos         Envíos      Notificaciones
```

Cada microservicio posee su propia lógica de negocio y puede evolucionar de forma independiente.

---

# Microservicios

## API Gateway

Responsable de centralizar todas las solicitudes provenientes del cliente.

Funciones principales:

- Enrutamiento de peticiones.
- Configuración CORS.
- Punto único de acceso para el frontend.

---

## ms-usuarios

Gestiona toda la información relacionada con los usuarios registrados.

Funcionalidades:

- Crear usuarios.
- Buscar usuarios.
- Actualizar información.
- Eliminar usuarios.

---

## ms-inventario

Administra los productos disponibles.

Funcionalidades:

- Crear productos.
- Actualizar stock.
- Devolver stock.
- Consultar inventario.

---

## ms-pedidos

Gestiona el ciclo de vida de los pedidos.

Funcionalidades:

- Crear pedidos.
- Consultar pedidos.
- Actualizar estados.
- Integración con inventario y envíos.

---

## ms-envios

Controla el proceso de despacho de pedidos.

Funcionalidades:

- Registrar envíos.
- Actualizar estado.
- Obtener información de seguimiento.

---

## ms-notificaciones

Encargado del envío de notificaciones del sistema.

Funcionalidades:

- Registrar notificaciones.
- Consultar historial.
- Gestionar estados de envío.

---

# Tecnologías utilizadas

## Backend

- Java 17
- Spring Boot 3.3
- Spring Cloud Gateway
- Spring Data JPA
- Hibernate
- Maven

## Base de datos

- MySQL

## Frontend

- React
- Vite

## Pruebas

- JUnit 5
- Mockito
- JaCoCo

## Contenedores

- Docker
- Docker Compose

## Control de versiones

- Git
- GitHub

---

# Patrones de Diseño Implementados

Durante el desarrollo del proyecto se aplicaron diversos patrones de diseño con el objetivo de mejorar la mantenibilidad, reutilización y organización del código.

Entre ellos destacan:

- Singleton
- Factory
- Strategy
- Observer
- Facade
- Saga

Estos patrones permiten desacoplar componentes, facilitar futuras modificaciones y mejorar la escalabilidad del sistema.

---

# Estructura del Proyecto

```
smartlogix/

├── api-gateway/
├── ms-usuarios/
├── ms-inventario/
├── ms-pedidos/
├── ms-envios/
├── ms-notificaciones/
├── frontend/
├── documentacion/
└── README.md
```

---

# Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 17
- Maven 3.9+
- Docker Desktop
- Docker Compose
- Node.js
- npm

---

# Instalación

Clonar el repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
```

Ingresar al proyecto:

```bash
cd smartlogix
```

---

# Ejecución

## Backend

Cada microservicio puede ejecutarse individualmente mediante:

```bash
mvn spring-boot:run
```

o generar su artefacto mediante:

```bash
mvn clean verify
```

---

## Frontend

Instalar dependencias:

```bash
npm install
```

Ejecutar:

```bash
npm run dev
```

---

## Docker

Para levantar los servicios utilizando Docker:

```bash
docker compose up --build
```

---

# Pruebas Unitarias

Todos los microservicios incluyen pruebas unitarias desarrolladas utilizando:

- JUnit 5
- Mockito

Las pruebas verifican la lógica de negocio de los principales servicios del sistema.

Para ejecutarlas:

```bash
mvn clean verify
```

---

# Cobertura de Código

La cobertura se obtiene mediante JaCoCo.

Cada microservicio genera automáticamente un reporte HTML ubicado en:

```
target/site/jacoco/index.html
```

Estos reportes permiten visualizar:

- porcentaje de cobertura
- líneas ejecutadas
- clases cubiertas
- métodos cubiertos

---

# Calidad del Software

Para asegurar la calidad del sistema se aplicaron las siguientes prácticas:

- Arquitectura basada en microservicios.
- Separación por capas.
- Principios SOLID.
- Patrones de diseño.
- Pruebas unitarias.
- Cobertura de código mediante JaCoCo.
- Control de versiones con Git.


