package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.models.Role;

@Service
public interface RoleService {
    public Role getUserRole();
}
