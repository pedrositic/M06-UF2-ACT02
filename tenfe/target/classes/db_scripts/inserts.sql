-- Inserciones para la tabla companyia
INSERT INTO `companyia` (`id`, `nom`) VALUES
(1, 'Renfe'),
(2, 'AVE'),
(3, 'Metro'),
(4, 'Trenitalia'),
(5, 'Eurostar'),
(6, 'Renfe2'),
(7, 'AVE2'),
(8, 'Metro2'),
(9, 'Trenitalia2'),
(10, 'Eurostar2'),
(11, 'Renfe3'),
(12, 'AVE3'),
(13, 'Metro3'),
(14, 'Trenitalia3'),
(15, 'Eurostar3');

-- Inserciones para la tabla conductor
INSERT INTO `conductor` (`id`, `nom`, `companyia`) VALUES
(1, 'Juan Perez', 1),
(2, 'Laura Lopez', 2),
(3, 'Carlos Garcia', 3),
(4, 'Miguel Angel', 4),
(5, 'Sofia Navarro', 5),
(6, 'Pedro Jimenez', 1),
(7, 'Marta Gomez', 2),
(8, 'Javier Martinez', 3),
(9, 'Antonio Fernandez', 4),
(10, 'Clara Rodriguez', 5),
(11, 'Manuel Gonzalez', 6),
(12, 'Lucia Perez', 7),
(13, 'Alberto Ruiz', 8),
(14, 'Veronica Sanchez', 9),
(15, 'Carlos Torres', 10);

-- Inserciones para la tabla estacio
INSERT INTO `estacio` (`id`, `nom`) VALUES
(1, 'Barcelona Sants'),
(2, 'Madrid Atocha'),
(3, 'Valencia Nord'),
(4, 'Sevilla Santa Justa'),
(5, 'Paris Gare de Lyon'),
(6, 'Roma Termini'),
(7, 'Bilbao Abando'),
(8, 'Valencia Joaquin Sorolla'),
(9, 'Sevilla Santa Justa2'),
(10, 'Malaga Maria Zambrano'),
(11, 'Barcelona Sants2'),
(12, 'Madrid Chamartin'),
(13, 'Granada Maria Cristina'),
(14, 'Zaragoza Delicias'),
(15, 'Santander Estacion');

-- Inserciones para la tabla maquinista
INSERT INTO `maquinista` (`id`, `nom`, `companyia`) VALUES
(1, 'Pedro Fernandez', 1),
(2, 'Ana Martinez', 2),
(3, 'Luis Gomez', 3),
(4, 'Francesco Rossi', 4),
(5, 'Marie Dupont', 5),
(6, 'Elena Ruiz', 1),
(7, 'Javier Lopez', 2),
(8, 'Sara Gonzalez', 3),
(9, 'Luis Herrera', 4),
(10, 'Marta Gomez', 5),
(11, 'Carlos Diaz', 6),
(12, 'Ana Perez', 7),
(13, 'Miguel Fernandez', 8),
(14, 'Lucia Martinez', 9),
(15, 'Pablo Sanchez', 10);

-- Inserciones para la tabla tren
INSERT INTO `tren` (`id`, `nom`, `companyia`, `capacitat`) VALUES
(1, 'Tren Talgo', 1, 200),
(2, 'Tren AVE S-103', 2, 300),
(3, 'Tren Metro L3', 3, 150),
(4, 'Tren Frecciarossa', 4, 500),
(5, 'Tren Eurostar E320', 5, 400),
(6, 'Tren AVE S-104', 1, 250),
(7, 'Tren Talgo II', 2, 320),
(8, 'Tren Metro L4', 3, 180),
(9, 'Tren Frecciarossa 1000', 4, 550),
(10, 'Tren Eurostar 4000', 5, 450),
(11, 'Tren AVE S-105', 6, 270),
(12, 'Tren Talgo III', 7, 330),
(13, 'Tren Metro L5', 8, 190),
(14, 'Tren Frecciarossa 2000', 9, 600),
(15, 'Tren Eurostar 5000', 10, 500);

-- Inserciones para la tabla trajecte
INSERT INTO `trajecte` (`id`, `nom`, `estacioInicialId`, `estacioFinalId`, `preu`, `parades`, `companyiaId`, `horaSortida`, `horaArribada`, `idTren`, `idMaquinista`) VALUES
(1, 'Barcelona a Madrid', 1, 2, 50.0, 2, 1, 800, 1100, 1, 1),
(2, 'Madrid a Valencia', 2, 3, 30.0, 1, 2, 1200, 1400, 2, 2),
(3, 'Valencia a Sevilla', 3, 4, 40.0, 3, 3, 1500, 1800, 3, 3),
(4, 'Paris a Roma', 5, 6, 120.0, 4, 4, 600, 1400, 4, 4),
(5, 'Roma a Barcelona', 6, 1, 100.0, 5, 5, 1600, 2300, 5, 5),
(6, 'Bilbao a Madrid', 7, 2, 45.0, 2, 1, 700, 1200, 6, 6),
(7, 'Madrid a Valencia', 2, 8, 35.0, 1, 2, 1000, 1400, 7, 7),
(8, 'Valencia a Barcelona', 8, 11, 50.0, 3, 3, 1500, 1800, 8, 8),
(9, 'Santander a Madrid', 14, 2, 55.0, 3, 4, 900, 1300, 9, 9),
(10, 'Madrid a Paris', 2, 5, 120.0, 5, 4, 700, 1300, 10, 10),
(11, 'Barcelona a Sevilla', 11, 9, 60.0, 4, 5, 800, 1400, 11, 11),
(12, 'Sevilla a Malaga', 9, 10, 40.0, 3, 6, 1000, 1600, 12, 12),
(13, 'Granada a Madrid', 13, 2, 65.0, 4, 7, 700, 1300, 13, 13),
(14, 'Zaragoza a Barcelona', 14, 11, 55.0, 3, 8, 900, 1400, 14, 14),
(15, 'Barcelona a Roma', 11, 6, 100.0, 5, 9, 1600, 2300, 15, 15);
