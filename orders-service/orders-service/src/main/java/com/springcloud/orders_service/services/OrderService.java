package com.springcloud.orders_service.services;

import com.springcloud.orders_service.events.OrderEvent;
import com.springcloud.orders_service.model.dtos.*;
import com.springcloud.orders_service.model.entities.Order;
import com.springcloud.orders_service.model.entities.OrderItems;
import com.springcloud.orders_service.model.enums.OrderStatus;
import com.springcloud.orders_service.repositories.OrderRepository;
import com.springcloud.orders_service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest orderRequest){

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("lb://inventory-service/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(result != null && !result.hasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());
            var orderSaved = this.orderRepository.save(order);
            //TODO SEND MESSAGE TO ORDER TOPIC
            this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                    new OrderEvent(orderSaved.getOrderNumber(), orderSaved.getOrderItems().size(), OrderStatus.PLACED)
            ));
            return mapToOrderResponse(orderSaved);
        }else{
            throw new IllegalArgumentException("Some of the products are not in stock");
        }
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(),order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }
}
