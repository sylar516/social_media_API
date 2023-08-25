package ru.effective_mobile.social_media_api.exception;

public class RelationshipAlreadyExistException extends RuntimeException {
    public RelationshipAlreadyExistException(String message) {
        super(message);
    }
}
