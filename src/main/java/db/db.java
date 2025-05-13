/*
-- Crear base de datos
CREATE DATABASE IF NOT EXISTS clubtenis;
USE clubtenis;

-- Tabla de jugadores
CREATE TABLE players (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    surnames VARCHAR(100),
    email VARCHAR(100),
    phone int,
    Dni VARCHAR(20) UNIQUE,
    user_name VARCHAR(50) UNIQUE,
    password VARCHAR(100) UNIQUE
);

-- Tabla de torneos (sin fechas)
CREATE TABLE tournaments (
    tournament_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(50),
    classification VARCHAR(50)
);

-- Tabla de inscripciones (con fechas individuales por jugador)
CREATE TABLE inscriptions (
    inscription_id INT AUTO_INCREMENT PRIMARY KEY,
    players_id INT,
    tournaments_id INT,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    FOREIGN KEY (players_id) REFERENCES players(player_id),
    FOREIGN KEY (tournaments_id) REFERENCES tournaments(tournament_id)
);

-- Insertar jugadores
INSERT INTO players (name, surnames, email, phone, Dni, user_name, password) VALUES
('Rafael', 'Nadal', 'nadal@example.com', 666111222, '11111111A', 'rafanadal', 'contrasena123'),
('Carlos', 'Alcaraz', 'alcaraz@example.com', 666222333, '22222222B', 'calcaraz', 'tenisTop'),
('Paula', 'Badosa', 'paula@example.com', 66633344, '33333333C', 'pbadosa', 'passPaula');

-- Insertar torneos (sin fechas)
INSERT INTO tournaments (name, description, classification) VALUES
('Open de Galicia', 'Torneo rexional galego', 'Amateur'),
('Copa Nacional', 'Competición nacional de verán', 'Profesional');

-- Insertar inscripciones con fechas individuales
INSERT INTO inscriptions (players_id, tournaments_id, start_date, end_date) VALUES
(1, 1, '2024-06-01', '2024-06-10'),
(2, 1, '2024-06-01', NULL),
(2, 2, '2024-07-15', '2024-07-25'),
(3, 2, '2024-07-15', NULL);
*/