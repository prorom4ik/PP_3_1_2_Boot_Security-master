package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private static Logger LOGGER;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        LOGGER = Logger.getLogger(AdminController.class.getName());
    }

    @GetMapping
    public String findAll(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user-create")
    public String createUserForm(ModelMap map) {
        map.addAttribute("roleList", roleService.findAll());
        map.addAttribute("user", new User());
        return "create";
    }

    @PostMapping
    public String createUser(User user) {
        if (user.getRoles().isEmpty()) {
            LOGGER.log(Level.SEVERE,"Попытка создания пользователя без ролей");
            return "emptyRolesError";
        }

        String password  = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user) {
        if (user.getRoles().isEmpty()) {
            LOGGER.log(Level.SEVERE,"Попытка создания пользователя без ролей");
            return "emptyRolesError";
        }
        String password  = user.getPassword();
        if (password != null && !password.equals("")) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
