# WideTalk - Introdução a Apache Kafka

Este repositório contém o código gerado no WideTalk. O sistema de estudo que foi proposta é uma aplicação para validar transação financeira utilizando
apenas Kafka no seu padrão pub/sub.

## Tecnologias ultilizadas
- Apache Kafka
- Spring Boot
- Spring Kafka Integration
- Spring Web

## Como rodar o projeto

1) Faça download do Apache Kafka e crie os tópicos "new_transaction" e "checked_transaction"
2) Dentro da pasta de cada projeto execute <b>mvn spring-boot:run</b>

## Como usar o projetos

#### Gerar uma transação:

[POST] http://localhost:8090/transactions

Body:
```json
{
	"id": 1,
	"value": 1987.90,
	"user": "leo"
}
``` 
#### Consultar uma transação
[GET] http://localhost:8091/transactions/{id}

Response:
```json
{
	"id": 1,
	"value": 1987.90,
	"user": "leo",
	"status": "APPROVED"
}
```
#### Consultar todas transações
[GET] http://localhost:8091/transactions

Response:
```json
[
    {
        "id": 1,
        "value": 1987.90,
        "user": "leo",
        "status": "APPROVED"
    }
]
```

## Arquitetura

Utilizamos o padrão <a href="https://martinfowler.com/bliki/CQRS.html">CQRS (Command Query Responsibility Segregation)</a> e criamos de forma simples um sistem que aprova transações financeiros de forma assíncrona.

Abaixo imagem para ilustrar nossa solução:

![Arquitetura](https://raw.githubusercontent.com/wideti/widetalk-apache-kafka-introducao/master/curso_kafka-09%20-%20Projeto%20inicial.jpg)

#### Transaction producer

Este serviço é uma API Rest e um Kafka Producer ao mesmo tempo, é responsável por receber uma transação
da view e encaminhar para o Kafka processar a transação.

#### Transaction validation service

É um serviço que funciona como <b>consumer</b> e <b>producer</b> Kafka ele ouve o tópico "new_transaction" processa a 
transação e encaminha para o tópico "checked_transaction".

#### Transaction manager service

Este serviço guarda as transações baseadas em seu ID, e oferece uma API REST para a View consultar o estado da transação.

