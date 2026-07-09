package com.erp.item.repository;

import com.erp.item.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByCode(String code);

    Optional<Item> findByCode(String code);
}