package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Passport;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    @Query("select p from passports p where p.series = :series")
    List<Passport> findBySeries(@Param("series") String series);

    @Query("select p from passports p where p.validitydate <= :dateCurrent")
    List<Passport> findAllUnavaliabe(@Param("dateCurrent") Date dateCurrent);

    @Query("select p from passports p where p.validitydate >= :dateStart and p.validitydate <= :dateEnd")
    List<Passport> findAllFindReplaceable(@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);

    @Query("select p from passports p where p.series = :series and p.number = :number")
    Optional<Passport> findBySeriesNumber(@Param("series") Integer series, @Param("number") Integer number);

//    @Transactional
    @Modifying
    @Query("delete from passports p where p.id = :id")
    int deletePasport(@Param("id")Integer id);

//    @Transactional
    @Modifying
    @Query("update passports p set p.number = :number where p.id = :id")
    int updatePassport(@Param("id") int id, @Param("number") int number);
}
