create table if not exists fabricante
(
    id   serial
        constraint fabricante_pk
            primary key,
    nome varchar(50) not null
);

create table if not exists aeronave
(
    id                 serial
        constraint aeronave_pk
            primary key,
    fabricante_id      integer     not null
        constraint aeronave_fabricante_id_fk
            references fabricante,
    apelido            varchar(50) not null,
    marcas             varchar(5)  not null,
    modelo             varchar(15) not null,
    numero_serie       integer     not null,
    categoria_registro varchar(3)  not null
);
comment on column aeronave.marcas is 'CC-MMM : marca de nacionalidade(CC) e matricula(MMM)';

INSERT INTO fabricante (nome) VALUES ('HELIBRÁS');
--INSERT INTO aeronave (marcas, fabricante_id, modelo, numero_serie, categoria_registro, apelido)
-- VALUES ('PPJJJ', 1, 'AS350B2', '4856', 'ADE', 'Aguia');

create table if not exists pessoa
(
    id   serial
        constraint pessoa_pk
            primary key,
    cpf  varchar(11) not null,
    nome varchar(50) not null
);

create table if not exists aeronauta
(
    id        serial
        constraint aeronauta_pk
            primary key,
    pessoa_id integer not null
        constraint aeronauta_pessoa_id_fk
            references pessoa,
    canac     integer not null
);
comment on table aeronauta is 'candidato ou profissional relacionado a aviação civil';
comment on column aeronauta.canac is 'codigo anac - identificacao na anac';
create unique index if not exists aeronauta_anac_uindex
    on aeronauta (canac);