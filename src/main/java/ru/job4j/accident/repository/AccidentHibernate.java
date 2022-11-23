package ru.job4j.accident.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final CrudRepository crudRepository;

    private final static String ALL_ACCIDENT = """
                                 SELECT DISTINCT a FROM Accident a
                                 LEFT JOIN FETCH a.type
                                 LEFT JOIN FETCH a.rules
                                 """;

    private final static String FIND_ALL_ACCIDENT
            = String.format("%s ORDER BY a.id", ALL_ACCIDENT);

    private final static String SELECT_RULES = "SELECT r FROM Rule r";

    private final static String SELECT_ACCIDENT_TYPE = "SELECT a FROM AccidentType a";

    private final static String FIND_ACCIDENT
            = String.format("%s WHERE a.id = :fId ORDER BY a.id", ALL_ACCIDENT);

    public Accident create(Accident accident) {
        Accident rsl = accident;
        try {
            crudRepository.run(session -> session.save(accident));
        } catch (Exception e) {
            rsl = null;
        }
        return rsl;
    }

    public List<Accident> findAll() {
        List<Accident> rsl;
        try {
            rsl = crudRepository.query(FIND_ALL_ACCIDENT, Accident.class);
        } catch (Exception e) {
            rsl = new ArrayList<>();
        }
        return rsl;
    }

    public List<Rule> findAllRule() {
        List<Rule> rsl;
        try {
            rsl = crudRepository.query(SELECT_RULES, Rule.class);
        } catch (Exception e) {
            rsl = new ArrayList<>();
        }
        return rsl;
    }

    public List<AccidentType> findAllAccidentType() {
        List<AccidentType> rsl;
        try {
            rsl = crudRepository.query(SELECT_ACCIDENT_TYPE, AccidentType.class);
        } catch (Exception e) {
            rsl = new ArrayList<>();
        }
        return rsl;
    }

    public Set<Rule> findRule(List<Integer> rulesList) {
        Set<Rule> rsl;
        try {
            rsl = Set.copyOf(crudRepository
                    .query(String.format("%s WHERE id IN (:fId)", SELECT_RULES),
                            Rule.class, Map.of("fId", rulesList)));
        } catch (Exception e) {
            rsl = new HashSet<>();
            e.printStackTrace();
        }
        return rsl;
    }


    public void update(Accident accident) {
        try {
            crudRepository.run(session -> session.update(accident));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(FIND_ACCIDENT, Accident.class, Map.of("fId", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public Optional<AccidentType> findByAccidentTypeId(int id) {
        Optional<AccidentType> rsl = Optional.empty();
        try {
            rsl = crudRepository
                    .optional(String.format("%s WHERE a.id = :fId", SELECT_ACCIDENT_TYPE),
                            AccidentType.class, Map.of("fId", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

}
