package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Passport;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    @Query("select p from Passport p where p.series = :series")
    Optional<Passport> findBySeries(@Param("series") String series);

    @Query("select p from Passport p where p.validityDate <= :dateCurrent")
    List<Passport> findAllUnavaliabe(@Param("dateCurrent")Date dateCurrent);

    @Query("select p from Passport p where p.validityDate <= :dateCurrent")
    List<Passport> findAllFindReplaceable(@Param("dateCurrent")Date dateCurrent);


}
