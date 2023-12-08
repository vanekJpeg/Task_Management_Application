package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.task_management_application.models.Task;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task,Integer> {
    Optional<List<Task>> findAllByExecutorId(int id);
    Optional<List<Task>> findAllByAuthorId(int id);
}
