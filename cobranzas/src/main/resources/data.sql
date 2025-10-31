-- Insert Roles
INSERT INTO roles (id, nombre) VALUES (1, 'ADMIN');
INSERT INTO roles (id, nombre) VALUES (2, 'ANALISTA');

-- Insert Users (password is '123456' in plain text)
INSERT INTO usuarios (id, username, password, enabled, rol_id) VALUES (1, 'admin', '123456', TRUE, 1);
INSERT INTO usuarios (id, username, password, enabled, rol_id) VALUES (2, 'analista', '123456', TRUE, 2);