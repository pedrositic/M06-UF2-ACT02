-- Inserciones para la tabla companyia
INSERT INTO `companyia` (`id`, `nom`) VALUES
(1, 'Renfe'),
(2, 'AVE'),
(3, 'Metro');

-- Inserciones para la tabla conductor
INSERT INTO `conductor` (`id`, `nom`, `companyia`) VALUES
(1, 'Juan Pérez', 1),
(2, 'Laura López', 2),
(3, 'Carlos García', 3);

-- Inserciones para la tabla estacio
INSERT INTO `estacio` (`id`, `nom`) VALUES
(1, 'Barcelona Sants'),
(2, 'Madrid Atocha'),
(3, 'Valencia Nord'),
(4, 'Sevilla Santa Justa');

-- Inserciones para la tabla maquinista
INSERT INTO `maquinista` (`id`, `nom`, `companyia`) VALUES
(1, 'Pedro Fernández', 1),
(2, 'Ana Martínez', 2),
(3, 'Luis Gómez', 3);

-- Inserciones para la tabla tren
INSERT INTO `tren` (`id`, `nom`, `companyia`, `capacitat`) VALUES
(1, 'Tren Talgo', 1, 200),
(2, 'Tren AVE S-103', 2, 300),
(3, 'Tren Metro L3', 3, 150);

-- Inserciones para la tabla trajecte
INSERT INTO `trajecte` (`id`, `nom`, `estacioInicialId`, `estacioFinalId`, `preu`, `parades`, `companyiaId`, `horaSortida`, `horaArribada`, `idTren`, `idMaquinista`) VALUES
(1, 'Barcelona a Madrid', 1, 2, 50.0, 2, 1, 800, 1100, 1, 1),
(2, 'Madrid a Valencia', 2, 3, 30.0, 1, 2, 1200, 1400, 2, 2),
(3, 'Valencia a Sevilla', 3, 4, 40.0, 3, 3, 1500, 1800, 3, 3);
