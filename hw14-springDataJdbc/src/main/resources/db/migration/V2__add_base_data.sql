
insert into client (id, name)
values (1, 'name1');
insert into client (id, name)
values (2, 'name2');

insert into address (id, street, client_id)
values (1, 'address1', 1);
insert into address (id, street, client_id)
values (2, 'address2', 2);

insert into phones (id, number, client_id)
values (1, 'phone11', 1);
insert into phones (id, number, client_id)
values (2, 'phone21', 2);
insert into phones (id, number, client_id)
values (3, 'phone22', 2);

SELECT setval('public."client_id_seq"',
              (SELECT MAX(id) FROM public.client)
           );

SELECT setval('public."address_id_seq"',
              (SELECT MAX(id) FROM public.address)
           );

SELECT setval('public."phones_id_seq"',
              (SELECT MAX(id) FROM public.phones)
           );