package com.dancemind.springmvc.bicycleshop.security;

import com.dancemind.springmvc.bicycleshop.dto.UserDto;
import com.dancemind.springmvc.bicycleshop.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dancemind.springmvc.bicycleshop.repositories.UserRepository;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        //return "registration";
        return "reg";
    }


    @PostMapping
    public String processRegistration(@ModelAttribute("user") @Valid UserDto userDto,
                                      BindingResult bindingResult, Model model) {

        log.info("---------- user: " + userDto.toString());

        if (bindingResult.hasErrors())
            //return "registration";
            return "reg";
        if (!isNull(userRepo.findByUsername(userDto.getUsername()))) {
            model.addAttribute("errorMessage", "Username is already in use. Please choose another.");
            return "reg";
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = new User(1L,userDto.getUsername(),
                userDto.getPassword(),"USER");

        userRepo.save(user);

        //userRepo.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }

}
