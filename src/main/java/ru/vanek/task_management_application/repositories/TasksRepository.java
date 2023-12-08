package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.task_management_application.models.Task;

import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task,Integer> {
    Optional<Task> findAllByExecutorEmail(String email);
    Optional<Task> findAllByAuthorEmail(String email);
}
