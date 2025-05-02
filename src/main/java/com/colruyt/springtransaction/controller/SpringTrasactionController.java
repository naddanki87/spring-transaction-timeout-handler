package com.colruyt.springtransaction.controller;

import com.colruyt.springtransaction.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Item API", description = "Operations related to Items")
public class SpringTrasactionController {

    private final ItemService itemService;

    public SpringTrasactionController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/batch")
    @Operation(summary = "Run multiple operations on Items", description = "This performs several database operations and rolls back if timeout occurs.")
    public ResponseEntity<?> runBatchInsert() {
        try {
            itemService.performMultipleOperations();
            return ResponseEntity.ok("All operations successful.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Transaction failed: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Iam ready");
    }
}