# Credit Analysis System with Apache Kafka

This monorepo contains two microservices developed in Java with Spring Boot that communicate through Apache Kafka. The system simulates the credit card application and analysis process.

## Project Structure

- `card-request`  
  Microservice responsible for receiving card applications and publishing them to a Kafka topic.

- `card-analysis`  
  Microservice responsible for consuming requests from Kafka and simulating a credit analysis.

- `docker-compose.yaml`  
  Defines Kafka, Zookeeper, and management interface (Kafka UI) services.

## Prerequisites

- Java 17+
- Maven
- Docker and Docker Compose

## How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/SergioHs/card-request.git
cd card-request
```

### 2. Start Kafka with Docker Compose

```bash
docker-compose up -d
```

The Kafka web interface will be available at: http://localhost:8080

### 3. Run the Microservices

Open two terminals and run:

**Card Request:**
```bash
cd card-request
mvn spring-boot:run
```

**Credit Analysis:**
```bash
cd card-analysis
mvn spring-boot:run
```

### 4. Submit a Card Application

Using curl:

```bash
curl -X POST http://localhost:8080/cards \
  -H "Content-Type: application/json" \
  -d '{"cpf": "12345678900", "name": "John Smith", "age": 30}'
```

## Notes

- Messages are exchanged through the Kafka topic `card-request`
- Kafka, Zookeeper, and Kafka UI run via containers defined in `docker-compose.yaml`
