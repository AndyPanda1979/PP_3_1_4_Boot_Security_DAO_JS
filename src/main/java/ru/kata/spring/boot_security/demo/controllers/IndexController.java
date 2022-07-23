package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;


@Controller
public class IndexController {


    @GetMapping(value = "/")
    public String indexPage(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().contains("anonymousUser")) {
            model.addAttribute("userName", "Guest");
            model.addAttribute("authenticated", false);
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            model.addAttribute("userName", userDetails.getUsername());
            model.addAttribute("userDetails", userDetails.getUser());
            model.addAttribute("authenticated", true);
        }
        return ("index");
    }
}
