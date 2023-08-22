package ru.effective_mobile.social_media_api.dto;

import java.time.LocalDateTime;

public class PostDto {
    private String header;

    private String text;

    private byte[] image;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Integer userId;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String header;
        private String text;
        private byte[] image;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private Integer userId;

        public Builder setHeader(String header) {
            this.header = header;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setUpdateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public PostDto build() {
            return new PostDto(this);
        }
    }

    public PostDto() {}

    public PostDto(Builder builder) {
        this.header = builder.header;
        this.text = builder.text;
        this.image = builder.image;
        this.createDate = builder.createDate;
        this.updateDate = builder.updateDate;
        this.userId = builder.userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
