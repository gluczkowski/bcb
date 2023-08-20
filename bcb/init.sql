CREATE DATABASE bcb;
create table account (id_account  serial not null, vl_balance numeric(19, 2), vl_credit numeric(19, 2), vl_limit numeric(19, 2), tp_account varchar(255), primary key (id_account));
create table client_company (id_client_company  serial not null, ds_email varchar(255), ds_name varchar(255), nr_telefone varchar(255), cd_cnpj varchar(14), id_conta int4, primary key (id_client_company));
create table client_person (id_client_person  serial not null, ds_email varchar(255), ds_name varchar(255), nr_telefone varchar(255), cd_cpf varchar(11), id_conta int4, primary key (id_client_person));
alter table client_company add constraint FKd6jronttso93m1qlg8sbd8s0y foreign key (id_conta) references account;
alter table client_person add constraint FKsvhiwwaearpfniskun0oyp5dn foreign key (id_conta) references account;
