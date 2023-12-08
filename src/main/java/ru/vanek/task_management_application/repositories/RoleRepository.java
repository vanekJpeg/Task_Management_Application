package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.task_management_application.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
