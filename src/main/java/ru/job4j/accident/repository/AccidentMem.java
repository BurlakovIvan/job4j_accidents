package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(0);

    public AccidentMem() {
        int id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident1", "Description1", "Address1"));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident2", "Description2", "Address2"));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident3", "Description3", "Address3"));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident4", "Description4", "Address4"));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident5", "Description5", "Address5"));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident6", "Description6", "Address6"));
    }

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    public void create(Accident accident) {
        int id = ids.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }

}
