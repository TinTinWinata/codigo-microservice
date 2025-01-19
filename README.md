# Codigo Test Project

## Objective

This project demonstrates the implementation of a scalable, secure, and efficient eVoucher Management System with the following goals:

1. Create backend RESTful APIs using Java, JPA, and the Spring Framework.
2. Implement caching mechanisms like Redis to optimize performance.
3. Secure APIs using authentication mechanisms (JWT or Bearer tokens).
4. Implement token expiration and refresh processes.
5. Efficiently handle concurrent requests.
6. Design and document functional flow diagrams for the system.
7. Design a database relational diagram and schema.
8. Demonstrate knowledge of microservices architecture.
9. Create cloud system design diagrams.
10. Implement scheduler tasks or logic for promo code generation.
11. Create a user-friendly CMS UI for eVoucher management using Angular or React.
12. Follow best practices in code quality, modularity, scalability, and security.
13. Deliver well-documented and structured code through GitHub.

---

## Features

### Functional Highlights

- **Voucher Creation**: Pre-generates promo codes and QR codes during voucher creation to ensure instant availability upon purchase.
- **Promo Code Management**: Handles up to 300 promo codes daily with optimized database and caching mechanisms.
- **User Authentication**: Secure access using JWT-based authentication with token expiration and refresh.
- **High Concurrency**: Reactive programming using R2DBC for non-blocking database operations in Voucher Service.
- **Caching**: Redis implementation for fast retrieval of frequently accessed data.
- **Asynchronous Communication**: Kafka is used for asynchronous voucher creation and promo code generation.
- **Resilience**: Circuit breaker patterns to handle failures in dependent services without affecting overall stability.

### Quantitative Analysis

- Low-frequency voucher creation (around three per day), with up to 100 promo codes per voucher.
- Estimated 300 promo codes generated daily.
- Handles up to 10⁵ daily users, with 50% viewing discount promos as part of marketing.

---

## Project Structure

### Key Components

1. **Microservices Architecture**:
   - **API Gateway**: Handles all incoming requests and validates JWT tokens.
   - **Voucher Service**: Manages voucher creation, promo code generation, and payment validation.
   - **Order Service**: Processes orders and ensures promo code synchronization.
2. **Databases**:
   - Relational Database (MySQL) for persistent storage.
   - Redis for caching.
3. **Messaging**:
   - Kafka for asynchronous communication between services.

## Deployment

The system is designed to run in a cloud environment with the following key configurations:

- Containerized microservices deployed on Kubernetes.
- Auto-scaling and load balancing for scalability.
- Redis and Kafka deployed as managed services.

---

## Further Improvements

1. **Dynamic Promo Code Generation**:
   - Generate additional promo codes dynamically when the count falls below a certain threshold.
2. **Monitoring and Logging**:
   - Implement advanced monitoring tools like Prometheus and Grafana.
   - Centralized logging for debugging and analytics.
3. **Enhanced UI**:
   - Add an analytics dashboard for admins to track voucher performance.

---

## How to Run

### Prerequisites

- Java 12 or higher
- Docker and Docker Compose
- Redis and Kafka installed or running as Docker containers
- MySQL database

### Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd codigo-test
   ```
2. Go each service and Build the project ex:
   ```bash
   cd voucher-service
   ./mvnw clean install
   ```
3. Run Docker Compose to start all services:
   ```bash
   docker-compose up
   ```
4. Access the API Gateway at `http://localhost:8080`.

---

## API Documentation

API documentation is available via Postman on codigo-postman.json

## Author

Developed by Justine Winata. For inquiries or collaboration, please contact tintin6892@gmail.com.
