package ru.kata.spring.boot_security.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AutoRegisterUser {
    private final UserServiceImp userServiceImp;
    private final RoleRepository roleRepository;
    @Autowired
    public AutoRegisterUser(UserServiceImp userServiceImp, RoleRepository roleRepository) {
        this.userServiceImp = userServiceImp;
        this.roleRepository = roleRepository;
    }


    @PostConstruct
    void AutoSaveUser() {
        Role role_admin = new Role(1L,"ROLE_ADMIN");
        Role role_user = new Role(2L,"ROLE_USER");
        roleRepository.save(role_admin);
        roleRepository.save(role_user);

        User user_admin = new User("admin","admin", "admin@mail.ru",25);
        User user_user = new User("user", "user", "user@mail.ru",20);

        user_admin.setRoles(List.of(role_admin,role_user));
        user_user.setRoles(List.of(role_user));

        userServiceImp.saveUser(user_admin);
        userServiceImp.saveUser(user_user);

    }
}
