Premissas utilizadas:
Não travei por cnpj/cpf pois penso que cada cliente poderia fazer varias contas com números diferentes.
Foi utilizado uma classe base para os atributos que existem em comum, separei em tabela de cliente pessoa e cliente empresa pra facilitar conciliação de dados, acredito que dessa forma fica mais fácil a visualização, pensando já em como ficaria no frontend.
Cliente pre é verificado se há crédito antes do envio da mensagem, se não houver a mensagem não será disparada.
Cliente pós é adicionado ao saldo o valor depois verificado o limite, se ultrapassar o limite a mensagem não será enviada.
Se houver um erro na api da twilio o valor que foi adicionado ao saldo, ou removido do crédito, será estornado.

Foi utilizado api da twilio para disparo da mensagem.

Tecnologia utilizada: Java versão 11. Springboot 2.7.14. Banco h2 para perfil test, Banco Postgre para o perfil Dev. Lombok e hibernate validator. Docker.

Para iniciar o projeto basta utilizar o comando : docker-compose up
Fazer o login no banco de dados :
host: 0.0.0.0
porta: 5432
login: postgres
senha: 123456

Subir as tabelas : 

create table account (id_account  serial not null, vl_balance numeric(19, 2), vl_credit numeric(19, 2), vl_limit numeric(19, 2), tp_account varchar(255), primary key (id_account));
create table client_company (id_client_company  serial not null, ds_email varchar(255), ds_name varchar(255), nr_telefone varchar(255), cd_cnpj varchar(14), id_conta int4, primary key (id_client_company));
create table client_person (id_client_person  serial not null, ds_email varchar(255), ds_name varchar(255), nr_telefone varchar(255), cd_cpf varchar(11), id_conta int4, primary key (id_client_person));
alter table client_company add constraint FKd6jronttso93m1qlg8sbd8s0y foreign key (id_conta) references account;
alter table client_person add constraint FKsvhiwwaearpfniskun0oyp5dn foreign key (id_conta) references account;

Tem essa mini documentação(com postman) já com os endpoints inseridos :
https://warped-desert-770734.postman.co/workspace/Happy~cc7fedc1-1878-4747-80c7-6c0ec63f3822/collection/16448283-2126a574-3af3-4031-9588-41b28ed193b8?action=share&creator=16448283