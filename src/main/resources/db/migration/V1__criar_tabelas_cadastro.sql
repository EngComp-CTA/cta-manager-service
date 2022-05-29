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

create table if not exists aeronave_horimetro
(
    aeronave_id       integer       not null
        constraint aeronave_horimetro_pk
            primary key
        constraint aeronave_horimetro_aeronave_id_fk
            references aeronave,
    total_voo         numeric(6, 1) not null,
    total_manutencao  numeric(6, 1) not null,
    atualizado_em     timestamp     not null
);
comment on column aeronave_horimetro.total_voo is 'total de horas de voo em decimal';
comment on column aeronave_horimetro.total_manutencao is 'total de horas de manutencao em decimal';

create table if not exists pessoa
(
    id   serial
        constraint pessoa_pk
            primary key,
    cpf  varchar(11) not null,
    nome varchar(50) not null,
    telefone varchar(11)
);
create unique index if not exists pessoa_cpf_uindex
    on pessoa (cpf);

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