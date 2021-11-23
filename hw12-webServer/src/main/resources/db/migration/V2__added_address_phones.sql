create table address
(
    id   bigserial not null primary key,
    street varchar(50),
    client_id bigint not null,
    constraint fk_phones_client foreign key (client_id) references client
);

create table phones
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint,
    constraint fk_phones_client foreign key (client_id) references client
);

