package ru.effective_mobile.social_media_api.dto;

import ru.effective_mobile.social_media_api.entity.Status;

public class RelationshipDto {
    private Integer user1Id;

    private Integer user2Id;

    private Status status;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer user1Id;
        private Integer user2Id;
        private Status status;

        public Builder setUser1Id(Integer userId) {
            this.user1Id = userId;
            return this;
        }

        public Builder setUser2Id(Integer userId) {
            this.user2Id = userId;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public RelationshipDto build() {
            return new RelationshipDto(this);
        }
    }

    public RelationshipDto() {
    }

    public RelationshipDto(Builder builder) {
        this.user1Id = builder.user1Id;
        this.user2Id = builder.user2Id;
        this.status = builder.status;
    }

    public Integer getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Integer user1Id) {
        this.user1Id = user1Id;
    }

    public Integer getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Integer user2Id) {
        this.user2Id = user2Id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
