package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger idAccident = new AtomicInteger(0);
    private final AtomicInteger idType = new AtomicInteger(0);
    private final AtomicInteger idRule = new AtomicInteger(0);

    public AccidentMem() {
        int id = idType.incrementAndGet();
        types.put(id, new AccidentType(id, "Две машины"));
        id = idType.incrementAndGet();
        types.put(id, new AccidentType(id, "Машина и человек"));
        id = idType.incrementAndGet();
        types.put(id, new AccidentType(id, "Машина и велосипед"));

        id = idRule.incrementAndGet();
        rules.put(id, new Rule(id, "Статья. 1"));
        id = idRule.incrementAndGet();
        rules.put(id, new Rule(id, "Статья. 2"));
        id = idRule.incrementAndGet();
        rules.put(id, new Rule(id, "Статья. 3"));

        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident1", "Description1",
                        "Address1", types.get(1), Set.of(rules.get(1), rules.get(3))));
        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident2", "Description2",
                        "Address2", types.get(3), Set.of(rules.get(2), rules.get(3))));
        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident3", "Description3",
                        "Address3", types.get(2), Set.of(rules.get(1))));
        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident4", "Description4",
                        "Address4", types.get(1), Set.of(rules.get(3), rules.get(2))));
        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident5", "Description5",
                        "Address5", types.get(2), Set.of(rules.get(1), rules.get(3))));
        id = idAccident.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident6", "Description6",
                        "Address6", types.get(3), Set.of(rules.get(2), rules.get(3))));
    }

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    public List<AccidentType> findAllAccidentType() {
        return types.values().stream().toList();
    }

    public List<Rule> findAllRule() {
        return rules.values().stream().toList();
    }

    public Set<Rule> findRule(List<Integer> rulesList) {
        Set<Rule> result = new HashSet<>();
        for (int id : rulesList) {
            result.add(rules.get(id));
        }
        return result;
    }

    public void create(Accident accident) {
        int id = idAccident.incrementAndGet();
        accident.setType(types.get(accident.getType().getId()));
        accident.setId(id);
        accidents.put(id, accident);
    }

    public void update(Accident accident) {
        accident.setType(types.get(accident.getType().getId()));
        accidents.replace(accident.getId(), accident);
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }

}
