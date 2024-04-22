# API REST para Controle de Cadastro de Profissionais e Contatos

Esta API permite o controle de cadastro de profissionais e seus números de contato.

Na raiz do projeto está os arquivos necessarios para inicializar a API<br/>

### Tecnologias utilizadas:
- Java v17
- Docker v24
- Postgres v16
- Postman v10

Primeiramente, deve inicializar o base de dados, com o Docker instalado, usando o terminal vá ate a raiz do projeto e insira o comando
'docker-compose up -d', aguarde alguns segundos ate o banco inicializar com os scripts correntamente, feito isso a API pode
ser iniciada.<br/>
Na raiz do projeto está uma coleção com o nome 'Simples Dental.postman_collection.json' para ser utilizada no Postman, está devidamente configurada
com todos os endpoints e rests.


## Schema do Banco de Dados

### Tabela `contatos`

Campos:
- nome: Varchar (ex.: fixo casa, celular, escritório)
- contato: Varchar
- created_date: Date
- profissional: Chave estrangeira com a tabela profissional

### Tabela `profissionais`

Campos:
- nome: Varchar
- cargo: Varchar (Valores possíveis: Desenvolvedor, Designer, Suporte, Tester)
- nascimento: Date
- created_date: Date

Mapeamentos: A tabela de contatos possui um mapeamento N para 1 com a tabela de profissionais.

`contatos N -> 1 profissionais`

## Endpoints

### Contatos

- **GET /contatos**

  Lista de contatos com base nos critérios definidos em Params

  Params:
    - q - String: Filtro para buscar contatos que contenham o texto em qualquer um de seus atributos
    - fields - List<String>: Opcional. Quando presente, apenas os campos listados em fields deverão ser retornados

- **GET /contatos/:id**

  Retorna todos os dados do contato que possui o ID passado na URL

- **POST /contatos**

  Insere no banco de dados os dados do contato enviados via body

  Body (Content-type: Json)

  Response:
    - Sucesso: Contato com id {ID} cadastrado

- **PUT /contatos/:id**

  Atualiza os dados do contato que possui o ID passado via URL com os dados enviados no Body

  Body (Content-type: Json)

  Response:
    - Sucesso: Cadastro alterado

- **DELETE /contatos/:id**

  Response:
    - Sucesso: Contato excluído

### Profissionais

- **GET /profissionais**

  Lista de profissionais com base nos critérios definidos em Params

  Params:
    - q - String: Filtro para buscar profissionais que contenham o texto em qualquer um de seus atributos
    - fields - List<String>: Opcional. Quando presente, apenas os campos listados em fields deverão ser retornados

- **GET /profissionais/:id**

  Retorna todos os dados do profissional que possui o ID passado na URL

- **POST /profissionais**

  Insere no banco de dados os dados do profissional enviados via body

  Body (Content-type: Json)

  Response:
    - Sucesso: Profissional com id {ID} cadastrado

- **PUT /profissionais/:id**

  Atualiza os dados do profissional que possui o ID passado via URL com os dados enviados no Body

  Body (Content-type: Json)

  Response:
    - Sucesso: Cadastro alterado

- **DELETE /profissionais/:id**

  **Importante!** Este método realiza uma exclusão lógica do registro

  Response:
    - Sucesso: Profissional excluído
