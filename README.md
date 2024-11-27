# API REST - Aplicação User Auto

Esta é a documentação da API do sistema Auto Car desenvolvida como parte do Desafio Pitang. Esta API fornece funcionalidades para gerenciar informações de usuários e seus carros. A seguir, detalharemos os principais endpoints e suas funcionalidades.

## Introdução

Este documento fornece uma descrição detalhada da aplicação `User Auto`, que é uma API REST desenvolvida em Java. A aplicação permite que usuários façam login, gerenciem suas informações de perfil, e adicionem ou gerenciem carros associados às suas contas. As rotas são protegidas por autenticação JWT e são descritas a seguir.

## Como Instalar

Certifique-se de ter o Java instalado, bem como o Maven para compilar e rodar o projeto. Em seguida, execute o comando para compilar as dependências do projeto:

```
    mvn clean install
```

## Como Rodar a Aplicação

Após a instalação, você pode iniciar a aplicação com o seguinte comando:

```
    mvn spring-boot:run
```

## Como Executar o Build, Deploy e Testes

Para realizar o build completo, deploy e execução dos testes automatizados, siga as etapas abaixo:

1. **Compilar e Construir o Projeto:**

   ```
   mvn clean install
   ```

   Isso irá compilar o projeto, rodar os testes unitários e gerar um pacote executável.

2. **Executar a Aplicação:**

   ```
   mvn spring-boot:run
   ```

3. **Rodar Testes Automatizados:** Para rodar os testes unitários e garantir que tudo esteja funcionando corretamente, use:

   ```
   mvn test
   ```

## Estórias de Usuário

1. **Como usuário autenticado, quero listar todos os carros que eu registrei**

2. **Como usuário autenticado, quero buscar um carro específico pelo ID**

3. **Como usuário autenticado, quero cadastrar um novo carro em meu perfil**

4. **Como usuário autenticado, quero atualizar as informações de um carro existente**

5. **Como usuário autenticado, quero remover um carro do meu perfil**

6. **Como usuário autenticado, quero listar todos os usuários cadastrados**

7. **Como usuário autenticado, quero buscar um usuário específico pelo ID**

8. **Como usuário autenticado, quero cadastrar um novo usuário**

9. **Como usuário autenticado, quero atualizar as informações de um usuário**

10. **Como usuário autenticado, quero remover um usuário do sistema**

11. **Como usuário autenticado, quero visualizar meu perfil**

12. **Como usuário não autenticado, quero realizar login na aplicação**

14. **Como usuário não autenticado, quero consultar a ranking de utilizações dos carros pelos usuários**



## Solução

### Justificativas e Defesa Técnica da Solução

A solução foi desenvolvida utilizando uma arquitetura RESTful, com o objetivo de ser escalável, modular e facilmente compreensível. Abaixo estão as principais justificativas técnicas para as escolhas realizadas:

1. **Autenticação JWT:** Utilizamos o JSON Web Token (JWT) para autenticação, garantindo segurança e praticidade na proteção dos endpoints. O JWT é adequado para APIs RESTful, pois permite autenticação stateless, o que contribui para a escalabilidade da aplicação.

2. **Spring Boot e Spring Security:** Optamos pelo uso do Spring Boot devido à sua popularidade e robustez para a criação de microserviços em Java. O Spring Security foi utilizado para a implementação da segurança, incluindo a autenticação via JWT.

3. **Persistência de Dados com JPA:** Foi utilizado o JPA (Java Persistence API) com um banco de dados relacional para garantir a persistência dos dados dos usuários e carros. Isso permite uma integração eficiente e o mapeamento direto dos modelos de dados.

4. **Modularidade e Manutenção:** A aplicação foi desenvolvida seguindo os princípios da modularidade, permitindo a fácil adição de novos módulos e a manutenção dos já existentes. As classes foram organizadas em camadas como controller, serviço, e repositório para garantir a separação de responsabilidades.

5. **Testes Automatizados:** Implementamos testes unitários com JUnit para garantir a estabilidade da aplicação e prevenir regressões. O alto nível de cobertura de testes permite uma entrega confiável.

6. \*\*Documentação e Facilidade de Uso:\*\***Uso:** Foi utilizada uma documentação detalhada para permitir que desenvolvedores e usuários da API possam entender como utilizá-la e como testar os endpoints. A inclusão de exemplos de cURL e a estrutura de resposta ajudam na compreensão rápida do funcionamento.

# API REST

## Autenticação
A API utiliza um sistema de autenticação baseado em JWT (JSON Web Token). É necessário realizar login e obter um token para acessar os recursos protegidos da API. Todos os endpoints, exceto o de login, requerem que o cabeçalho de autorização seja preenchido com o token no formato:

```
Authorization: Bearer {{TOKEN}}
```

## Endpoints

### 1. Login (Signin)
- **Endpoint**: `/api/signin`
- **Método**: `POST`
- **Descrição**: Realiza o login do usuário e retorna um token JWT.
- **Requisição**:
  ```json
  {
    "username": "lucas88",
    "password": "12345"
  }
  ```
- **Resposta**: Um objeto JSON contendo o token de autenticação.

### 2. Gerenciamento de Carros

#### 2.1 Listar Todos os Carros do Usuário Logado
- **Endpoint**: `/api/cars`
- **Método**: `GET`
- **Descrição**: Retorna todos os carros do usuário autenticado.

#### 2.2 Buscar um Carro Específico pelo ID
- **Endpoint**: `/api/cars/{id}`
- **Método**: `GET`
- **Descrição**: Busca um carro específico do usuário autenticado pelo seu ID.

#### 2.3 Cadastrar um Novo Carro
- **Endpoint**: `/api/cars`
- **Método**: `POST`
- **Descrição**: Permite ao usuário logado cadastrar um novo carro.
- **Requisição**:
  ```json
  {
    "yearManufacture": 2023,
    "licensePlate": "AAA-0001",
    "model": "Corolla",
    "color": "Prata",
    "createdAt": "2024-11-15"
  }
  ```

#### 2.4 Atualizar um Carro pelo ID
- **Endpoint**: `/api/cars/{id}`
- **Método**: `PUT`
- **Descrição**: Atualiza as informações de um carro específico do usuário logado.
- **Requisição**:
  ```json
  {
    "yearManufacture": 2023,
    "licensePlate": "SKY-0001",
    "model": "Corolla",
    "color": "Prata",
    "createdAt": "2024-10-15"
  }
  ```

#### 2.5 Remover um Carro pelo ID
- **Endpoint**: `/api/cars/{id}`
- **Método**: `DELETE`
- **Descrição**: Remove um carro específico do usuário logado.

### 3. Gerenciamento de Usuários

#### 3.1 Listar Todos os Usuários
- **Endpoint**: `/api/users`
- **Método**: `GET`
- **Descrição**: Retorna todos os usuários cadastrados no sistema.

#### 3.2 Buscar um Usuário pelo ID
- **Endpoint**: `/api/users/{id}`
- **Método**: `GET`
- **Descrição**: Retorna as informações de um usuário específico.

#### 3.3 Cadastrar um Novo Usuário
- **Endpoint**: `/api/users`
- **Método**: `POST`
- **Descrição**: Permite o cadastro de um novo usuário no sistema.
- **Requisição**:
  ```json
  {
    "firstName": "Mariana",
    "lastName": "Silva",
    "email": "mariana.silva23@email.com",
    "birthday": "1985-09-10",
    "login": "mariana85",
    "password": "senhaSegura123",
    "phone": "998877665",
    "createdAt": "2024-11-25",
    "cars": [
      {
        "yearManufacture": 2012,
        "licensePlate": "XYZ-4567",
        "model": "Corolla",
        "color": "Branco",
        "createdAt": "2024-11-25"
      }
    ]
  }
  ```

#### 3.4 Atualizar um Usuário pelo ID
- **Endpoint**: `/api/users/{id}`
- **Método**: `PUT`
- **Descrição**: Atualiza as informações de um usuário específico.
- **Requisição**:
  ```json
  {
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao.silva@email.com",
    "birthday": "1985-08-15",
    "login": "joao123",
    "password": "35635",
    "phone": "1234567890",
    "cars": [
      {
        "id": 101,
        "yearManufacture": 1999,
        "licensePlate": "ABC-1222",
        "model": "Monza",
        "color": "Prata",
        "createdAt": "2024-11-24"
      }
    ],
    "createdAt": "2024-11-24"
  }
  ```

#### 3.5 Remover um Usuário pelo ID
- **Endpoint**: `/api/users/{id}`
- **Método**: `DELETE`
- **Descrição**: Remove um usuário específico do sistema.

### 4. Retornar Informações do Usuário Logado
- **Endpoint**: `/api/me`
- **Método**: `GET`
- **Descrição**: Retorna as informações do usuário atualmente autenticado.

### 5. Ranking de Utilização de Carros pelos Usuários
- **Endpoint**: `/api/cars_utilization`
- **Método**: `GET`
- **Descrição**: Retorna o ranking de usuários e seus carros, ordenados pela quantidade de utilizações.
- **Requisição**:
  ```json
    [{
    "userFirstName": "Lucas",
    "carModel": "Corolla",
    "utilizationCount": 3
    },
    {
    "userFirstName": "Ana",
    "carModel": "Kadett",
    "utilizationCount": 2
    },
    {
    "userFirstName": "Fernanda",
    "carModel": "Honda",
    "utilizationCount": 1
    }]
    ```  


# Instruções de Uso da Coleção do Postman

## Localização da Coleção
A coleção do Postman está localizada na pasta **`docs`** do projeto. O arquivo é chamado:

```
Auto Car (Desafio Pitang).postman_collection.json
```

Você pode importar este arquivo diretamente para o Postman para começar a usar os endpoints da aplicação.

---

## Link da Coleção no Postman

Confira o arquivo da coleção disponível em: [AutoCar.postman_collection.json](docs%2FAutoCar.postman_collection.json)

---

## Uso dos Endpoints (Dica de Sequencia no Postman)

### 01. **Realizar Login**
- **Endpoint**: `/api/signin`
- **Método**: `POST`
- **Descrição**: Realiza a autenticação do usuário e retorna um token JWT.
- **Requisição**:
  ```json
  {
    "username": "seu-usuario",
    "password": "sua-senha"
  }
  ```
- **Resposta**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```
- **Uso no Postman**:
    - Importe a coleção, abra a requisição **"Realizar login"** e insira os dados no corpo da requisição.
    - Após o login, o token será salvo automaticamente como variável de ambiente (`TOKEN`) para as próximas requisições.

---

### 02. **Listar Todos os Carros do Usuário Logado**
- **Endpoint**: `/api/cars`
- **Método**: `GET`
- **Descrição**: Retorna todos os carros associados ao usuário autenticado.
- **Requisição**:
    - Envie o cabeçalho:
      ```
      Authorization: Bearer {{TOKEN}}
      ```
- **Resposta**:
  ```json
  [
    {
      "id": 1,
      "model": "Corolla",
      "color": "Prata",
      "yearManufacture": 2020
    },
    {
      "id": 2,
      "model": "Civic",
      "color": "Preto",
      "yearManufacture": 2018
    }
  ]
  ```
- **Uso no Postman**:
    - Utilize a requisição **"Listar todos os carros do usuário logado"** na coleção importada.

---

### 03. **Buscar um Carro do Usuário Logado pelo ID**
- **Endpoint**: `/api/cars/{id}`
- **Método**: `GET`
- **Descrição**: Retorna as informações de um carro específico do usuário logado.
- **Parâmetro**:
    - Substitua `{id}` pelo ID do carro que deseja buscar.
- **Requisição**:
    - Envie o cabeçalho:
      ```
      Authorization: Bearer {{TOKEN}}
      ```
- **Resposta**:
  ```json
  {
    "id": 1,
    "model": "Corolla",
    "color": "Prata",
    "yearManufacture": 2020
  }
  ```
- **Uso no Postman**:
    - Use a requisição **"(+) Buscar um carro do usuário logado pelo id"**, atualizando o ID no endpoint conforme necessário.

---

### 04. **Carros Utilizados**
- **Endpoint**: `/api/cars_utilization`
- **Método**: `GET`
- **Descrição**: Retorna o ranking de usuários e seus carros baseados no número de utilizações.
- **Requisição**:
    - Envie o cabeçalho:
      ```
      Authorization: Bearer {{TOKEN}}
      ```
- **Resposta**:
  ```json
  [
    {
      "userFirstName": "Lucas",
      "carModel": "Corolla",
      "utilizationCount": 3
    },
    {
      "userFirstName": "Ana",
      "carModel": "Kadett",
      "utilizationCount": 2
    }
  ]
  ```
- **Uso no Postman**:
    - Selecione a requisição **"Carros utilizados"** na coleção importada.

---

### Observações sobre o Postman
1. **Configuração da Variável `BASE`**:
    - Certifique-se de configurar a variável `BASE` no Postman para apontar para o ambiente correto (local ou produção).

2. **Token JWT**:
    - O token JWT gerado após o login é salvo automaticamente na variável `TOKEN` do Postman, permitindo uso contínuo sem necessidade de copiar manualmente.

3. **Envirements**:
    - LOCAL: Para testar localmente, a variável `BASE` possui o valor `localhost:8080`.
    - HEROKU-PRD: Para testar em produção, a variável `BASE` possui o valor `user-auto-backend-2dd11eab029e.herokuapp.com`.

---

## Considerações Finais
Esta API foi desenvolvida para permitir o gerenciamento de informações de usuários e seus respectivos carros. Todas as requisições requerem um token de autenticação válido, que deve ser obtido previamente por meio do endpoint de login.

Caso tenha dúvidas sobre como utilizar algum endpoint, entre em contato com o autor ```+55 83 996283529 (Thiago  Gonçalo)``` 



