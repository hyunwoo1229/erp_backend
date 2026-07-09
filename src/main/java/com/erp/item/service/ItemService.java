package com.erp.item.service;

import com.erp.item.Item;
import com.erp.item.dto.ItemCreateRequest;
import com.erp.item.dto.ItemResponse;
import com.erp.item.repository.ItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemResponse create(ItemCreateRequest request) {
        if (itemRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 품목 코드입니다: " + request.getCode());
        }
        Item item = new Item(request.getCode(), request.getName(), request.getUnit(), request.getSafetyStock());
        Item saved = itemRepository.save(item);
        return new ItemResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::new)
                .toList();
    }
}