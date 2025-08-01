# 📄 document-producer

> Serviço que realiza a leitura de PDFs em um bucket S3 e envia mensagens para o Kafka se uma palavra-chave for encontrada.


O `document-producer` é um serviço Spring Boot que realiza a leitura de documentos PDF armazenados em um bucket S3, busca por uma palavra-chave específica e, caso encontre, envia uma mensagem para um tópico Kafka, a ser consumida por outro serviço (o *consumer*).

---

## ✅ Funcionalidades

- 🔍 Faz leitura de documentos PDF a partir de um bucket S3;
- 🔑 Pesquisa a ocorrência de uma palavra-chave dentro de cada documento;
- 📤 Envia mensagens para um tópico Kafka (`document-filter`) contendo o nome do arquivo que contém a palavra;
- 🪵 Exibe logs com os arquivos processados e os que contêm ou não a palavra.

---

## 📦 Tecnologias e Dependências

- Java 17
- Spring Boot
  - spring-boot-starter-web
  - spring-kafka
- AWS SDK v2 (S3)
- Apache PDFBox
- Lombok
- Kafka (producer)
- JUnit / Spring Kafka Test

---

## ▶️ Como rodar a aplicação

### Pré-requisitos

- Java 17+
- Maven 3.6+
- Kafka em execução (pode ser local ou via Docker)
- Bucket S3 configurado (pode usar o [LocalStack](https://github.com/localstack/localstack) para testes locais)

### Passos

### 1. Clone o repositório:

```bash
git clone https://github.com/GBvaillant/document-producer.git
cd document-producer
```
### 2. Execute o docker compose (Servidor Kafka + zookeeper)

```bash
docker compose up -d
```

### 3. Configure as variáveis de ambiente ou `application.properties` ou `application.yml` para acessar seu bucket S3 e Kafka.

Exemplo de `application.properties`:

```properties
# S3
aws.region=us-east-1
aws.bucket.name=document-processor

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
document.topic=document-filter
```
### 4. Crie um bucket no localstack e adicione quantos arquivos quiser.

```bash
# Criar bucket
aws --endpoint-url=http://localhost:4566 s3 mb s3://nome-do-bucket --profile localstack

# Adicionar arquivos
aws --endpoint-url=http://localhost:4566 s3 cp "arquivo1.pdf" s3://document-processor/ --profile localstack
aws --endpoint-url=http://localhost:4566 s3 cp "arquivo2.pdf" s3://document-processor/ --profile localstack
```
---

## 📮 Exemplo de Requisição

**POST** `/search`

```http
Content-Type: application/json
```

**Body**:

```json
{
  "keyword": "contrato"
}
```

---

## 📤 Exemplo de Mensagem Kafka Enviada

```text
{search=contrato, key=Contrato de Locação Residencial.pdf}
```

---

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```
---

## 🧑‍💻 Contribuições

Pull Requests são bem-vindos.

---

## 📄 Licença

[MIT License](LICENSE)
