package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.storage.dbimpl.film.MpaDbStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Autowired
    MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public Mpa create(Mpa mpa) {
        checkId(mpa.getId());
        return mpaDbStorage.create(mpa);
    }

    public Mpa update(Mpa mpa) {
        checkId(mpa.getId());
        return mpaDbStorage.update(mpa);
    }

    public Mpa getMpa(Integer id) {
        log.info("Получен GET запрос по эндпоинту '/mpa/{id}' на получение ретинга по id");
        checkId(id);
        return mpaDbStorage.getMpa(id);
    }

    public List<Mpa> getMpas() {
        log.info("Получен GET запрос по эндпоинту '/mpa' на получение всех рейтингов");
        return mpaDbStorage.getMpas();
    }

    public void checkId(Integer id) {
        log.info("Mpa с id = {} не найден.", id);
        if (id == null) throw new NotFoundException("MPA с id=%d нет в списке", id);
    }

}
