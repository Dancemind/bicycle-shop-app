package com.dancemind.springmvc.bicycleshop.repositories;

import com.dancemind.springmvc.bicycleshop.entities.User;
import org.springframework.data.repository.CrudRepository;
import com.dancemind.springmvc.bicycleshop.entities.Bicycle;

import java.util.List;

public interface BicycleRepository extends CrudRepository<Bicycle, Long> {

    List<Bicycle> findAllByDeletedOrderByCreatedAtDesc(boolean deleted);

    //Bicycle findOneById(long id);

    Bicycle findByName(String bicycleName);


}
