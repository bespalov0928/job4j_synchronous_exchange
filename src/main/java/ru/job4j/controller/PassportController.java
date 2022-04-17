package ru.job4j.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Passport;
import ru.job4j.service.PassportService;

import java.util.*;

@RestController
@RequestMapping("/passport")
public class PassportController {
    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    //    - /save, сохранить данные паспорта
    @PostMapping("/save")
    public ResponseEntity<Passport> save(@ModelAttribute Passport passport) {
        return this.passportService.save(passport);
    }

    //    - /update?id=*, обновить данные паспорта
    @PutMapping("/update?id=*")
    public ResponseEntity<Void> update(@RequestParam int id, @RequestBody Passport passport) {
        this.passportService.update(id, passport);
        return ResponseEntity.ok().build();
    }

    //    - /delete?id=*, удалить данные паспорта
    @DeleteMapping("/delete?id=*")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        return this.passportService.delete(id);
    }

    //    - /find, загрузить все паспорта
    @GetMapping("/find")
    public List<Passport> findAll() {
        return (List<Passport>) this.passportService.findAll();
    }

    //    - /find?seria=*, загрузить паспорта с заданной серией
    @GetMapping("/find?seria=*")
    public ResponseEntity<Passport> findBySeries(@RequestParam String series) {
        return this.passportService.findBySeries(series);
    }

    //    - /unavaliabe, загрузить паспорта чей срок вышел
    @GetMapping("/unavaliabe")
    public List<Passport> findAllUnavaliabe() {
        return this.passportService.findAllUnavaliabe();
    }

    //    - /find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца
    @GetMapping("/findReplaceable")
    public List<Passport> findAllFindReplaceable() {
        return this.passportService.findAllFindReplaceable();
    }

}
