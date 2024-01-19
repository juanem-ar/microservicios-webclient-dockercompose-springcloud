package com.springcloud.inventory_service.controllers;

import com.springcloud.inventory_service.model.entities.Inventory;
import com.springcloud.inventory_service.model.entities.dtos.BaseResponse;
import com.springcloud.inventory_service.model.entities.dtos.OrderItemRequest;
import com.springcloud.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkAvailable(@PathVariable String sku){
        return this.inventoryService.isInStock(sku);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAll(){
        return this.inventoryService.getAll();
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems){
        return this.inventoryService.areInStock(orderItems);
    }
}
