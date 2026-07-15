# Order Flow

Order Flow é um sistema de pedidos para restaurante, estruturado em microserviços que se comunicam de forma assíncrona.
O fluxo simula o dia a dia de um salão: o garçom registra o pedido, a cozinha recebe e prepara, e o status final volta
para quem atendeu a mesa.

Este README descreve a arquitetura, as tecnologias e os padrões utilizados no projeto como um todo. Cada microserviço
possui seu próprio README com detalhes específicos de implementação.

## Arquitetura

O projeto é dividido em dois microserviços independentes, que não se comunicam de forma síncrona entre si — toda a
integração acontece via eventos no Kafka:

- **garcom-service**: responsável por registrar e gerenciar os pedidos feitos nas mesas.
- **cozinha-service**: responsável por receber os pedidos e controlar o fluxo de preparo na cozinha.

Cada serviço possui seu próprio banco de dados, suas próprias migrations e seu próprio ciclo de vida, seguindo o
princípio de independência entre microserviços.

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot** — construção das APIs REST e da camada de aplicação
- **Spring Data JPA** — persistência e acesso a dados
- **Liquibase** — versionamento e controle de migrations do banco de dados
- **PostgreSQL** — banco de dados relacional em produção
- **Apache Kafka** — comunicação assíncrona entre os microserviços
- **Gradle** — gerenciamento de build e dependências
- **Lombok** — redução de boilerplate nas classes Java
- **H2 Database** — banco em memória utilizado exclusivamente nos testes de integração
- **Database Rider** — carga de datasets para os testes de integração
- **JUnit 5** — framework de testes

## Padrões e regras do projeto

- **Arquitetura orientada a eventos**: os microserviços não se conhecem diretamente. Toda comunicação entre
  `garcom-service` e `cozinha-service` acontece através de eventos publicados em tópicos do Kafka.
- **Transactional Outbox Pattern**: para garantir consistência entre a escrita no banco de dados e a publicação do
  evento no Kafka, cada serviço utiliza uma tabela de outbox. Veja a seção dedicada abaixo.
- **Separação em camadas**: cada serviço segue uma organização por responsabilidade (`controller`, `service`,
  `repository`, `domain`, `dto`, `outbox`, `kafka`), mantendo as regras de negócio isoladas na camada de serviço e
  validadores dedicados para as regras de validação.
- **Tratamento centralizado de exceções**: cada serviço possui um handler global (`@RestControllerAdvice`) que padroniza
  as respostas de erro da API, mapeando exceções de negócio para os status HTTP correspondentes.
- **Migrations versionadas com Liquibase**: toda alteração de schema é feita através de changelogs, garantindo
  rastreabilidade e reprodutibilidade do banco em qualquer ambiente.

## Como funciona o Outbox Pattern no projeto

Um dos maiores riscos em arquiteturas orientadas a eventos é a inconsistência entre "salvar no banco" e "publicar no
Kafka": se uma das duas operações falhar isoladamente, o sistema pode ficar com dados divergentes entre o serviço e o
restante da arquitetura. Para evitar esse problema, o projeto implementa o **Transactional Outbox Pattern** em ambos os
microserviços.

O funcionamento segue estes passos:

1. Quando uma ação relevante acontece (por exemplo, a criação de um pedido), o serviço salva a entidade de domínio **e**
   um registro de `OutboxEvent` na mesma transação do banco de dados. Esse registro guarda o tipo do evento, o payload
   já serializado em JSON e o status inicial `PENDENTE`.
2. Como a entidade principal e o evento de outbox são persistidos na mesma transação, ou os dois são gravados com
   sucesso, ou nenhum dos dois é — não existe cenário em que o pedido é salvo sem que o evento correspondente também
   seja.
3. Após o commit da transação, o serviço publica um evento interno da aplicação (`OutboxCreatedEvent`) usando o
   `ApplicationEventPublisher` do Spring.
4. Um listener assíncrono escuta esse evento, busca o registro correspondente na tabela de outbox, envia o payload para
   o tópico correto do Kafka através do `KafkaTemplate` e, em caso de sucesso, atualiza o status do registro para
   `ENVIADO`, junto com o timestamp de envio.

Esse desenho garante que a publicação no Kafka só é tentada depois que a mudança de estado já está garantida no banco, e
mantém um histórico auditável de todos os eventos que precisaram ou precisam ser enviados — o que também abre espaço
para, futuramente, reprocessar eventos que ficaram pendentes.

## Estratégia de testes

Os testes do projeto têm como objetivo validar cenários reais de uso, e não apenas unidades isoladas de código. Por
isso, a maior parte da suíte é composta por testes de integração que sobem o contexto do Spring e exercitam a aplicação
de ponta a ponta — da camada HTTP até o banco de dados.

Alguns pontos importantes da abordagem:

- **Banco H2 para testes de integração**: os testes não dependem de um banco PostgreSQL real. É utilizado o H2 em
  memória, configurado com um `application.yaml` próprio para o ambiente de testes, o que torna a suíte rápida e
  independente de infraestrutura externa.
- **Database Rider para carga de dados**: cenários que dependem de um estado prévio no banco usam datasets XML
  carregados via `@DataSet`, garantindo que cada teste comece a partir de um estado conhecido e controlado.
- **Fixtures para reutilização de objetos**: a criação de objetos de teste (DTOs de requisição, entidades de domínio,
  etc.) é centralizada em classes de fixture. Isso evita duplicação de código entre os testes e facilita a manutenção
  quando um objeto de domínio muda — a alteração é feita em um único lugar.
- **Cenários reais de negócio**: os testes cobrem tanto os fluxos de sucesso quanto as regras de negócio que devem
  impedir uma ação (por exemplo, tentar registrar um pedido em uma mesa que já está sendo atendida, ou buscar um pedido
  que não existe), validando o comportamento completo da API, incluindo os status HTTP retornados pelo handler global de
  exceções.

## Estrutura do repositório

```
order-flow/
├── garcom-service/   # Microserviço responsável pelo registro de pedidos
├── cozinha-service/  # Microserviço responsável pelo preparo dos pedidos
├── docker-compose.yml
└── LICENSE
```