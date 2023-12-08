package ru.vanek.task_management_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vanek.task_management_application.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment,Integer> {
    Optional<List<Comment>> findAllByCommentedTaskId(int id);
}
