# modelocarro

API RESTful em Spring Boot para gerenciamento de marcas e modelos de carros, com persistência em MySQL.

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
docker stop mysql-modelocarro
```

### 5. Banco de Dados (Opcional para vizualização)
- Ao rodar o Docker e a Aplicação, abra o DBeaver e faça uma nova conexão
- Selecione o MySQL e clique em "Next >"
- Insira apenas a senha "root_pwd", não é necessário mudar mais nada
- Clique em "DriverProperties" logo acima, ao lado de "Main" e clique em "Download"
- Procure por "allowPublicKeyRetrieval" e deixe com o valor "true"
- Clique em "Finish" e abra o banco