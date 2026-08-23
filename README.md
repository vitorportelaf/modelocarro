# modelocarro

API RESTful em Spring Boot para gerenciamento de marcas e modelos de carros, com persistência em MySQL.

## Checkpoint 3 — Refatoração com DTOs e Mappers

Nesta entrega o projeto do Checkpoint 2 foi refatorado para aplicar a separação de
responsabilidades entre o modelo de persistência (Entities JPA) e o contrato da API (DTOs).
As entidades, os repositories e as rotas do CRUD continuam os mesmos — o que mudou foi
a forma como a API recebe e retorna os dados.

### O que mudou

- **DTOs de request** (`dto`): `MarcaCreateRequest`, `MarcaUpdateRequest`,
  `ModeloCreateRequest` e `ModeloUpdateRequest`. As Controllers não recebem mais entidades
  JPA no corpo das requisições. Os DTOs de criação possuem validações
  (Bean Validation: `@NotNull`, `@NotBlank`, `@Size`, `@Positive`, `@PositiveOrZero`).
- **DTOs de response** (`dto`): `MarcaResponse` e `ModeloResponse`. Todas as operações de
  criação, consulta, atualização e listagem retornam DTOs — nenhuma entidade JPA é mais
  exposta nas respostas.
- **Mappers** (`mapper`): `MarcaMapper` e `ModeloMapper` centralizam toda a conversão
  entre DTOs e Entities usando ModelMapper (`toModel`, `toModel(id, dto)` e `toDto`).
- **Controllers refatoradas**: `MarcaController` e `ModeloController` agora recebem DTOs
  de request, delegam a conversão para os Mappers e retornam DTOs de response.
  As rotas de leitura (`findAll` e `findById`) continuam usando o verbo **GET**.
- **Dependências novas** no `pom.xml`: `modelmapper` e `spring-boot-starter-validation`.

### Estrutura de pacotes

```
br.com.fiap.vitorportelaf.modelocarro
├── controller    → MarcaController, ModeloController
├── dto           → Create/Update Requests e Responses
├── mapper        → MarcaMapper, ModeloMapper
├── model         → Entities JPA (Marca, Modelo)
└── repository    → MarcaRepository, ModeloRepository
```

### Rotas da API

| Método | Rota                     | Corpo (request)       | Retorno (response)     |
|--------|--------------------------|-----------------------|------------------------|
| POST   | `/api/v1/marcas`         | `MarcaCreateRequest`  | `MarcaResponse`        |
| GET    | `/api/v1/marcas`         | —                     | `List<MarcaResponse>`  |
| GET    | `/api/v1/marcas/{id}`    | —                     | `MarcaResponse`        |
| PUT    | `/api/v1/marcas/{id}`    | `MarcaUpdateRequest`  | `MarcaResponse`        |
| DELETE | `/api/v1/marcas/{id}`    | —                     | —                      |
| POST   | `/api/v1/modelos`        | `ModeloCreateRequest` | `ModeloResponse`       |
| GET    | `/api/v1/modelos`        | —                     | `List<ModeloResponse>` |
| GET    | `/api/v1/modelos/{id}`   | —                     | `ModeloResponse`       |
| PUT    | `/api/v1/modelos/{id}`   | `ModeloUpdateRequest` | `ModeloResponse`       |
| DELETE | `/api/v1/modelos/{id}`   | —                     | —                      |

Exemplo de corpo para criar uma marca:

```json
{
  "id": 1,
  "nome": "Volkswagen",
  "paisOrigem": "Alemanha",
  "anoFundacao": 1937,
  "siteOficial": "https://www.vw.com.br",
  "ativa": true
}
```

Exemplo de corpo para criar um modelo:

```json
{
  "id": 1,
  "nome": "Golf GTI",
  "anoLancamento": 2023,
  "tipoCombustivel": "Gasolina",
  "precoBase": 259990.00,
  "observacoes": "Versao esportiva"
}
```

## Como rodar a aplicação

### 1. Subir o banco de dados (MySQL via Docker)

Com o Docker Desktop em execução, rode no terminal:

```bash
docker run -d \
    --name mysql \
    --rm \
    -e MYSQL_ROOT_PASSWORD=root_pwd \
    -e MYSQL_USER=new_user \
    -e MYSQL_PASSWORD=my_pwd \
    -p 3306:3306 \
    mysql
```

Aguarde cerca de 30 segundos para o MySQL inicializar.

### 2. Rodar a aplicação Spring Boot

Na raiz do projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação sobe na porta **8080**.

### 3. Acessar o Swagger

```
http://localhost:8080/
```

### 4. Parar tudo

- Aplicação Spring Boot: `Ctrl + C` no terminal.
- Banco de dados:

```bash
docker stop mysql
```

### 5. Banco de Dados (Opcional para vizualização)
- Ao rodar o Docker e a Aplicação, abra o DBeaver e faça uma nova conexão
- Selecione o MySQL e clique em "Next >"
- Insira apenas a senha "root_pwd", não é necessário mudar mais nada
- Clique em "DriverProperties" logo acima, ao lado de "Main" e clique em "Download"
- Procure por "allowPublicKeyRetrieval" e deixe com o valor "true"
- Clique em "Finish" e abra o banco