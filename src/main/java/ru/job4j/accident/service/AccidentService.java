package ru.job4j.accident.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@ThreadSafe
@AllArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentsRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    public List<Accident> findAll() {
        return accidentsRepository.findAll();
    }

    public List<AccidentType> findAllAccidentType() {
        return accidentTypeRepository.findAll();
    }

    public List<Rule> findAllRule() {
        return ruleRepository.findAll();
    }

    public Set<Rule> findRule(List<Integer> rulesList) {
        return ruleRepository.findRule(rulesList);
    }

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public void update(Accident accident) {
        accidentsRepository.save(accident);
    }
    public Optional<Accident> findById(int id) {
        return accidentsRepository.findById(id);
    }

    public Optional<AccidentType> findByAccidentTypeId(int id) {
        return accidentTypeRepository.findById(id);
    }
}
