package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    private List<Task> createdTasks;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "executor")
    private List<Task> toDoTasks;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    public User(String username) {
        this.username = username;

    }

    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public User(int id, String username, String password, String email, List<Task> createdTasks, List<Task> toDoTasks, List<Comment> comments, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdTasks = createdTasks;
        this.toDoTasks = toDoTasks;
        this.comments = comments;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(List<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public List<Task> getToDoTasks() {
        return toDoTasks;
    }

    public void setToDoTasks(List<Task> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(createdTasks, user.createdTasks) && Objects.equals(toDoTasks, user.toDoTasks) && Objects.equals(comments, user.comments) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, createdTasks, toDoTasks, comments, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdTasks=" + createdTasks +
                ", toDoTasks=" + toDoTasks +
                ", comments=" + comments +
                ", roles=" + roles +
                '}';
    }
}
