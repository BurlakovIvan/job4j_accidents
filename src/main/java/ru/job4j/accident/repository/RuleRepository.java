package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Set;

public interface RuleRepository extends CrudRepository<Rule, Integer>  {
    @Query("SELECT r FROM Rule r")
    List<Rule> findAll();

    @Query("SELECT r FROM Rule r WHERE id IN (:id)")
    Set<Rule> findRule(List<Integer> id);
}
