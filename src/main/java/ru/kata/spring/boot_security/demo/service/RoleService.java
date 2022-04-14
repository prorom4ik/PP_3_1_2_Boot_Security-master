package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.getById(id);
    }

    public Set<Role> getByName(String[] roleName) {
        return roleRepository.findAll().stream()
                .filter(role -> roleName.equals(role.getName()))
                .collect(Collectors.toSet());
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void deleteById(Long id) {
        Role role = roleRepository.getById(id);
        if (role != null) {
            roleRepository.delete(role);
        }
    }
}
