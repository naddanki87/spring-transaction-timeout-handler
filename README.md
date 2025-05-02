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
