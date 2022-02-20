create table users
(
    id bigserial not null primary key,
    telegram_id bigint not null,
    chat_id bigint not null,
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

create table goods_info
(
    id bigserial not null primary key,
    outer_id bigint not null,
    name text not null,
    sell_average_price bigint not null,
    buy_average_price bigint not null
);

create table shops
(
    id bigserial not null primary key,
    good_info_id bigint not null references goods_info(id),
    price bigint not null,
    amount integer not null,
    deal_type text not null
);

create table sent_data
(
    id bigserial not null primary key,
    user_id bigint not null,
    outer_id bigint,
    message text not null,
    CONSTRAINT fk_users_sent_data FOREIGN KEY (user_id)
        REFERENCES users(id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE
        DEFERRABLE INITIALLY IMMEDIATE
);

