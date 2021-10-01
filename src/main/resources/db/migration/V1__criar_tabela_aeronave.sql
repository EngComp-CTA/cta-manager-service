CREATE TABLE IF NOT EXISTS aeronave (
	id serial,
	marcas varchar(5) NOT NULL,
	fabricante_id integer NOT NULL,
	modelo varchar(25) NOT NULL,
	numero_serie varchar(15) NOT NULL
);

--	id SERIAL PRIMARY KEY,
INSERT INTO aeronave (marcas, fabricante_id, modelo, numero_serie) VALUES ('XX123', 1, 'helicopter', '12345');

