package com.colruyt.springtransaction.repository;

import com.colruyt.springtransaction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}