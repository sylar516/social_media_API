package ru.effective_mobile.social_media_api.exception;

public class PublishByAnotherUserException extends RuntimeException {
    public PublishByAnotherUserException(String message) {
        super(message);
    }
}
