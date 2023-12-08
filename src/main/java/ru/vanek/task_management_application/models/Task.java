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
    private Status status;//TODO автоматическое назначение на в ожидании

    @ManyToOne()
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private User author;//TODO автоматическое назначение

    @ManyToOne()
    @JoinColumn(name = "executor_id",referencedColumnName = "id")
    private User executor;

    @OneToMany(mappedBy = "commentedTask")
    private List<Comment> comments;//TODO автоматическое назначение

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;//TODO автоматическое назначение

}
