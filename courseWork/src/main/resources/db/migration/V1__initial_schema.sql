create table users
(
    id bigserial not null primary key,
    telegram_id bigint not null,
    first_name text default '',
    last_name text default '',
    user_name text default '',
    is_active boolean not null default false
);

create table goods
(
    id bigserial not null primary key,
    name text not null,
    outer_id bigint not null,
    deal_type text not null,
    math_statement text not null,
    price bigint not null,
    user_id bigint not null,
    CONSTRAINT fk_users_goods FOREIGN KEY (user_id)
        REFERENCES users(id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
        DEFERRABLE INITIALLY IMMEDIATE
);

