package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.PassportException;
import ru.job4j.model.Passport;
import ru.job4j.service.PassportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;

@RestController
@RequestMapping("/passport")
public class PassportController {
    private final PassportService passportService;
    private final ObjectMapper objectMapper;

    public PassportController(PassportService passportService, ObjectMapper objectMapper) {
        this.passportService = passportService;
        this.objectMapper = objectMapper;
    }

    /**
     * /save, сохранить данные паспорта
     */
    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) throws PassportException {
        Passport passportSave = this.passportService.save(passport);
        if (passportSave == null) {
            throw new PassportException("Passport with this series and number already exists");
        }
        return new ResponseEntity<Passport>(
                passportSave,
                HttpStatus.CREATED);
    }

    /**
     * /update?id=*, обновить данные паспорта
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Passport passport) {
        if (this.passportService.update(passport)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * /delete?id=*, удалить данные паспорта
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (this.passportService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * /find, загрузить все паспорта
     */
    @GetMapping("/find")
    public List<Passport> findAll() {
        return (List<Passport>) this.passportService.findAll();
    }

    /**
     * /find?seria=*, загрузить паспорта с заданной серией
     */
    @GetMapping("/find?seria=*")
    public List<Passport> findBySeries(@RequestParam String series) {
        return this.passportService.findBySeries(series);
    }

    /**
     * /unavaliabe, загрузить паспорта чей срок вышел
     */
    @GetMapping("/unavaliabe")
    public List<Passport> findAllUnavaliabe() {
        return this.passportService.findAllUnavaliabe();
    }

    /**
     * /find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца
     */
    @GetMapping("/findReplaceable")
    public List<Passport> findAllFindReplaceable() {
        return this.passportService.findAllFindReplaceable();
    }

    @ExceptionHandler(value = {PassportException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
    }


}
