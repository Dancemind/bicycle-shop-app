package com.dancemind.springmvc.bicycleshop.repositories;

import com.dancemind.springmvc.bicycleshop.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}