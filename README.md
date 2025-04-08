# Desafio de Programação - API Bancária

[![Java CI with Gradle](https://github.com/nihansk/desafio-bancao/actions/workflows/gradle.yml/badge.svg)](https://github.com/nihansk/desafio-bancao/actions/workflows/gradle.yml) [![Docker Image CI](https://github.com/nihansk/desafio-bancao/actions/workflows/docker-image.yml/badge.svg)](https://github.com/nihansk/desafio-bancao/actions/workflows/docker-image.yml) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/4c98593f3b3741bb85c5c24462e6c19c)](https://app.codacy.com/gh/nihansk/desafio-bancao/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

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
*   JUnit 5 & Mockito (Testes)
*   JaCoCo (Cobertura de Testes)

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

## 🧪 Testes Unitários

O projeto utiliza JUnit 5 e Mockito para testes unitários, focando nas camadas de Controller e Service.

Para executar os testes, utilize o wrapper do Gradle:

```bash
./gradlew test
```
*No Windows, use `.\gradlew test`*

Isso executará todos os testes unitários definidos no diretório `src/test/java`.

## 📊 Cobertura de Testes (JaCoCo)

Utilizamos o plugin JaCoCo para medir a cobertura do código pelos testes unitários.

Após executar os testes (com `./gradlew test` ou `./gradlew build`), o relatório de cobertura é gerado automaticamente.

Para visualizar o relatório HTML, abra o seguinte arquivo no seu navegador:

`./build/reports/jacoco/test/html/index.html`

Este relatório detalha a cobertura por pacote, classe, método e linha.

## 🗺️ Roadmap de Features
- [x] Funcionalidades básicas da API
   - [x] Endpoint `POST /transacao`
   - [x] Endpoint `DELETE /transacao`
   - [x] Endpoint `GET /estatistica`
- [x] Unit tests (Service & Controller)
- [x] Dockerização
- [x] Logs
- [x] Observabilidade (Actuator)
- [x] Tratamento de Erros mais robusto
- [x] Documentação da API (Swagger)
- [x] Documentação do projeto (README)
- [x] Configuração do endpoint `GET /estatistica` (cálculo no último minuto)
- [x] Cobertura de Testes (JaCoCo)
