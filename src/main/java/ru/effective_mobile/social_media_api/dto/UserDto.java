package ru.effective_mobile.social_media_api.dto;

import java.util.List;

public class UserDto {
    private String name;

    private String email;

    private List<PostDto> posts;

    private List<RelationshipDto> relationships;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private List<PostDto> posts;
        private List<RelationshipDto> relationships;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPosts(List<PostDto> posts) {
            this.posts = posts;
            return this;
        }

        public Builder setRelationships(List<RelationshipDto> relationships) {
            this.relationships = relationships;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

    public UserDto() {
    }

    public UserDto(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.posts = builder.posts;
        this.relationships = builder.relationships;
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

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<RelationshipDto> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipDto> relationships) {
        this.relationships = relationships;
    }
}
