package ru.yandex.practicum.javafilmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.service.MpaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    private final MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMpa(@Valid @PathVariable Integer id) {
        return new ResponseEntity<>(mpaService.getMpa(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getMpas() {
        return new ResponseEntity<>(mpaService.getMpas(), HttpStatus.OK);
    }


}
