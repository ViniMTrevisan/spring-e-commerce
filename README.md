# API de E-commerce com Spring Boot

![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)
![Spring%20Boot](https://img.shields.io/badge/Spring%20Boot-3.x.x-brightgreen.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20RDS-orange.svg)

Uma API RESTful robusta para uma plataforma de e-commerce, construída com Spring Boot. O projeto inclui um sistema completo de autenticação JWT, autorização baseada em papéis (Role-Based), integração de pagamentos com Stripe e está pronto para ser conteinerizado e implantado na nuvem.

## ✨ Funcionalidades Principais

* **Autenticação JWT Completa:**
    * Login com `Access Token` (curta duração) para segurança.
    * Geração de `Refresh Token` (longa duração) armazenado em um cookie `HttpOnly` para renovação automática do token de acesso.
* **Autorização Baseada em Papéis (Roles):**
    * Distinção entre usuários (`USER`) e administradores (`ADMIN`).
    * Endpoints protegidos que só podem ser acessados por administradores (ex: `/admin/**`).
* **Segurança:**
    * Hashing de senhas com `BCryptPasswordEncoder`.
    * Gerenciamento seguro de chaves e segredos (`.env`) usando `spring-dotenv`.
* **Gerenciamento de Usuários:**
    * Registro de novos usuários.
    * Endpoint protegido `/auth/me` para buscar informações do usuário logado.
* **Integração com Pagamentos (Stripe):**
    * Criação de sessões de checkout do Stripe.
    * Endpoint de Webhook para receber e validar confirmações de pagamento do Stripe, garantindo a segurança com a `STRIPE_WEBHOOK_SECRET_KEY`.
* **Gerenciamento de Carrinho e Produtos:**
    * Endpoints (protegidos) para gerenciar carrinhos de compra, produtos, etc.

---

## 🚀 Arquitetura e Tecnologias

Este projeto utiliza uma arquitetura moderna e escalável, pronta para a nuvem.

* **Backend:** **Spring Boot 3**
    * **Spring Web:** Para a criação de controladores RESTful.
    * **Spring Security 6:** Para gerenciamento de autenticação e autorização.
    * **Spring Data JPA:** Para persistência de dados com o banco.
    * **JWT (jjwt):** Para geração e validação dos tokens.
* **Banco de Dados:** **MySQL**
* **Pagamentos:** **Stripe API**
* **Containerização:** **Docker** e **Docker Compose**
* **Documentação:** **Swagger (OpenAPI 3)**

### ☁️ Arquitetura de Deploy (AWS)

A aplicação foi projetada para ser implantada na AWS com a seguinte arquitetura:

* **Backend (AWS EC2):** A aplicação Spring Boot é conteinerizada usando Docker e executada em uma instância **Amazon EC2**. O **Docker Compose** é utilizado para orquestrar o container da aplicação.
* **Banco de Dados (AWS RDS):** O banco de dados MySQL é hospedado como um serviço gerenciado no **Amazon RDS**, garantindo alta disponibilidade, backups automáticos e escalabilidade.

---

## 📚 Documentação da API (Swagger)

A documentação completa da API, com todos os endpoints, modelos de requisição e resposta, é gerada automaticamente pelo Swagger.

Após iniciar a aplicação, a documentação pode ser acessada em:

➡️ **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## Endpoints Principais

Aqui estão os principais grupos de endpoints da API:

### Autenticação (Público)

* `POST /users`
    * Registra um novo usuário no sistema.
* `POST /auth/login`
    * Autentica um usuário e retorna um `Access Token` no corpo da resposta e um `Refresh Token` em um cookie `HttpOnly`.
* `POST /auth/refresh`
    * Utiliza o `Refresh Token` (enviado via cookie) para gerar um novo `Access Token`.

### Usuário (Protegido - `USER` ou `ADMIN`)

* `GET /auth/me`
    * Retorna os detalhes do usuário atualmente autenticado.
* `POST /checkout`
    * Cria uma sessão de pagamento no Stripe e retorna a URL de checkout.
* `GET /carts/**`, `POST /carts/**`
    * Endpoints para gerenciamento do carrinho de compras.

### Administrador (Protegido - `ADMIN`)

* `GET /admin/hello`
    * Endpoint de exemplo para testar o acesso de administrador.
* `GET /admin/**`
    * Outros endpoints de gerenciamento (ex: gerenciar produtos, usuários, etc.).

### Webhook (Stripe)

* `POST /stripe/webhook`
    * Endpoint que recebe eventos do Stripe (ex: `checkout.session.completed`) para confirmar pagamentos.

---

## ⚙️ Configuração e Execução Local

### Pré-requisitos

* Java 17+
* Maven ou Gradle
* Docker e Docker Compose
* Um arquivo `.env` (veja abaixo)

### 1. Configuração do Ambiente (`.env`)

Crie um arquivo chamado `.env` na **raiz do projeto** (no mesmo nível do `pom.xml`). A aplicação usa o `spring-dotenv` para carregar essas variáveis.

```ini
# Configuração do Banco de Dados
DB_URL=jdbc:mysql://localhost:3306/store_db
DB_USER=root
DB_PASSWORD=seu-password

# Chave Secreta do JWT (Use 'openssl rand -base64 32' para gerar)
JWT_SECRET=sua-chave-secreta-super-longa-e-segura-de-pelo-menos-256-bits

# Chaves do Stripe
STRIPE_API_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET_KEY=whsec_...
```

### 2. Executando com Docker Compose

A forma mais simples de subir todo o ambiente (API + Banco de Dados MySQL) é usando o ***Docker Compose***.

```bash
# Constrói as imagens e sobe os containers em background
docker-compose up -d --build
```

### Autor

* Desenvolvido por Vinicius Trevisan


### Licença

* Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.