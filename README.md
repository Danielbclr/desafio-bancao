# Desafio de Programação - API Bancária

Este projeto é uma API REST para gerenciar transações bancárias e gerar estatísticas, idealizado como experiência de aprendizado. É desenvolvido em Java 21 com Spring Boot 3. O projeto é baseado no desafio: [rafaellins-itau/desafio-itau-vaga-99-junior](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

## 💡 Funcionalidades

*   **`POST /transacao`**: Registra uma nova transação bancária.
*   **`DELETE /transacao`**: Limpa todas as transações registradas.
*   **`GET /estatistica`**: Retorna as estatísticas das transações (soma, média, máximo, mínimo, quantidade) do último minuto.

## 🚀 Tecnologias Utilizadas

*   Java 21
*   Spring Boot 3
*   Gradle
*   Docker
*   Lombok
*   SpringDoc OpenAPI (Swagger UI)
*   Spring Actuator

## Pré-requisitos

*   JDK 21 ou superior
*   Gradle 8.x (o wrapper `./gradlew` é incluído)
*   Docker (para execução via container)

## ⚡ Como Executar

### Localmente (via Gradle)

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/desafio-bancao.git
    cd desafio-bancao
    ```
2.  **Construa o projeto:**
    ```bash
    ./gradlew build
    ```
    *No Windows, use `.\gradlew build`*
3.  **Execute a aplicação:**
    ```bash
    java -jar build/libs/desafio-bancao-0.0.1-SNAPSHOT.jar
    ```

A API estará disponível em `http://localhost:8080`.

### 🐳 Via Docker

1.  **Clone o repositório (se ainda não o fez):**
    ```bash
    git clone https://github.com/seu-usuario/desafio-bancao.git
    cd desafio-bancao
    ```
2.  **Construa a imagem Docker:**
    Certifique-se de que o arquivo JAR foi criado primeiro com `./gradlew build`.
    ```bash
    docker build -t desafio-bancao .
    ```
3.  **Execute o container Docker:**
    ```bash
    docker run -d -p 8080:8080 --name desafio-bancao-app desafio-bancao
    ```

A API estará disponível em `http://localhost:8080`.

## 📄 Acessando a Documentação da API (Swagger UI)

Com a aplicação em execução (localmente ou via Docker), acesse a documentação interativa da API (Swagger UI) no seu navegador:

`http://localhost:8080/swagger-ui.html`

## 🗺️ Roadmap de Features
- [x] Funcionalidades básicas da API
   - [x] Endpoint `POST /transacao`
   - [x] Endpoint `DELETE /transacao`
   - [x] Endpoint `GET /estatistica`
- [ ] Unit tests
- [x] Dockerização
- [x] Logs
- [x] Observabilidade (Actuator)
- [x] Tratamento de Erros mais robusto
- [x] Documentação da API (Swagger)
- [x] Documentação do projeto (README)
- [x] Configuração do endpoint `GET /estatistica` (cálculo no último minuto)
