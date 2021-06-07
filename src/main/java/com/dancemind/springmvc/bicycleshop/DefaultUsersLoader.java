package com.dancemind.springmvc.bicycleshop;



import com.dancemind.springmvc.bicycleshop.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dancemind.springmvc.bicycleshop.repositories.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class DefaultUsersLoader {

    @Autowired
    UserRepository userRepo;

    @PostConstruct
    public void initUserData() {

        // load user ROLE_USER into database

        User defaultUser = new User(1L,
                "sss",
                "725369b8561121ef48a83679faa7955dff7c460a8f3d9a3a6605dcb0ad2aa6569788665e490ccea9",
                "USER");
        defaultUser.setFullname("User Name");
        defaultUser.setStreet("StreetName");
        defaultUser.setCity("CityName");
        defaultUser.setState("StateName");
        defaultUser.setZip("12345");

        userRepo.save(defaultUser);

        // load admin ROLE_ADMIN into database
        User defaultAdmin = new User(2L,
                "ad",
                "2539c20d403bc8cdef166efbe056a2776bd96d37246298a45564540f24a5344b0477847e302faa4d",
                "ADMIN");

        userRepo.save(defaultAdmin);
    }


}

