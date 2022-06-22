package com.batarilo.reactiveorderservice.service;

import com.batarilo.reactiveorderservice.dto.PurchaseOrderResponseDto;
import com.batarilo.reactiveorderservice.repository.PurchaseOrderRepository;
import com.batarilo.reactiveorderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){

       return Flux.fromStream(()->
               purchaseOrderRepository.findByUserId(userId).stream())
               .map(EntityDtoUtil::getPurchaseResponse)
               .subscribeOn(Schedulers.boundedElastic());

    }

}
