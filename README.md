# 🏡 API Urban Valle

API RESTful desenvolvida com Spring Boot e Maven para gerenciar informações relacionadas ao sistema imobiliário *Urban Valle*. Esta API permite cadastro e gerenciamento de usuários, imóveis, consultores e contatos

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **Maven**
- **PostgreSQL**
- **JPA/Hibernate**
- **Lombok**
- **Swagger**
  
---

## ⚙️ Como Rodar Localmente

### Pré-requisitos:

* Java 17+
* Maven instalado
* PostgreSQL rodando (local ou remoto)

### 1. Clone o projeto

```bash
git clone https://github.com/MinYonhee/api-urban-valle.git
cd api-urban-valle
```

### 2. Configure o `application.properties`

Em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urbanvalle
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

server.port=8080
```

> ✅ Dica: se estiver usando Back4App, use a URL, usuário e senha de lá.

### 3. Rode a aplicação

```bash
./mvnw spring-boot:run
```

---

# 🔗 Endpoints Disponíveis

## 👤 Usuários

```
GET    /usuarios
GET    /usuarios/{id}
POST   /usuarios
PUT    /usuarios/{id}
DELETE /usuarios/{id}
```

## 🏘️ Imóveis

```
GET    /imoveis
GET    /imoveis/{id}
POST   /imoveis
PUT    /imoveis/{id}
DELETE /imoveis/{id}
```

## 🧑‍💼 Consultores

```
GET    /consultores
GET    /consultores/{id}
POST   /consultores
PUT    /consultores/{id}
DELETE /consultores/{id}
```

## 📞 Contatos

```
GET    /contatos
GET    /contatos/{id}
POST   /contatos
PUT    /contatos/{id}
DELETE /contatos/{id}
```

---

## 🧪 Testes

Recomenda-se o uso de ferramentas como:

- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)


## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
