package ru.yandex.practicum.javafilmorate.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }
}
