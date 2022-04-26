package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.job4j.PassportException;
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

    public Passport save(Passport passport) {
        try {
            return this.passportRepository.save(passport);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update?id=*, обновить данные паспорта
     */
    public boolean update(Passport passport) {
        if (findById(passport.getId()) != null) {
            return false;
        }
        this.passportRepository.save(passport);
        return true;
    }

    /**
     * delete?id=*, удалить данные паспорта
     */
    public boolean delete(int id) {
        if (findById(id) != null) {
            return false;
        }
        Passport passport = new Passport();
        passport.setId(id);
        this.passportRepository.delete(passport);
        return true;
    }

    /**
     * ind, загрузить все паспорта
     *
     * @return
     */
    public List<Passport> findAll() {
        return (List<Passport>) this.passportRepository.findAll();
    }

    /**
     * find?seria=*, загрузить паспорта с заданной серией
     *
     * @param series
     * @return
     */
    public List<Passport> findBySeries(String series) {
        return this.passportRepository.findBySeries(series);
    }

    /**
     * unavaliabe, загрузить паспорта чей срок вышел
     *
     * @return
     */
    public List<Passport> findAllUnavaliabe() {
        List<Passport> list = this.passportRepository.findAllUnavaliabe(new Date());
        return list;
    }

    /**
     * find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца
     *
     * @return
     */
    public List<Passport> findAllFindReplaceable() {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MONTH, 3);
        Date dateEnd = c.getTime();
        Date datestart = new Date();

        List<Passport> list = this.passportRepository.findAllFindReplaceable(datestart, dateEnd);
        return list;
    }

    public Passport findById(int id) {
        return this.passportRepository.findById(id).orElse(null);
    }

    public Passport findBySeriesNumber(Integer series, Integer number) {
        return this.passportRepository.findBySeriesNumber(series, number).orElse(null);
    }


}
