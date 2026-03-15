# ProjetoTreino2 API

API REST para gerenciamento de **autores**, **livros**, **clientes** e **usuários**, com autenticação JWT e Spring Security.

---

##  Stack

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4 |
| Spring Security + JWT (auth0) | — |
| Spring Data JPA | — |
| PostgreSQL | — |
| Maven Wrapper | — |

---

##  Como rodar localmente

**1. Crie o banco de dados no PostgreSQL:**
```sql
CREATE DATABASE projeto_curso2;
```

**2. Ajuste as credenciais em `src/main/resources/application.properties` (se necessário):**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/projeto_curso2
spring.datasource.username=postgres
spring.datasource.password=12345
```

**3. Execute a aplicação:**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

A aplicação sobe em: `http://localhost:8080`

---

##  Configurações importantes

Variáveis definidas em `src/main/resources/application.properties`:

| Variável | Valor padrão |
|---|---|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/projeto_curso2` |
| `spring.datasource.username` | `postgres` |
| `spring.datasource.password` | `12345` |
| `api.security.token.secret` | `${JWT_SECRET:my-secret-key}` |
| CORS origem permitida | `http://localhost:4200` |

>  Em produção, substitua o `JWT_SECRET` por uma variável de ambiente segura.

---

##  Autenticação

A API utiliza **JWT Bearer Token**. Inclua o token no header de todas as requisições protegidas:
```
Authorization: Bearer <TOKEN>
```

### Login
```http
POST /auth/login
```
**Body:**
```json
{
  "login": "admin",
  "password": "12345"
}
```
**Resposta:**
```json
{
  "token": "JWT_AQUI"
}
```

### Registro de usuário
```http
POST /auth/register
```
>  Requer role `ADMIN`

**Body:**
```json
{
  "login": "novo_usuario",
  "password": "12345",
  "role": "ADMIN"
}
```

---

##  Endpoints

###  Autores — `/api/autores`

| Método | Rota | Role |
|---|---|---|
| `POST` | `/create` | ADMIN |
| `PUT` | `/update/{id}` | ADMIN |
| `DELETE` | `/delete/{id}` | ADMIN |
| `GET` | `/findAll` | Autenticado |
| `GET` | `/findById/{id}` | Autenticado |
| `GET` | `/findByNome/{nome}` | Autenticado |

**Body exemplo:**
```json
{
  "nome": "Machado de Assis",
  "livros": [1, 2]
}
```

---

###  Clientes — `/api/clientes`

| Método | Rota | Role |
|---|---|---|
| `POST` | `/create` | ADMIN |
| `PUT` | `/update/{id}` | ADMIN |
| `DELETE` | `/delete/{id}` | ADMIN |
| `GET` | `/findAll` | Autenticado |
| `GET` | `/findById/{id}` | Autenticado |
| `GET` | `/findBynome/{nome}` | Autenticado |

**Body exemplo:**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "livro_id": [1, 2]
}
```

---

###  Livros — `/api/livros`

| Método | Rota | Role |
|---|---|---|
| `POST` | `/create` | ADMIN |
| `PUT` | `/update/{id}` | ADMIN |
| `DELETE` | `/delete/{id}` | ADMIN |
| `GET` | `/findAll` | Autenticado |
| `GET` | `/findById/{id}` | Autenticado |
| `GET` | `/findByTitulo/{titulo}` | Autenticado |
| `GET` | `/findByAnoPublicacao/{ano}` | Autenticado |
| `GET` | `/findByAnoPublicacaoMaiorQue/{ano}` | Autenticado |
| `GET` | `/findByAnoPublicacaoMenorQue/{ano}` | Autenticado |
| `GET` | `/findByAutor/{id}` | Autenticado |

**Body exemplo:**
```json
{
  "titulo": "Dom Casmurro",
  "autor_id": 1,
  "anoPublicacao": 1899,
  "cliente_id": [1]
}
```

---

###  Usuários — `/api/usuarios`

| Método | Rota | Role |
|---|---|---|
| `GET` | `/findAll` | Autenticado |
| `GET` | `/findByLogin/{nome}` | Autenticado |
| `DELETE` | `/delete/{id}` | ADMIN |

---

## 🔒 Observações de segurança

- Todas as rotas protegidas exigem JWT válido no header.
- `POST /auth/login` é público (sem token).

---

## 🧪 Testes
```bash
# Windows
mvnw.cmd test

# Linux/Mac
./mvnw test
```

Cobertura de testes configurada com **JaCoCo** no `pom.xml`.
