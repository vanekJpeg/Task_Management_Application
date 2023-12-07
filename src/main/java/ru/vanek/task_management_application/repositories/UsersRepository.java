package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.task_management_application.models.User;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
