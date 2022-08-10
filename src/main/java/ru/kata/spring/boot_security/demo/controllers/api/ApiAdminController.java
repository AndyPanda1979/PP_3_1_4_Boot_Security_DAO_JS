package ru.kata.spring.boot_security.demo.controllers.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiAdminController {

    private final UserService userService;
    private final RoleService roleService;

    public ApiAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // получить текущего владельца сессии
    @GetMapping(value = "admin/currentuser")
    public User currentUser(@AuthenticationPrincipal User testUser) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return userDetails.getUser();
        return testUser;
    }

    // получить все возможные роли
    @GetMapping(value = "admin/roles")
    public List<Role> allRoles() {
        return roleService.getAllRoles();
    }

    /* -- CRUD -- */

    @GetMapping(value = "admin/users")
    public List<User> allUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/admin/users/{id}")
    public User userDetails(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/admin/users")
    public void createUser(@ModelAttribute ("user") User user, @ModelAttribute("role") Role role) {
        userService.saveUser(user);
    }

    @PatchMapping("admin/users/{id}")
    public void saveUser(@ModelAttribute ("user") User user, @PathVariable ("id") Long id) {
        userService.updateUser(user);
    }

    @DeleteMapping(value = "admin/users/{id}")
    public void deleteUser(@PathVariable ("id") Long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
    }

}
