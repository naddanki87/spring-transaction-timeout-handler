 🧾 Spring Transaction Timeout Demo

This Spring Boot application demonstrates how to handle transactions with a custom timeout condition. If the total operation time exceeds a configured threshold (e.g., 3 seconds), the inserted records are manually deleted within the same transaction instead of rolling back via exception.

## 🚀 Features

- Spring Boot + JPA + H2
- Custom transaction timeout handling
- Manual cleanup instead of rollback
- Swagger UI for API testing
- H2 in-memory database for quick testing

## 🔄 Transaction Flow

If inserting multiple items takes **more than 3 seconds**, all inserted items are **deleted within the same transaction**, and the transaction **commits** with zero records in the database.

## 🧪 API Endpoint

### POST `/api/items/batch`

- Inserts multiple items
- Introduces delays to simulate slow operations
- If total delay > `3s`, deletes all inserted records within the same transaction

---

## 📦 How to Build and Run

### 🛠️ Build the JAR

```bash
mvn clean package
```

This generates the executable JAR in the `target` folder.

### ▶️ Run the App

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Useful URLs

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  - JDBC URL: `jdbc:h2:mem:testdb`

**Alternate Approach**:
The @Transactional(timeout = 30) annotation is used in Java (commonly with Spring Framework) to manage the duration of a transaction. Here’s what it means and how it works:

What Does It Do?
Declares a Transaction: Marks a method or class so that its operations are executed within a database transaction.

Sets a Timeout: The timeout = 30 part specifies that the transaction should not run longer than 30 seconds.

How It Works
When the annotated method is called, a transaction begins.

If the method (and all its database operations) completes within 30 seconds, the transaction is committed (changes are saved).

If the method takes longer than 30 seconds, the transaction is automatically rolled back (changes are discarded), and an exception is thrown.
---

## 📁 Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Web + JPA
- H2 Database
- Swagger/OpenAPI
- Maven

---

```mermaid
sequenceDiagram
    participant Client
    participant API
    participant ItemService
    participant ItemRepository
    participant Database

    Client->>API: POST /batch
    API->>ItemService: performMultipleOperations()
    ItemService->>ItemRepository: Save Item 1
    ItemRepository->>Database: Insert Item 1
    Database-->>ItemRepository: Item 1 saved

    ItemService->>ItemRepository: Save Item 2
    ItemRepository->>Database: Insert Item 2
    Database-->>ItemRepository: Item 2 saved

    ItemService->>ItemRepository: Save Item 3
    ItemRepository->>Database: Insert Item 3
    Database-->>ItemRepository: Item 3 saved

    ItemService->>ItemRepository: Save Item 4
    ItemRepository->>Database: Insert Item 4
    Database-->>ItemRepository: Item 4 saved

    ItemService->>ItemService: Check Time (Timeout > 3s?)
    ItemService->>ItemRepository: Delete All Inserted Items
    ItemRepository->>Database: Delete All Items
    Database-->>ItemRepository: Items Deleted
    ItemService->>API: Return Success (No Records Persisted)
    Client-->>API: Response (Data Cleared)


