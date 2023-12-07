package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name ="password" )
    private String password;

    @Column(name ="email" )
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "executor")
    private List<Task> toDoTasks;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    public User(String username) {
        this.username = username;

    }

    public User() {
    }
}
