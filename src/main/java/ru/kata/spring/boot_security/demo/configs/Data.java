package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class Data {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Data(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        User romanUser = new User("roman","torop",19,"rom4ik");
        romanUser.setPassword(passwordEncoder.encode("123"));
        romanUser.setRoles(new HashSet(Arrays.asList(roleAdmin,roleUser)));

        User makarUser  = new User("makar","pavlov",21, "makar4ik");
        makarUser.setPassword(passwordEncoder.encode("1234"));
        makarUser.setRoles(new HashSet(List.of(roleUser)));

        userService.saveUser(romanUser);
        userService.saveUser(makarUser);

    }
}
