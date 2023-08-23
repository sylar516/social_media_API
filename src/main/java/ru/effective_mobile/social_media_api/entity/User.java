package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "social_media", name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "relationshipId.user1")
    private List<Relationship> relationships;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }
}
