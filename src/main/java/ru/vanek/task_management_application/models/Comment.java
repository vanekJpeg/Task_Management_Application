package ru.vanek.task_management_application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Entity
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
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private User author;

    @ManyToOne()
    @JoinColumn(name = "task_id",referencedColumnName = "id")
    private Task commentedTask;

    public Comment() {
    }

    public Comment(int id, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(int id, String text, Date createdAt, User author, Task commentedTask) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
        this.commentedTask = commentedTask;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", author=" + author +
                ", commentedTask=" + commentedTask +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && Objects.equals(text, comment.text) && Objects.equals(createdAt, comment.createdAt) && Objects.equals(author, comment.author) && Objects.equals(commentedTask, comment.commentedTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt, author, commentedTask);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Task getCommentedTask() {
        return commentedTask;
    }

    public void setCommentedTask(Task commentedTask) {
        this.commentedTask = commentedTask;
    }
}
