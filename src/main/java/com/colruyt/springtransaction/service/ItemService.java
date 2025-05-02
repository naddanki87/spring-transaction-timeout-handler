package com.colruyt.springtransaction.service;

import com.colruyt.springtransaction.entity.Item;
import com.colruyt.springtransaction.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Value("${transaction.timeout.threshold}")
    private long timeoutThreshold;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void performMultipleOperations() throws Exception {
        long start = System.currentTimeMillis();
        List<Item> insertedItems = new ArrayList<>();

        Item item1 = itemRepository.save(new Item(null, "Nageshwar"));
        insertedItems.add(item1);
        checkTimeoutAndCleanup(start, insertedItems);

        log.info("So far records in DB: " + itemRepository.findAll());

        Thread.sleep(1500);
        Item item2 = itemRepository.save(new Item(null, "Hareesh"));
        insertedItems.add(item2);
        checkTimeoutAndCleanup(start, insertedItems);

        log.info("So far records in DB: " + itemRepository.findAll());

        Thread.sleep(1500);
        Item item3 = itemRepository.save(new Item(null, "Ranga"));
        insertedItems.add(item3);
        checkTimeoutAndCleanup(start, insertedItems);

        log.info("Final records in DB: " + itemRepository.findAll());
    }

    private void checkTimeoutAndCleanup(long startTime, List<Item> insertedItems) {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > timeoutThreshold) {
            System.out.println("Timeout exceeded (" + elapsed + " ms). Cleaning up inserted records...");
            itemRepository.deleteAll(insertedItems);
            insertedItems.clear();
        }
    }
}