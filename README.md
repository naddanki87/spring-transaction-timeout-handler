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
