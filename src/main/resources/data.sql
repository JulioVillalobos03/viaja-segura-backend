-- Insertar administradores
INSERT INTO admins (name, last_name, birth_date, sex, curp, municipality, city, email, password, created_at, updated_at)
VALUES
    ('Ana', 'Gómez', '1990-05-10', 'F', 'GMLA900510MDFRNN01', 'Benito Juárez', 'CDMX', 'ana.admin@example.com', '$2a$12$rq1jR46FXBaj87OuVKQKCeIPv0.QMPhmHNBWrqAuRudeL/qC3uOwi', NOW(), NOW());

-- Insertar pasajeros
INSERT INTO passengers (name, last_name, birth_date, sex, curp, municipality, city, email, phone, password, created_at, updated_at)
VALUES
    ('Carlos', 'López', '1995-07-20', 'M', 'LOPC950720HDFRRR09', 'Guadalajara', 'Jalisco', 'carlos.p@example.com', '3312345678', '$2a$12$rq1jR46FXBaj87OuVKQKCeIPv0.QMPhmHNBWrqAuRudeL/qC3uOwi', NOW(), NOW());

-- Insertar conductores
INSERT INTO drivers (name, last_name, birth_date, sex, curp, municipality, city, email, phone, password, is_available, status, average_rating, created_at, updated_at)
VALUES
    ('Luis', 'Martínez', '1988-03-15', 'M', 'MRNL880315HDFRRN01', 'Monterrey', 'Nuevo León', 'luis.driver@example.com', '8187654321', '$2a$12$rq1jR46FXBaj87OuVKQKCeIPv0.QMPhmHNBWrqAuRudeL/qC3uOwi', true, 'active', 4.8, NOW(), NOW());

-- Información personal del conductor
INSERT INTO driver_personal_info (driver_id, license_id, test_passed, badge_expiration)
VALUES
    (1, 'LIC-DRIV-001', true, '2026-12-31');

-- Vehículos
INSERT INTO vehicles (driver_id, plate_number, brand, model, color, seats, year, created_at, updated_at)
VALUES
    (1, 'XYZ1234', 'Toyota', 'Avanza', 'Rojo', 5, 2020, NOW(), NOW());

-- Ubicación actual del conductor
INSERT INTO driver_locations (driver_id, latitude, longitude, updated_at)
VALUES
    (1, 25.686614, -100.316113, NOW());

-- Ubicación actual del pasajero
INSERT INTO passenger_locations (passenger_id, latitude, longitude, updated_at)
VALUES
    (1, 20.659698, -103.349609, NOW());

-- Viaje
INSERT INTO rides (passenger_id, driver_id, origin_lat, origin_lng, destination_lat, destination_lng, status, price, distance_km, started_at, ended_at, created_at, updated_at)
VALUES
    (1, 1, 20.659698, -103.349609, 25.686614, -100.316113, 'completed', 350.50, 900.00, NOW(), NOW(), NOW(), NOW());

-- Calificación del viaje
INSERT INTO ride_ratings (ride_id, driver_id, passenger_id, rating, passenger_rating, comment, created_at)
VALUES
    (1, 1, 1, 5, 5, 'Excelente viaje y servicio.', NOW());

-- Reporte de viaje
INSERT INTO ride_reports (ride_id, user_id, user_type, message, created_at)
VALUES
    (1, 1, 'passenger', 'El viaje fue excelente pero hubo un poco de tráfico.', NOW());
