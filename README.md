Este projeto foi feito como desafio de uma entrevista técnica.

Premissas utilizadas:
Não travei por cnpj/cpf pois penso que cada cliente poderia fazer varias contas com números diferentes.
Foi utilizado uma classe base para os atributos que existem em comum, separei em tabela de cliente pessoa e cliente empresa pra facilitar conciliação de dados, acredito que dessa forma fica mais fácil a visualização, pensando já em como ficaria no frontend, também já pensando no front implementei métodos que devolvem os dados páginados.
Cliente pre é verificado se há crédito antes do envio da mensagem, se não houver a mensagem não será disparada.
Cliente pós é adicionado ao saldo o valor depois verificado o limite, se ultrapassar o limite a mensagem não será enviada.
Se houver um erro na api da twilio o valor que foi adicionado ao saldo, ou removido do crédito, será estornado.

Foi utilizado api da twilio para disparo da mensagem.

Tecnologia utilizada: Java versão 11. Springboot 2.7.14. Banco h2 para perfil test, Banco Postgre para o perfil Dev. Lombok e hibernate validator. Docker.

será necessário alterar o token da twilio, pois sempre que subia ele na aplicação a twilio acabava mudando meu token por questão de segurança. no arquivo 
application.properties setar a variavel servico.token com este valor : 23fe26c3cc0dd09a5cdfd2b2fa7b7bcd
Navegar para a pasta bcb: cd .\bcb\
Para iniciar o projeto será necessário atualizar o .jar, devido a alteração do token, basta utilizar o comando : mvn clean install -DskipTests=true 
em seguida : docker-compose up

Tem essa mini documentação(com postman) já com os endpoints inseridos :
https://warped-desert-770734.postman.co/workspace/Happy~cc7fedc1-1878-4747-80c7-6c0ec63f3822/collection/16448283-2126a574-3af3-4031-9588-41b28ed193b8?action=share&creator=16448283
