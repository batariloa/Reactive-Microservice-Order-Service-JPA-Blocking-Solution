package com.batarilo.reactiveorderservice.controller;

import com.batarilo.reactiveorderservice.dto.PurchaseOrderRequestDto;
import com.batarilo.reactiveorderservice.dto.PurchaseOrderResponseDto;
import com.batarilo.reactiveorderservice.entity.PurchaseOrder;
import com.batarilo.reactiveorderservice.service.OrderFulfillmentService;
import com.batarilo.reactiveorderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PurchaseOrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService orderQueryService;

    @PostMapping("order")
    public Mono<PurchaseOrderResponseDto> purchase(@RequestBody Mono<PurchaseOrderRequestDto> request){

        return orderFulfillmentService.processOrder(request);

    }

    @GetMapping ("{id}")
    private Flux<PurchaseOrderResponseDto> getPurchaseOrdersById(@RequestParam int id){

        return orderQueryService.getProductsByUserId(id);
    }

}
