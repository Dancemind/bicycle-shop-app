package com.dancemind.springmvc.bicycleshop.controllers;

import com.dancemind.springmvc.bicycleshop.entities.Bicycle;
import com.dancemind.springmvc.bicycleshop.entities.Order;
import com.dancemind.springmvc.bicycleshop.repositories.BicycleRepository;
import com.dancemind.springmvc.bicycleshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@Controller
@SessionAttributes("order")
@Slf4j
public class BicycleController {

    private BicycleRepository bicycleRepository;
    private UserRepository userRepository;

    @Autowired
    public BicycleController(BicycleRepository bicycleRepository,
                             UserRepository userRepository) {
        this.bicycleRepository = bicycleRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @GetMapping("/bicycles")
    public String showAllBicycles(Model model) {
        model.addAttribute("bicycles", bicycleRepository.
                findAllByDeletedOrderByCreatedAtDesc(false));
        return "bicycles";
    }

    @PostMapping("/bicycles/order/{id}")
    public String processBuyBicycle(@PathVariable("id") long id,
                                    @ModelAttribute Order order,
                                    Model model) {

        Bicycle bicycle = bicycleRepository.findById(id).get();
        order.addBicycle(bicycle);

        //log.info("   --- Redirect: order form ");

        return "redirect:/bicycles/order/current";
    }

    @GetMapping("/adminpanel/bicycles")
    public String apShowAllBicycles(Model model) {
        model.addAttribute("bicycles", bicycleRepository.
                findAllByDeletedOrderByCreatedAtDesc(false));
        return "apbicycles";
    }

    @GetMapping("/adminpanel/bicycles/edit/{id}")
    public String showEditBicycle(@PathVariable("id") long id, Model model) {
        model.addAttribute("bicycle", bicycleRepository.findById(id).get());
        return "edit";
    }

    @PostMapping("/adminpanel/bicycles/edit")
    public String update(@ModelAttribute("bicycle") Bicycle bicycle) {
        //log.info(" -------- Updating.. id:" + bicycle.getId());
        bicycle.createdAt();
        bicycle.setDeleted(false);
        bicycleRepository.save(bicycle);
        return "redirect:/adminpanel/bicycles";
    }

    @PostMapping("/adminpanel/bicycles/{id}")
    public String delete(@PathVariable("id") long id) {
        //log.info(" -------- Deleting.. id:" + id);

        Bicycle bicycle = bicycleRepository.findById(id).get();
        bicycle.setDeleted(true);
        bicycleRepository.save(bicycle);
        return "redirect:/adminpanel/bicycles";
    }

    @GetMapping("/adminpanel/addbicycle")
    public String addBicycleForm(Model model) {
        model.addAttribute("bicycle", new Bicycle());
        return "addbicycle";
    }

    @PostMapping("/adminpanel/addbicycle")
    public String addNewBicycle(@Valid Bicycle bicycle,
                                Errors errors,
                                Model model) {

        //log.info("   --- Adding new bicycle..");


        if (errors.hasErrors()) {
            return "addbicycle";
        }
        if (!isNull(bicycleRepository.findByName(bicycle.getName()))) {
            model.addAttribute("errorMessage", "The name is already in database.");
            return "addbicycle";
        }

        bicycle.setDeleted(false);
        bicycleRepository.save(bicycle);

        return "redirect:/adminpanel/bicycles";
    }
}
