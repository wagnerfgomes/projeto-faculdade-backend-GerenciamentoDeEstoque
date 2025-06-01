# API de Gerenciamento de Estoque

API para controle de produtos, categorias e usuários em um sistema de estoque.

## Autores

- Wagner Felipe Gomes Ferreira
- Matheus Henrique Costa Xavier
- Caio Nazário de Barros Antão

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Lombok
- Swagger/OpenAPI

## Como executar

1. Clone o repositório:

   ```sh
   git clone https://github.com/seu-usuario/api-Gerenciamento-de-estoque.git
   ```

2. Configure o banco de dados:

   - Usando o Docker Compose:

   ```sh
   cd docker
   ```

   ```docker
   docker docker-compose up -d
   ```

   Configure o arquivo `application.properties` com as configurações do banco de dados.

   ```Yml
      # Configuração do DataSource
      spring.datasource.url=jdbc:mysql://localhost:3306/estoquedb #endereço do banco e nome do banco
      spring.datasource.username=root #nome do usuário
      spring.datasource.password=root #senha do usuário
   ```

3. Execute a aplicação:

   ```sh
   ./mvnw spring-boot:run
   ```

4. Acesse a documentação Swagger em:  
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints principais

### Autenticação

- `POST /auth/login` — Login e obtenção do token JWT
- `POST /auth/register` — Cadastro de usuário

### Usuários

- `GET /user?username={username}` — Buscar usuário
- `GET /user/all` — Listar usuários
- `PUT /user/update` — Atualizar usuário

### Produtos

- `POST /produto/register` — Cadastrar produto
- `GET /produto/listar` — Listar produtos
- `PUT /produto/update` — Atualizar produto
- `PUT /produto/desativar` — Desativar produto

### Categorias

- `POST /categoryproduct/create` — Cadastrar categoria
- `GET /categoryproduct/list` — Listar categorias
- `GET /categoryproduct?name={name}` — Buscar categoria
- `PUT /categoryproduct/update` — Atualizar categoria
- `DELETE /categoryproduct/delete/{name}` — Remover categoria

---
Projeto acadêmico — Uso não comercial permitido.
[License :book:](https://github.com/wagnerfgomes/projeto-faculdade-backend-GerenciamentoDeEstoque/blob/master/license)
