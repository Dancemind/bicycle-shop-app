package com.dancemind.springmvc.bicycleshop.repositories;

import com.dancemind.springmvc.bicycleshop.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository
        extends CrudRepository<Order, Long> {

    List<Order> findAllByOrderByPlacedAtDesc();

}
