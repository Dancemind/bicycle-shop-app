package com.dancemind.springmvc.bicycleshop;


import com.dancemind.springmvc.bicycleshop.entities.Bicycle;
import com.dancemind.springmvc.bicycleshop.repositories.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BicyclesLoader {

    @Autowired
    BicycleRepository bicycleRepository;

    @PostConstruct
    public void addData() {

        Bicycle b = new Bicycle();
        b.setName("First bike");
        b.setPrice("340");
        b.setDeleted(false);
        bicycleRepository.save(b);

        b = new Bicycle();
        b.setName("Combo bike");
        b.setPrice("470");
        b.setDeleted(false);
        bicycleRepository.save(b);

        b = new Bicycle();
        b.setName("Pink bike");
        b.setPrice("570");
        b.setDeleted(false);
        bicycleRepository.save(b);

    }


}