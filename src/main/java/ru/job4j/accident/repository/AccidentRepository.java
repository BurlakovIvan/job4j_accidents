package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    @Query("SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules")
    List<Accident> findAll();

    @Query("SELECT DISTINCT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :id")
    Optional<Accident> findById(@Param("id") Integer accidentId);
}