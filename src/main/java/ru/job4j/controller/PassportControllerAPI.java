package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.model.Passport;

import java.util.List;

@RestController
@RequestMapping("/passport_api")
public class PassportControllerAPI {

    private static final String API_SAVE = "http://localhost:8080/passport/save";
    private static final String API_UPDATE = "http://localhost:8080/passport/update";
    private static final String API_DELETE = "http://localhost:8080/passport/delete/%s";
    private static final String API_FIND = "http://localhost:8080/passport/find";
    private static final String API_FINDID = "http://localhost:8080/passport/find?seria=%s";
    private static final String API_UNAVALIABE = "http://localhost:8080/passport/unavaliabe";
    private static final String API_FINDREPLACEABLE = "http://localhost:8080/passport/findReplaceable";

    @Autowired
    private RestTemplate rest;

    @PostMapping("/save")
    public Passport save(@RequestBody Passport passport) {
        Passport rsl = rest.postForObject(API_SAVE, passport, Passport.class);
        return rsl;
    }

    @PutMapping("/update")
    public Void update(@RequestBody Passport passport) {
        rest.put(API_UPDATE, passport, Passport.class);
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(String.format(API_DELETE, id));
        return null;
    }

    @GetMapping("/find")
    public List<Passport> findAll() {
        List<Passport> rsl = rest.exchange(API_FIND, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>(){}
        ).getBody();
        return rsl;
    }

    @GetMapping("/find?seria=*")
    public Passport findBySeries(@RequestParam String series) {
        Passport rsl = rest.getForObject(String.format(String.format(API_FINDID, series), series), Passport.class);
        return rsl;
    }

    @GetMapping("/unavaliabe")
    public List<Passport> findAllUnavaliabe() {
        List<Passport> rsl = rest.exchange(API_UNAVALIABE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>(){}
        ).getBody();
        return rsl;
    }

    @GetMapping("/findReplaceable")
    public List<Passport> findAllFindReplaceable() {
        List<Passport> rsl = rest.exchange(API_FINDREPLACEABLE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>(){}
        ).getBody();
        return rsl;
    }







}
