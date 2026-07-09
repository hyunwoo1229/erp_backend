package com.erp.item.controller;

import com.erp.item.dto.ItemCreateRequest;
import com.erp.item.dto.ItemResponse;
import com.erp.item.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemResponse create(@Valid @RequestBody ItemCreateRequest request) {
        return itemService.create(request);
    }

    @GetMapping
    public List<ItemResponse> findAll() {
        return itemService.findAll();
    }
}