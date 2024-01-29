package com.springcloud.notificationservice.listeners;

import com.springcloud.notificationservice.events.OrderEvent;
import com.springcloud.notificationservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {
    @KafkaListener(topics= "orders-topic")
    public void handlerOrderNotification(String message){
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        //Send email to customer, send sms to customer, etc.
        //Notify another service...

        log.info("Order {} event received for order : {} with {} items", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemsCount());
    }
}
