package com.batarilo.reactiveorderservice.util;

import com.batarilo.reactiveorderservice.dto.OrderStatus;
import com.batarilo.reactiveorderservice.dto.PurchaseOrderResponseDto;
import com.batarilo.reactiveorderservice.dto.RequestContext;
import com.batarilo.reactiveorderservice.entity.PurchaseOrder;
import dto.TransactionRequestDto;
import dto.TransactionStatus;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext){

        TransactionRequestDto transactionRequestDto
                = TransactionRequestDto.builder()
                .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
                .amount(requestContext.getProductDto().getPrice())
                .build();

        requestContext.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){

        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;

        return PurchaseOrder.builder()
                .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
                .amount(requestContext.getTransactionRequestDto().getAmount())
                .productId(requestContext.getPurchaseOrderRequestDto().getProductId())
                .status(orderStatus)
                .build();
    }

    public static PurchaseOrderResponseDto getPurchaseResponse(PurchaseOrder purchaseOrder){
       return PurchaseOrderResponseDto.builder()
                .orderId(purchaseOrder.getId())
                .userId(purchaseOrder.getUserId())
                .amount(purchaseOrder.getAmount())
                .productId(purchaseOrder.getProductId())
                .status(purchaseOrder.getStatus())
                .build();
    }
}
