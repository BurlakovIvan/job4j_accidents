package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.HashMap;
import java.util.Map;

@Repository
@ThreadSafe
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private int ids = 1;

    {
        accidents.put(ids++,
                new Accident(1, "Accident1", "Description1", "Address1"));
        accidents.put(ids++,
                new Accident(1, "Accident2", "Description2", "Address2"));
        accidents.put(ids++,
                new Accident(1, "Accident3", "Description3", "Address3"));
        accidents.put(ids++,
                new Accident(1, "Accident4", "Description4", "Address4"));
        accidents.put(ids++,
                new Accident(1, "Accident5", "Description5", "Address5"));
        accidents.put(ids++,
                new Accident(1, "Accident6", "Description6", "Address6"));
    }

    public Map<Integer, Accident> findAll() {
        return new HashMap<>(accidents);
    }


}
