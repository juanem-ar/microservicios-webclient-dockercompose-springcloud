package com.springcloud.orders_service.model.dtos;

public record BaseResponse(String[] errorMessage) {
    public boolean hasErrors(){
        return errorMessage != null && errorMessage.length > 0;
    }
}
