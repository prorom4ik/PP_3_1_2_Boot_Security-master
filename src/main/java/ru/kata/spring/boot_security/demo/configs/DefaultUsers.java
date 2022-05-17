package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Value;
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
public class DefaultUsers {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("spring.jpa.hibernate.ddl-auto")
    private String ddl_auto;

    public DefaultUsers(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if ("create".equals(ddl_auto) || "create-drop".equals(ddl_auto)) {
            Role roleAdmin = new Role(1L,"ROLE_ADMIN");
            Role roleUser = new Role(2L,"ROLE_USER");

            roleService.addRole(roleAdmin);
            roleService.addRole(roleUser);

            User romanUser = new User("roman","torop",19,"rom4ik", passwordEncoder.encode("123"));
            romanUser.setRoles(new HashSet(Arrays.asList(roleAdmin,roleUser)));

            User makarUser  = new User("makar","pavlov",21, "makar4ik",passwordEncoder.encode("1234"));
            makarUser.setRoles(new HashSet(List.of(roleUser)));

            userService.saveUser(romanUser);
            userService.saveUser(makarUser);
        }
    }
}