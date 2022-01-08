create table profiles
(
    id bigserial not null primary key,
    name varchar(50) not null
);

create table goods
(
    id bigserial not null primary key,
    name varchar(50) not null,
    outer_id bigint not null,
    deal_type varchar(50) not null,
    math_statement varchar(50) not null,
    price bigint not null,
    profile_id bigint not null,
    CONSTRAINT fk_profiles_goods FOREIGN KEY (profile_id)
        REFERENCES profiles(id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
        DEFERRABLE INITIALLY IMMEDIATE
);

create table emails
(
    id bigserial not null primary key,
    profile_id bigint not null,
    email varchar(256) not null,
    CONSTRAINT fk_profiles_emails FOREIGN KEY (profile_id)
        REFERENCES profiles(id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
        DEFERRABLE INITIALLY IMMEDIATE
);


