# modelocarro

API RESTful em Spring Boot para gerenciamento de marcas e modelos de carros.

Este README foi reduzido para conter apenas o que é exigido na entrega: instruções
para executar a aplicação localmente (via Maven), instruções para rodar a imagem
Docker publicada (pull/run) com as variáveis necessárias, informação sobre
profiles e como acessar o Swagger/OpenAPI.

---

## Como rodar a aplicação (desenvolvimento)

1) Subir o banco MySQL (exemplo via Docker):

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

2) Rodar a aplicação localmente (na raiz do projeto):

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
mvnw.cmd spring-boot:run
```

A aplicação sobe na porta 8080.

## Acessar o Swagger/OpenAPI

Após a aplicação subir, o Swagger UI está disponível em:

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

---

## Variáveis de ambiente

### Linux / macOS

```sh
export DB_SERVER_URL=localhost
export DB_SERVER_PORT=3306
export DB_SCHEMA=modelocarro
export DB_USER=root
export DB_PWD=root_pwd
export SPRING_PROFILES_ACTIVE=dev
```

### Windows PowerShell

```powershell
$env:DB_SERVER_URL="localhost"
$env:DB_SERVER_PORT="3306"
$env:DB_SCHEMA="modelocarro"
$env:DB_USER="root"
$env:DB_PWD="root_pwd"
$env:SPRING_PROFILES_ACTIVE="dev"
```

---

## Profile `prd` e migração

Quando `SPRING_PROFILES_ACTIVE=prd` o projeto usa `spring.jpa.hibernate.ddl-auto=none`.
Ou seja, o Hibernate NÃO cria ou atualiza tabelas nesse profile. Antes de executar a
aplicação com `prd`, crie o schema/tabelas no banco executando o script de migration:
`src/main/resources/migration-2026-08-23.sql`.


## Execução com Docker

Além do banco, a própria aplicação pode rodar em container.

### 1. Criar a imagem

Na raiz do projeto:

```sh
docker build -t modelocarro:1.0 .
```

### 2. Baixar imagem do Docker Hub (se aplicável)

Se a imagem estiver publicada no Docker Hub, baixe-a substituindo `<usuario>` e `<tag>`:

```sh
docker pull <usuario>/modelocarro:<tag>
```

2) Executar a imagem (mapear porta 8080 e passar variáveis):

```sh
docker run \
  -p 8080:8080 \
  -e DB_SERVER_URL=host.docker.internal \
  -e DB_SERVER_PORT=3306 \
  -e DB_SCHEMA=modelocarro \
  -e DB_USER=root \
  -e DB_PWD=root_pwd \
  -e SPRING_PROFILES_ACTIVE=dev \
  modelocarro:1.0
```