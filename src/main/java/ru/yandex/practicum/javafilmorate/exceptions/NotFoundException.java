package ru.yandex.practicum.javafilmorate.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Integer id) {
        super(msg);
    }
}
