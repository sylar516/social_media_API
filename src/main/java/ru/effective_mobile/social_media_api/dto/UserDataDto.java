package ru.effective_mobile.social_media_api.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDataDto {
    @NotBlank(message = "name is required field")
    private String name;

    @NotBlank(message = "email is required field")
    private String email;

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
}
