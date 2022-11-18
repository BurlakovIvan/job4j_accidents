package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final List<AccidentType> types = new ArrayList<>();
    private final AtomicInteger ids = new AtomicInteger(0);

    public AccidentMem() {
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));

        int id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident1", "Description1", "Address1", types.get(0)));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident2", "Description2", "Address2", types.get(2)));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident3", "Description3", "Address3", types.get(1)));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident4", "Description4", "Address4", types.get(0)));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident5", "Description5", "Address5", types.get(1)));
        id = ids.incrementAndGet();
        accidents.put(id,
                new Accident(id, "Accident6", "Description6", "Address6", types.get(2)));
    }

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    public List<AccidentType> findAllAccidentType() {
        return new ArrayList<>(types);
    }

    public void create(Accident accident) {
        int id = ids.incrementAndGet();
        accident.setType(types.get(accident.getType().getId() - 1));
        accident.setId(id);
        accidents.put(id, accident);
    }

    public void update(Accident accident) {
        accident.setType(types.get(accident.getType().getId() - 1));
        accidents.put(accident.getId(), accident);
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }

}
