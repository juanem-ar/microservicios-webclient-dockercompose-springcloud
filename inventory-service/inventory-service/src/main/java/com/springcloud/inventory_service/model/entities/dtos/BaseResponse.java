package com.springcloud.inventory_service.model.entities.dtos;

public record BaseResponse(String[] errorMessage) {
    public boolean hasErrors(){
        return errorMessage != null && errorMessage.length > 0;
    }
}
