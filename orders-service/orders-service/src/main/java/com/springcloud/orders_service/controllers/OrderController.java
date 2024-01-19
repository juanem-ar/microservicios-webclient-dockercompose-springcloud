package com.springcloud.orders_service.controllers;

import com.springcloud.orders_service.model.dtos.OrderRequest;
import com.springcloud.orders_service.model.dtos.OrderResponse;
import com.springcloud.orders_service.model.entities.Order;
import com.springcloud.orders_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        this.orderService.placeOrder(orderRequest);
        return "Order placed succesfully";
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return this.orderService.findAll();
    }


}
