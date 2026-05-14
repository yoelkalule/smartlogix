CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    rol VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (email, password, nombre, rol)
VALUES ('admin@smartlogix.com', 'admin123', 'Administrador', 'ADMIN')
ON CONFLICT (email) DO NOTHING;