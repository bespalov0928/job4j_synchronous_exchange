package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.job4j.model.Passport;
import ru.job4j.repository.PassportRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class PassportService {
    private final PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public ResponseEntity<Passport> save(Passport passport) {
        return new ResponseEntity<Passport>(
                this.passportRepository.save(passport),
                HttpStatus.CREATED
        );
    }

    //    - /update?id=*, обновить данные паспорта
    public ResponseEntity<Void> update(Passport passport) {
//        Passport passportFind = this.passportRepository.findById(id).get();
//        passportFind.setName(passport.getName());
//        passportFind.setLastName(passport.getLastName());
//        passportFind.setSeries(passport.getSeries());
//        passportFind.setNumber(passport.getNumber());
//        passportFind.setBirthday(passport.getBirthday());
//        passportFind.setValidityDate(passport.getValidityDate());
        this.passportRepository.save(passport);
        return ResponseEntity.ok().build();
    }

    //    - /delete?id=*, удалить данные паспорта
    public ResponseEntity<Void> delete(int id) {
        Passport passport = new Passport();
        passport.setId(id);
        this.passportRepository.delete(passport);
        return ResponseEntity.ok().build();
    }

    //    - /find, загрузить все паспорта
    public List<Passport> findAll() {
        return (List<Passport>) this.passportRepository.findAll();
    }

    //    - /find?seria=*, загрузить паспорта с заданной серией
    public ResponseEntity<Passport> findBySeries(String series) {
        var passport = this.passportRepository.findBySeries(series);
        return new ResponseEntity<Passport>(
                passport.orElse(new Passport()),
                passport.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    //    - /unavaliabe, загрузить паспорта чей срок вышел
    public List<Passport> findAllUnavaliabe() {
        List<Passport> list = this.passportRepository.findAllUnavaliabe(new Date());
        return list;
    }

    //    - /find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца
    public List<Passport> findAllFindReplaceable() {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MONTH, 3);
        Date dateEnd = c.getTime();
        Date datestart = new Date();

        List<Passport> list = this.passportRepository.findAllFindReplaceable(datestart, dateEnd);
        return list;
    }

}
