# Encurtador de URL

## Interface

<p align="center">
    <img src="./docs/tela-principal.png" width="900"/>
</p>

## Descrição

Aplicação web desenvolvida em Java para encurtamento de URLs.

O sistema permite cadastrar uma URL original, gerar um código curto automaticamente ou utilizar um alias personalizado, armazenar os registros em banco de dados e redirecionar o usuário para a URL original quando a URL encurtada for acessada.

Além da interface web, a aplicação disponibiliza uma API REST para integração com outros sistemas.

---

# Funcionalidades

* Cadastro de URL original.
* Geração automática de código curto.
* Utilização de alias personalizado.
* Validação de alias duplicado.
* Listar as URLs cadastradas (API)
* Ajuste automático do protocolo (`https://`) quando não informado.
* Persistência das URLs em banco H2.
* Cópia da URL encurtada para a área de transferência.
* Redirecionamento HTTP para a URL original.
* Página personalizada para URLs inexistentes.
* Exclusão de todas as URLs cadastradas mediante confirmação.

---

# Tecnologias utilizadas

## Back-end

* Java 8
* Jakarta EE / Java EE 8
* JSF
* JAX-RS (REST)
* JPA (Hibernate)
* CDI
* Servlet

## Front-end

* PrimeFaces 12
* XHTML
* CSS
* JavaScript

## Banco de dados

* H2 Database

## Servidor de aplicação

* WildFly 26

## Build

* Maven

---

# Arquitetura

O projeto foi organizado em camadas para facilitar manutenção e evolução.

```
View (JSF + PrimeFaces)

        ↓

ManagedBean

        ↓

Service

        ↓

DAO

        ↓

JPA / Hibernate

        ↓

Banco H2
```

### Responsabilidade de cada camada

**ManagedBean**

Responsável pela interação com a interface JSF.

**Service**

Centraliza as regras de negócio, como:

* geração do código curto;
* validação de alias;
* normalização da URL;
* regras de criação da URL encurtada.

**DAO**

Responsável exclusivamente pelo acesso aos dados utilizando JPA.

Essa separação evita que regras de negócio fiquem espalhadas pela aplicação e facilita futuras evoluções.

---

# API REST

## Criar URL

```
POST /api/urls
```

Exemplo:

alias é opcional

```json
{
    "urlOriginal":"https://www.google.com",
    "alias":"google"
}
```

Resposta:

```json
{
    "codigo":"google",
    "urlEncurtada":"http://localhost:8080/encurtadorurl-1.0/r/google"
}
```

---

## Buscar todas as URLs cadastradas

```
GET /api/urls/
```

Resposta

```json
[
    {
        "codigo": "youtube",
        "dataCriacao": "2026-07-29T17:32:57.530642",
        "dataCriacaoFormatada": "29/07/2026 17:32",
        "id": 43,
        "urlOriginal": "https://www.youtube.com"
    },
    {
        "codigo": "google",
        "dataCriacao": "2026-07-29T17:32:43.83971",
        "dataCriacaoFormatada": "29/07/2026 17:32",
        "id": 42,
        "urlOriginal": "https://www.google.com"
    }
]
```

## Deletar todas as URLs cadastradas

```
DELETE /api/urls/
```

Retorna 204 No Content


---

# Como executar

## Requisitos

* Java 8
* Maven
* WildFly 26

## Passos

1. Clonar o projeto.

2. Executar:

```
mvn clean package
```

3. Gerar o arquivo WAR.

4. Realizar o deploy no WildFly.

5. Acessar:

```
http://localhost:8080/encurtadorurl-1.0
```

---

# Decisões de implementação

Durante o desenvolvimento foram adotadas algumas decisões visando simplicidade e boa organização.

* Utilização de JSF + PrimeFaces para construção da interface.
* Persistência em banco H2 por dispensar instalação de banco externo.
* Separação em camadas (View, Service e DAO).
* Geração automática de códigos utilizando UUID reduzido.
* Possibilidade de utilização de alias personalizado.
* Construção dinâmica da URL encurtada utilizando informações da requisição.
* API REST independente da interface web.
* Página de erro personalizada para códigos inexistentes.

---

# Requisito de processamento sincronizado

O desafio solicita que o motor de geração processe apenas uma requisição por vez.

Essa regra foi implementada na camada de serviço através de sincronização do processo de criação da URL, garantindo que duas requisições concorrentes não gerem conflitos durante a criação de novos códigos.

---

# Melhorias futuras

Caso houvesse mais tempo para evolução do projeto, seriam implementadas as seguintes funcionalidades:

* Testes unitários utilizando JUnit e Mockito.
* Testes de integração para API REST.
* Docker para execução simplificada da aplicação.
* Banco PostgreSQL ou MySQL para ambiente de produção.
* Geração de códigos utilizando algoritmo Base62 para redução do tamanho das URLs.


