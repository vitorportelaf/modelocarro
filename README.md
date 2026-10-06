# modelocarro

API REST desenvolvida em **Java 17 + Spring Boot** para gerenciamento de **marcas** e
**modelos** de carros, com persistência em **SQL Server** utilizando **Spring Data JPA**
(Hibernate).

Checkpoint 2 — Microservices and Web Engineering (2º semestre/2026).

---

## Integrantes

| Nome completo | RM |
|---------------|----|
|Vitor Portela Fantinto   | RM554540 |
| Nicolas Varella Barros Padovam   | RM556586 |

---

## Tecnologias

- Java 17
- Spring Boot 4 (Web MVC, Validation, Data JPA)
- Spring Data JPA / Hibernate (`SQLServerDialect`)
- Driver JDBC Microsoft SQL Server (`com.microsoft.sqlserver:mssql-jdbc`)
- SpringDoc OpenAPI (Swagger UI)
- Lombok e ModelMapper
- Maven (wrapper `mvnw` incluso)
- Docker (opcional)

---

## Estrutura do projeto

```
src/main/java/br/com/fiap/vitorportelaf/modelocarro
├── Application.java          # classe principal do Spring Boot
├── controller/               # endpoints REST (MarcaController, ModeloController, PingController)
├── dto/                      # objetos de requisição/resposta (Create, Update, Response)
├── mapper/                   # conversão DTO <-> entidade (ModelMapper)
├── model/                    # entidades JPA mapeadas para as tabelas (Marca, Modelo)
├── repository/               # interfaces Spring Data JPA (JpaRepository)
└── service/                  # regras de negócio / acesso aos repositórios

src/main/resources
├── application-dev.properties   # profile dev  -> SQL Server local (valores fixos)
├── application-prd.properties   # profile prd  -> SQL Server via variáveis de ambiente
└── application.properties       # configuração base
```

### Persistência

| Entidade | Tabela no SQL Server | Colunas |
|----------|----------------------|---------|
| `Marca`  | `marcas`  | `id`, `nome_marca`, `pais_origem`, `ano_fundacao`, `site_oficial`, `ativa` |
| `Modelo` | `modelos` | `id`, `nome_modelo`, `ano_lancamento`, `tipo_combustivel`, `preco_base`, `observacoes` |

As tabelas são criadas/atualizadas automaticamente pelo Hibernate
(`spring.jpa.hibernate.ddl-auto=update`) na primeira execução. Só é necessário que o
**database** já exista no SQL Server (veja o passo 1 abaixo).

---

## Pré-requisitos

- JDK 17+
- Docker (para subir o SQL Server localmente) **ou** acesso a um SQL Server remoto
- (Opcional) Postman, Insomnia ou `curl` para testar os endpoints

---

## 1. Subir o SQL Server

### Opção A — SQL Server local via Docker

```bash
docker run -d \
  --name sqlserver \
  -e "ACCEPT_EULA=Y" \
  -e "MSSQL_SA_PASSWORD=1q2w3e4R@" \
  -p 1433:1433 \
  mcr.microsoft.com/mssql/server:2022-latest
```

Aguarde ~20 segundos para o servidor inicializar e crie o banco `api`:

```bash
docker exec -it sqlserver /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "1q2w3e4R@" -C \
  -Q "CREATE DATABASE api"
```

> O SQL Server **não** cria o database automaticamente; o comando acima é obrigatório
> antes de iniciar a aplicação. As tabelas são criadas pela própria aplicação.

### Opção B — SQL Server remoto / já existente

Basta ter um database criado e um usuário com permissão de criar tabelas e ler/gravar
dados. Use o **profile `prd`** (seção 2) informando os dados de conexão por variáveis
de ambiente.

---

## 2. Configuração da conexão com o SQL Server

A conexão é configurada nos arquivos de profile em `src/main/resources`.

### Profile `dev` (SQL Server local — padrão do Docker acima)

`src/main/resources/application-dev.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=api;encrypt=false;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=1q2w3e4R@
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

Informações de conexão do profile `dev`:

| Parâmetro | Valor |
|-----------|-------|
| Host      | `localhost` |
| Porta     | `1433` |
| Database  | `api` |
| Usuário   | `sa` |
| Senha     | `1q2w3e4R@` |
| Opções    | `encrypt=false;trustServerCertificate=true` |

Para usar outro servidor no profile `dev`, basta alterar esses valores no arquivo.

### Profile `prd` (SQL Server remoto via variáveis de ambiente)

`src/main/resources/application-prd.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://${DB_SERVER_URL}:${DB_SERVER_PORT};databaseName=${DB_SCHEMA};encrypt=false;trustServerCertificate=true
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PWD}
```

| Variável                 | Descrição                         | Exemplo |
|--------------------------|-----------------------------------|---------|
| `DB_SERVER_URL`          | host do SQL Server                | `localhost` |
| `DB_SERVER_PORT`         | porta do SQL Server               | `1433` |
| `DB_SCHEMA`              | nome do database                  | `api` |
| `DB_USER`                | usuário                           | `sa` |
| `DB_PWD`                 | senha                             | `1q2w3e4R@` |
| `SPRING_PROFILES_ACTIVE` | profile ativo                     | `prd` |

Linux / macOS:

```bash
export DB_SERVER_URL=localhost
export DB_SERVER_PORT=1433
export DB_SCHEMA=api
export DB_USER=sa
export DB_PWD='1q2w3e4R@'
export SPRING_PROFILES_ACTIVE=prd
```

Windows PowerShell:

```powershell
$env:DB_SERVER_URL="localhost"
$env:DB_SERVER_PORT="1433"
$env:DB_SCHEMA="api"
$env:DB_USER="sa"
$env:DB_PWD="1q2w3e4R@"
$env:SPRING_PROFILES_ACTIVE="prd"
```

> **Importante:** sempre execute a aplicação com um profile ativo (`dev` ou `prd`).
> São esses profiles que configuram o SQL Server.

---

## 3. Executar a aplicação

Na raiz do projeto:

Linux / macOS:

```bash
# profile dev (SQL Server local)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# ou profile prd (após exportar as variáveis da seção 2)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prd
```

Windows:

```powershell
mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

A aplicação sobe em **http://localhost:8080**. No log aparecem os comandos SQL
executados pelo Hibernate (`show-sql=true`), incluindo a criação das tabelas `marcas`
e `modelos` no SQL Server.

### Executar com Docker (opcional)

```bash
docker build -t modelocarro:1.0 .

docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_SERVER_URL=host.docker.internal \
  -e DB_SERVER_PORT=1433 \
  -e DB_SCHEMA=api \
  -e DB_USER=sa \
  -e DB_PWD='1q2w3e4R@' \
  modelocarro:1.0
```

> Dentro do container, `localhost` aponta para o próprio container; por isso use o
> profile `prd` com `DB_SERVER_URL=host.docker.internal` (SQL Server no host) ou o
> endereço do servidor remoto. No Linux, adicione `--add-host=host.docker.internal:host-gateway`.

---

## 4. Testar a API

### Swagger UI

Com a aplicação rodando, acesse: **http://localhost:8080/**

Todos os endpoints podem ser testados diretamente pelo Swagger.

### Endpoints disponíveis

Base: `http://localhost:8080`

| Método | Endpoint                 | Descrição                    | Sucesso | Erro |
|--------|--------------------------|------------------------------|---------|------|
| GET    | `/ping`                  | health check (retorna `pong`) | 200 | — |
| POST   | `/api/v1/marcas`         | cadastra uma marca           | 201 | 400 (validação) |
| GET    | `/api/v1/marcas`         | lista todas as marcas        | 200 | — |
| GET    | `/api/v1/marcas/{id}`    | busca marca por id           | 200 | 404 |
| PUT    | `/api/v1/marcas/{id}`    | altera uma marca             | 200 | 404 |
| DELETE | `/api/v1/marcas/{id}`    | exclui uma marca             | 204 | 404 |
| POST   | `/api/v1/modelos`        | cadastra um modelo           | 201 | 400 (validação) |
| GET    | `/api/v1/modelos`        | lista todos os modelos       | 200 | — |
| GET    | `/api/v1/modelos/{id}`   | busca modelo por id          | 200 | 404 |
| PUT    | `/api/v1/modelos/{id}`   | altera um modelo             | 200 | 404 |
| DELETE | `/api/v1/modelos/{id}`   | exclui um modelo             | 204 | 404 |

> O `id` é informado pelo cliente no POST (não é gerado automaticamente).
> No PUT, envie **todos** os campos do recurso.

### Roteiro de teste com `curl`

**Marcas**

```bash
# inserir
curl -X POST http://localhost:8080/api/v1/marcas \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"Toyota","paisOrigem":"Japão","anoFundacao":1937,"siteOficial":"https://www.toyota.com","ativa":true}'

# consultar todas
curl http://localhost:8080/api/v1/marcas

# consultar por id
curl http://localhost:8080/api/v1/marcas/1

# alterar
curl -X PUT http://localhost:8080/api/v1/marcas/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Toyota Motor","paisOrigem":"Japão","anoFundacao":1937,"siteOficial":"https://global.toyota","ativa":true}'

# excluir
curl -X DELETE http://localhost:8080/api/v1/marcas/1
```

**Modelos**

```bash
# inserir
curl -X POST http://localhost:8080/api/v1/modelos \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"Corolla","anoLancamento":2024,"tipoCombustivel":"Flex","precoBase":150000.0,"observacoes":"Versão XEi"}'

# consultar todos
curl http://localhost:8080/api/v1/modelos

# consultar por id
curl http://localhost:8080/api/v1/modelos/1

# alterar
curl -X PUT http://localhost:8080/api/v1/modelos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Corolla Hybrid","anoLancamento":2025,"tipoCombustivel":"Híbrido","precoBase":185000.0,"observacoes":"Versão Altis"}'

# excluir
curl -X DELETE http://localhost:8080/api/v1/modelos/1
```

### Validação

Exemplo de requisição inválida (campos obrigatórios ausentes) — retorna **400 Bad Request**:

```bash
curl -i -X POST http://localhost:8080/api/v1/marcas \
  -H "Content-Type: application/json" \
  -d '{"id":2}'
```

---

## 5. Conferir os dados diretamente no SQL Server

Para comprovar que a API está gravando no banco, após um POST execute:

```bash
docker exec -it sqlserver /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "1q2w3e4R@" -C -d api \
  -Q "SELECT * FROM marcas; SELECT * FROM modelos;"
```

Também é possível conectar com DBeaver, Azure Data Studio ou SSMS usando os dados de
conexão da seção 2 (host `localhost`, porta `1433`, database `api`, usuário `sa`,
senha `1q2w3e4R@`, marcar *Trust Server Certificate*).

---

## 6. Encerrar

- Aplicação: `Ctrl + C` no terminal.
- SQL Server:

```bash
docker stop sqlserver
docker rm sqlserver
```
