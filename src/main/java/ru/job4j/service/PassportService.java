package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.PassportException;
import ru.job4j.model.Passport;
import ru.job4j.repository.PassportRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@EnableScheduling
public class PassportService {
    private final PassportRepository passportRepository;
    @Autowired
    private KafkaTemplate<Integer, String> template;


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
        int rsl = this.passportRepository.updatePassport(passport.getId(), passport.getNumber());
        return rsl > 0 ? true : false;
    }

    /**
     * delete?id=*, удалить данные паспорта
     */
    public boolean delete(int id) {
        int rsl = this.passportRepository.deletePasport(id);
        return rsl > 0 ? true : false;
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
    @Scheduled(fixedDelay = 1000)
    public List<Passport> findAllUnavaliabe() {
        List<Passport> list = this.passportRepository.findAllUnavaliabe(new Date());
        if (list.isEmpty()) {
            for (Passport passport:list) {
                template.send("unavaliabe", passport.toString());
            }
        }

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
