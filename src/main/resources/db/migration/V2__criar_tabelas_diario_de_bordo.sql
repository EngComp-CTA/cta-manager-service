create table if not exists diario_bordo
(
    id                serial
        constraint diario_bordo_pk
            primary key,
    aeronave_id       integer                 not null
        constraint diario_bordo_aeronave_id_fk
            references aeronave,
    data_voo          date                    not null,
    status            varchar(25)             not null,
    cliente           varchar(50),
    ativo             boolean                 not null,
    criado_em         timestamp default now() not null,
    atualizado_em     timestamp,
    numero_sequencial smallint                not null
);

comment on column diario_bordo.status is 'ABERTO/AGUARDANDO RUBRICA/COMPLETO';

create table if not exists tripulante
(
    id                serial
        constraint diario_bordo_tripulante_pk
            primary key,
    diario_bordo_id   integer not null
        constraint diario_bordo_tripulantes_diario_bordo_id_fk
            references diario_bordo,
    aeronauta_id      integer not null
        constraint diario_bordo_tripulante_aeronauta_id_fk
            references aeronauta (id),
    hora_apresentacao time    not null
);

create table if not exists diario_bordo_ocorrencia
(
    diario_bordo_id integer not null
        constraint ocorrencia_diario_bordo_id_fk
            references diario_bordo,
    ocorrencia      text
);

create table if not exists local
(
    id          serial
        constraint local_pk
            primary key,
    nome        varchar(25) not null,
    codigo_oaci integer     not null
);

create table if not exists etapa
(
    id              serial
        constraint etapa_pk
            primary key,
    diario_bordo_id integer                 not null
        constraint voo_diario_bordo_id_fk
            references diario_bordo,
    origem          integer                 not null
        constraint voo_local_id_fk
            references local,
    destino         integer                 not null
        constraint voo_local_id_fk_2
            references local,
    partida         time                    not null,
    decolagem       time,
    pouso           time,
    corte           time                    not null,
    tempo_diurno    time,
    tempo_noturno   time,
    tempo_vfr       time,
    tempo_ifr_r     time,
    pousos          smallint                not null,
    combustivel     smallint                not null,
    carga           smallint,
    pessoas_bordo   smallint,
    natureza        varchar(2)              not null,
    observacao      text,
    criado_em       timestamp default now() not null,
    atualizado_em   timestamp               not null,
    finalizado      boolean                 not null
);

create table if not exists funcao_tripulante
(
    id            serial
        constraint funcao_tripulante_pk
            primary key,
    etapa_id      integer not null
        constraint funcao_tripulante_etapa_id_fk
            references etapa,
    tripulante_id integer not null
        constraint funcao_tripulante_tripulante_id_fk
            references tripulante,
    funcao_bordo  char    not null
);

create table if not exists etapa_ciclo
(
    id       serial
        constraint etapa_ciclo_pk
            primary key,
    etapa_id integer       not null
        constraint voo_ciclo_voo_id_fk
            references etapa (id),
    numero   smallint      not null,
    n1       numeric(4, 2) not null,
    n2       numeric(4, 2) not null,
    rin1     integer,
    rin2     integer
);

create table if not exists diario_bordo_horimetro
(
    diario_bordo_id    integer       not null
        constraint diario_bordo_horimetro_pk
            primary key
        constraint diario_bordo_horimetro_diario_bordo_id_fk
            references diario_bordo,
    total_voo_anterior numeric(6, 1) not null,
    total_voo          numeric(6, 1) not null,
    total_mnt_anterior numeric(6, 1) not null,
    total_mnt          numeric(6, 1) not null
);

comment on table diario_bordo_horimetro is 'resumo total das horas em decimais do diario ';

create table if not exists diario_bordo_manutencao
(
    diario_bordo_id     integer       not null
        constraint diario_bordo_manutencao_pk
            primary key
        constraint diario_bordo_manuntencao_diario_bordo_id_fk
            references diario_bordo,
    ultima_intervencao  numeric(6, 1) not null,
    proxima_intervencao numeric(6, 1) not null,
    restante_manutencao numeric(6, 1) not null
);

create table if not exists etapa_abastecimento
(
    etapa_id    integer     not null
        constraint etapa_abastecimento_pk
            primary key
        constraint etapa_abastecimento_etapa_voo_id_fk
            references etapa,
    fornecedora varchar(25) not null,
    cepa        smallint    not null,
    quantidade  smallint    not null
);

create table if not exists etapa_registro
(
    id           serial
        constraint voo_tripulacao_pk
            primary key,
    etapa_id     integer not null
        constraint registro_etapa_id_fk
            references etapa,
    sist         varchar(25),
    discrepancia text    not null,
    aeronauta_id integer not null
        constraint registro_aeronauta_id_fk
            references aeronauta
);

create table if not exists diario_bordo_aprovacao
(
    diario_bordo_id integer not null
        constraint diario_bordo_aprovacao_pk
            primary key
        constraint diario_aprovacao_diario_bordo_id_fk
            references diario_bordo,
    aeronauta_id    integer not null
        constraint diario_bordo_aprovacao_aeronauta_id_fk
            references aeronauta (id),
    acao_corretiva  text    not null
);

create table if not exists aeronave_horimetro
(
    aeronave_id       integer       not null
        constraint aeronave_horimetro_pk
            primary key
        constraint aeronave_horimetro_aeronave_id_fk
            references aeronave,
    total_voo         numeric(6, 1) not null,
    total_manuntencao numeric(6, 1) not null,
    atualizado_em     timestamp     not null
);
comment on column aeronave_horimetro.total_voo is 'total de horas de voo em decimal';
comment on column aeronave_horimetro.total_manuntencao is 'total de horas de manuntencao em decimal';
