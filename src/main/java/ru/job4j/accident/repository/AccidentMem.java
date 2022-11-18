package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@ThreadSafe
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private int ids = 1;

    {
        accidents.put(ids,
                new Accident(ids++, "Accident1", "Description1", "Address1"));
        accidents.put(ids,
                new Accident(ids++, "Accident2", "Description2", "Address2"));
        accidents.put(ids,
                new Accident(ids++, "Accident3", "Description3", "Address3"));
        accidents.put(ids,
                new Accident(ids++, "Accident4", "Description4", "Address4"));
        accidents.put(ids,
                new Accident(ids++, "Accident5", "Description5", "Address5"));
        accidents.put(ids,
                new Accident(ids++, "Accident6", "Description6", "Address6"));
    }

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    public void create(Accident accident) {
        accident.setId(ids);
        accidents.put(ids++, accident);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }

}
