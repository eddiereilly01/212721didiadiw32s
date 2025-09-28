CREATE TABLE sensors (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    metrics VARCHAR(255)
);

CREATE TABLE readings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id INT,
    reading_value FLOAT,
    reading_timestamp TIMESTAMP,
    metric VARCHAR(255)
);

INSERT INTO sensors (id, name, description, metrics) VALUES (1, 'TempSensor', 'Temperature sensor', 'TEMPERATURE,HUMIDITY');
INSERT INTO sensors (id, name, description, metrics) VALUES (2, 'PRECIPITATION Sensor', 'PRECIPITATION sensor', 'PRECIPITATION');
INSERT INTO sensors (id, name, description, metrics) VALUES (3, 'Wind Sensor', 'Wind speed sensor', 'WIND_SPEED');

INSERT INTO readings (sensor_id, reading_value, reading_timestamp, metric) VALUES
(1, 22.5, '2025-09-28 08:00:00', 'TEMPERATURE'),
(1, 19.0, '2025-09-27 12:00:00', 'TEMPERATURE'),
(1, 23.0, '2025-09-26 12:00:00', 'TEMPERATURE'),
(1, 22.0, '2025-09-25 12:00:00', 'TEMPERATURE'),
(1, 16.0, '2025-09-21 12:00:00', 'TEMPERATURE'),
(1, 62.0, '2025-09-28 08:00:00', 'HUMIDITY'),
(1, 33.0, '2025-09-21 12:00:00', 'HUMIDITY'),
(1, 3.0, '2025-09-26 12:00:00', 'HUMIDITY'),
(1, 53.0, '2025-09-25 12:00:00', 'HUMIDITY'),
(1, 38.0, '2025-09-24 12:00:00', 'HUMIDITY'),
(2, 52.0, '2025-09-28 08:00:00', 'PRECIPITATION'),
(2, 4.0, '2025-09-21 12:00:00', 'PRECIPITATION'),
(2, 3.0, '2025-09-26 12:00:00', 'PRECIPITATION'),
(2, 3.0, '2025-09-25 12:00:00', 'PRECIPITATION'),
(2, 32.0, '2025-09-24 12:00:00', 'PRECIPITATION'),
(3, 5.0, '2025-09-20 08:00:00', 'WIND_SPEED'),
(3, 4.5, '2025-09-26 12:00:00', 'WIND_SPEED')
(3, 2.5, '2025-09-23 12:00:00', 'WIND_SPEED')
(3, 3.5, '2025-09-23 12:00:00', 'WIND_SPEED')
(3, 2.4, '2025-09-21 12:00:00', 'WIND_SPEED')
(3, 7.4, '2025-09-20 12:00:00', 'WIND_SPEED')


