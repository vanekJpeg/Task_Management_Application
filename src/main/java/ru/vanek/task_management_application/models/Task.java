package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;
import ru.vanek.task_management_application.utils.Status;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "commentedTask")
    private List<Comment> comments;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Task() {
    }

    public Task(int id, String header, String description, Status status, User author, User executor, List<Comment> comments, Date createdAt) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.status = status;
        this.author = author;
        this.executor = executor;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", author=" + author +
                ", executor=" + executor +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(header, task.header) && Objects.equals(description, task.description) && status == task.status && Objects.equals(author, task.author) && Objects.equals(executor, task.executor) && Objects.equals(comments, task.comments) && Objects.equals(createdAt, task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, description, status, author, executor, comments, createdAt);
    }
}

