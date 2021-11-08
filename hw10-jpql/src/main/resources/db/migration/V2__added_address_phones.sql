create table address
(
    id   bigserial not null primary key,
    street varchar(50)
);

create table phones
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint,
    constraint fk_phones_client foreign key (client_id) references client
);

alter table client add column address_id bigint;
alter table client add constraint fk_clients_address foreign key (address_id) references address;
