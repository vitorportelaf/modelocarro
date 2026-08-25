create table marcas (
    id bigint not null,
    nome_marca varchar(100) not null,
    pais_origem varchar(50) not null,
    ano_fundacao integer not null,
    site_oficial varchar(150),
    ativa bit not null,
    primary key (id)
) engine = InnoDB;

create table modelos (
    id bigint not null,
    nome_modelo varchar(100) not null,
    ano_lancamento integer not null,
    tipo_combustivel varchar(30) not null,
    preco_base double precision not null,
    observacoes varchar(255),
    primary key (id)
) engine = InnoDB;