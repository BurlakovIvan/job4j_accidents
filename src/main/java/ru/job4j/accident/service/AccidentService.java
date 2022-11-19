package ru.job4j.accident.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem store;

    public List<Accident> findAll() {
        return store.findAll();
    }

    public List<AccidentType> findAllAccidentType() {
        return store.findAllAccidentType();
    }

    public List<Rule> findAllRule() {
        return store.findAllRule();
    }

    public Set<Rule> findRule(List<Integer> rulesList) {
        return store.findRule(rulesList);
    }

    public void create(Accident accident) {
        store.create(accident);
    }

    public void update(Accident accident) {
        store.update(accident);
    }
    public Optional<Accident> findById(int accidentId) {
        return store.findById(accidentId);
    }
}
