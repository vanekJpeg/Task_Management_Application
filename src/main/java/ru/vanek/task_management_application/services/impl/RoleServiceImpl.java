package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.models.Role;
import ru.vanek.task_management_application.repositories.RoleRepository;
import ru.vanek.task_management_application.services.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }

}
