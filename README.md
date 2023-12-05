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

DESAFIO ESTÁ ABAIXO:

"Boa tarde <CANDIDATO/A>, conforme falamos hoje, segue o descritivo para criação do projeto simplificado de teste; Com base nele faremos nossa entrevista técnica com live-coding dia DATA+HORA Pode avisar o término por e-mail ou Whats, e subir no GitHub em um repositório public. Prazo até nossa call

Lembrando que na call faremos um live-coding rápido com objetivo de ver seus conhecimentos e modo de trabalhar, e isso é mais importante do que ter tudo implementando e funcionando 100%.

Vamos desenvolver uma plataforma nova, iremos criar o BCB – Big Chat Brasil, o mais novo e interessante enviador de SMS e outras mensagens brasileiro. Precisamos que seja possível via web ou mobile que os clientes enviem mensagens para seus usuários finais. Nesse sistema cada cliente precisa ser previamente cadastrado. Ao receber a solicitação de envio de mensagem deve ser verificado o tipo de plano de pagamento desse cliente. Caso for pre-pago, esse cliente deve possuir creditos para envio de SMS que custam R$0,25 cada. Caso o cliente seja pós pago, deve registrar o consumo na conta até o limite máximo autorizado.

Dados para cadastro do cliente:

Nome, e-mail, Telefone, CPF responsável, CNPJ, Nome empresa.
Para envio de SMS deve conter:

Número telefone, flag se é WhatsApp, texto
No Backoffice, nossos financeiro deve poder fazer operações e disponibilizar dados como:

Incluir creditos para um cliente
Consultar saldo de um cliente
Alterar limite de um cliente
Alterar plano de um cliente
Ver dados de um cliente
Instruções gerais:

escrever as premissas que foram assumidas no desenvolvimento
deve conter um passo a passo para execução do sistema no readme.md
entregar no Github
Caso seu teste se aplique mais para a vaga backend:

backend em Java (spring boot + spring jpa + spring cloud) ou Node + NestJs
banco de dados de Postgre (pode ser rodando em docker)
Caso seu teste se aplique mais para a vaga frontend:

na parte web/mobile deve ser possível fazer login simples (não necessita cadastro prévio)
front com react ou angular
deve ser responsivo para web e mobile
backend pode ser real ou simulado/mockado
Caso seja fullstack, e tiver disponibilidade pode fazer ambos os sides.

Pode ser um plus:

rodar back + front em container
rodar em kubernetes
envio de notificações (mesmo que seja via mock)
uso de processos assíncronos
outras melhorias de performance que possa ser feita baseada em suas premissas
uso de testes unitários no back ou no front
Observações:

esse teste atende de JR a Especialistas, faça o seu melhor
se ficar muito apertado, entregue com escopo reduzido
se não tiver conhecimento no front, entregue apenas o backend para atender
caso não tiver conhecimento do backend, utilize mocks e entregue apenas o front
collection postman(ou outra ferramenta para testar manualmente)
Pode avisar o término por e-mail. Prazo até nossa call DIA+HORA (a definir de acordo com a disponibilidade do candidato)

Qualquer dúvida estou a disposição,

Boa sorte

Atenciosamente,"
