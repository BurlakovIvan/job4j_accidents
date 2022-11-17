package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Report;

import java.util.HashMap;
import java.util.Map;

@Repository
@ThreadSafe
public class ReportMem {
    private final Map<Integer, Report> reports = new HashMap<>();
    private int ids = 1;

    {
        reports.put(ids++,
                new Report(1, "Report1", 1));
        reports.put(ids++,
                new Report(1, "Report2", 1));
        reports.put(ids++,
                new Report(1, "Report3", 2));
        reports.put(ids++,
                new Report(1, "Report4", 1));
        reports.put(ids++,
                new Report(1, "Report5", 3));
        reports.put(ids++,
                new Report(1, "Report6", 2));
    }

    public Map<Integer, Report> findAll() {
        return new HashMap<>(reports);
    }


}
