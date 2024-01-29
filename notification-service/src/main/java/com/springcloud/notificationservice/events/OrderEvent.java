package com.springcloud.notificationservice.events;

import com.springcloud.notificationservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
