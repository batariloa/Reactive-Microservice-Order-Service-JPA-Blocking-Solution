package com.batarilo.reactiveorderservice.service;

import com.batarilo.reactiveorderservice.client.ProductClient;
import com.batarilo.reactiveorderservice.client.UserClient;
import com.batarilo.reactiveorderservice.dto.PurchaseOrderRequestDto;
import com.batarilo.reactiveorderservice.dto.PurchaseOrderResponseDto;
import com.batarilo.reactiveorderservice.dto.RequestContext;
import com.batarilo.reactiveorderservice.repository.PurchaseOrderRepository;
import com.batarilo.reactiveorderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

    @Autowired
    ProductClient productClient;



    @Autowired
    private UserClient userClient;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> purchaseOrderRequestDtoMono){

        return purchaseOrderRequestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(e-> System.out.println("bblblb"))
                .doOnNext(EntityDtoUtil::setTransactionRequestDto) //set the amount to the price of the product
                .flatMap(this::userRequestResponse)
                .doOnNext(e-> System.out.println("set user " + e.getTransactionResponseDto().toString()))
                .map(EntityDtoUtil::getPurchaseOrder)
                .doOnNext(e-> System.out.println("bblblb"))
                .map(purchaseOrderRepository::save) //blocking
                .map(EntityDtoUtil::getPurchaseResponse)
                .subscribeOn(Schedulers.boundedElastic());

    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext){
        return this.productClient.getProductById(requestContext.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(requestContext::setProductDto)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext){

        return this.userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}
