package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;
import ru.vanek.task_management_application.utils.Status;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "header")
    private String header;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private User author;

    @ManyToOne()
    @JoinColumn(name = "executor_id",referencedColumnName = "id")
    private User executor;

    @OneToMany(mappedBy = "commentedTask")
    private List<Comment> comments;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
