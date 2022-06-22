package com.batarilo.reactiveorderservice.repository;

import com.batarilo.reactiveorderservice.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {


    List<PurchaseOrder> findByUserId(int id);
}
