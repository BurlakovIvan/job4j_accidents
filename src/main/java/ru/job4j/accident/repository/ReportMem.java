package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Report;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class ReportMem {
    private final Map<Integer, Report> reports = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(0);

    public ReportMem() {
        int id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report1", 1));
        id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report2", 1));
        id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report3", 2));
        id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report4", 1));
        id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report5", 3));
        id = ids.incrementAndGet();
        reports.put(id,
                new Report(id, "Report6", 2));
    }

    public List<Report> findAll() {
        return reports.values().stream().toList();
    }

    public void create(Report report) {
        int id = ids.incrementAndGet();
        report.setId(id);
        reports.put(id, report);
    }

    public void update(Report report) {
        reports.put(report.getId(), report);
    }

    public Optional<Report> findById(int reportId) {
        return Optional.ofNullable(reports.get(reportId));
    }
}
