package com.dancemind.springmvc.bicycleshop.controllers;

import com.dancemind.springmvc.bicycleshop.entities.Order;
import com.dancemind.springmvc.bicycleshop.entities.User;
import com.dancemind.springmvc.bicycleshop.repositories.OrderRepository;
import com.dancemind.springmvc.bicycleshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Controller
@SessionAttributes("order")
@Slf4j
public class OrderController {

    private OrderRepository orderRepo;
    private UserRepository userRepo;


    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }


    @PostMapping("/bicycles/order")
    public String processOrderForm(@Valid Order order, Errors errors,
                                   @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderform";
        }

         // put delivery address to user
        user.setFullname(order.getDeliveryName());
        user.setStreet(order.getDeliveryStreet());
        user.setCity(order.getDeliveryCity());
        user.setState(order.getDeliveryState());
        user.setZip(order.getDeliveryZip());

        //log.info("--------user:" + user.toString());

        order.placedAt();
        Order savedOrder = orderRepo.save(order);
        order.setId(savedOrder.getId());


        return "redirect:/bicycles/order/success";

    }


    @GetMapping("/bicycles/order/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute Order order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        order.setUser(user);
        return "orderform";
    }

    @GetMapping("/bicycles/order/success")
    public String orderSuccess(@ModelAttribute Order order,
                               SessionStatus sessionStatus,
                               Model model) {
        model.addAttribute("id", order.getId());
        sessionStatus.setComplete();
        return "ordersuccess";
    }

    @GetMapping("/adminpanel/orders")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderRepo.findAllByOrderByPlacedAtDesc());
        return "aporders";
    }
}