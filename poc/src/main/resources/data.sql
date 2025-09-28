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
(1, 22.5, '2025-09-8 08:00:00', 'TEMPERATURE'),
(1, 23.0, '2025-09-8 12:00:00', 'TEMPERATURE'),
(1, 60.0, '2025-09-8 08:00:00', 'HUMIDITY'),
(1, 58.0, '2025-09-8 12:00:00', 'HUMIDITY'),
(3, 5.0, '2025-09-28 08:00:00', 'WIND_SPEED'),
(3, 7.5, '2025-09-28 12:00:00', 'WIND_SPEED')


