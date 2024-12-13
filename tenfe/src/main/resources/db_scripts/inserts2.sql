-- Inserciones adicionales para la tabla companyia
INSERT INTO `companyia` (`id`, `nom`) VALUES
(4, 'Trenitalia'),
(5, 'Eurostar');

-- Inserciones adicionales para la tabla conductor
INSERT INTO `conductor` (`id`, `nom`, `companyia`) VALUES
(4, 'Miguel Angel', 4),
(5, 'Sofia Navarro', 5);

-- Inserciones adicionales para la tabla estacio
INSERT INTO `estacio` (`id`, `nom`) VALUES
(5, 'Paris Gare de Lyon'),
(6, 'Roma Termini');

-- Inserciones adicionales para la tabla maquinista
INSERT INTO `maquinista` (`id`, `nom`, `companyia`) VALUES
(4, 'Francesco Rossi', 4),
(5, 'Marie Dupont', 5);

-- Inserciones adicionales para la tabla tren
INSERT INTO `tren` (`id`, `nom`, `companyia`, `capacitat`) VALUES
(4, 'Tren Frecciarossa', 4, 500),
(5, 'Tren Eurostar E320', 5, 400);

-- Inserciones adicionales para la tabla trajecte
INSERT INTO `trajecte` (`id`, `nom`, `estacioInicialId`, `estacioFinalId`, `preu`, `parades`, `companyiaId`, `horaSortida`, `horaArribada`, `idTren`, `idMaquinista`) VALUES
(4, 'Paris a Roma', 5, 6, 120.0, 4, 4, 600, 1400, 4, 4),
(5, 'Roma a Barcelona', 6, 1, 100.0, 5, 5, 1600, 2300, 5, 5);
