package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl {

    final RoleRepository roleRepository;
    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).get();
    }

    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }
    public void deleteUser(User user) {
        userRepository.delete(user);}

    public List<String> getUserRolesAsStringList(User user) {
        List<Role> userRoles = user.getRoles();
        List<String> userRolesAsStringList = new ArrayList<>();
        for (Role role: userRoles) {
            userRolesAsStringList.add(role.getAuthority());
        }
        return userRolesAsStringList;
    }
}
