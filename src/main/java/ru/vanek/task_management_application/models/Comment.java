package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="text")
    private String text;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User author;

    @ManyToOne()
    @JoinColumn(name = "task_id",referencedColumnName = "id")
    private Task commentedTask;
}
