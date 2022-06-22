package com.batarilo.reactiveorderservice.dto;

import dto.ProductDto;
import dto.TransactionRequestDto;
import dto.TransactionResponseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {


    private PurchaseOrderResponseDto purchaseOrderResponseDto;
    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private TransactionResponseDto transactionResponseDto;
    private TransactionRequestDto transactionRequestDto;
    private ProductDto productDto;


    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto){
    this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }


}
