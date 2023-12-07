package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vanek.task_management_application.models.Task;

public interface TasksRepository extends JpaRepository<Task,Integer> {
}
