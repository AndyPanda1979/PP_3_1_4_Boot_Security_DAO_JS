package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.util.List;

@Controller
public class AdminController {

    final UserServiceImpl userService;
    final RoleService roleService;

    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    /* ====== READ ====== */

    @GetMapping(value = "admin/users")
    public String allUsers(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User admin = userDetails.getUser();
        List<User> users = userService.getUsers();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("usersList", users);
        model.addAttribute("admin", admin);
        model.addAttribute("roles", roles);
        return ("admin/users_new");
    }

    @GetMapping(value = "/admin/user/{id}")
    public String userDetails(@PathVariable ("id") Long id, ModelMap model) {
        System.out.println("CATCH userDetails");
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return ("admin/userdetails_new");
    }


    /* ====== CREATE ====== */

    @GetMapping(value = "/admin/user/add")
    public String addUser(@ModelAttribute ("user") User user, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User admin = userDetails.getUser();
        List<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("admin", admin);
        model.addAttribute("listRoles", listRoles);
        return ("admin/adduser_form_new");
    }

    @PostMapping(value = "/admin/user/add")
    public String createUser(@ModelAttribute ("user") User user, @ModelAttribute("role") Role tole) {
        userService.saveUser(user);
        return ("redirect:/admin/users");
    }


    /* ====== UPDATE ====== */

    @PatchMapping("admin/user")
    public String saveUser(@ModelAttribute ("user") User user) {
        userService.updateUser(user);
        return ("redirect:/admin/users");
    }


    /* ====== DELETE ====== */

    @DeleteMapping(value = "admin/user/{id}/delete")
    public String deleteUser(@PathVariable ("id") long id, ModelMap model) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return ("redirect:/admin/users");
    }
}
