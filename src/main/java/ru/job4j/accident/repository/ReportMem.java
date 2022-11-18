package ru.job4j.accident.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Report;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@ThreadSafe
public class ReportMem {
    private final Map<Integer, Report> reports = new ConcurrentHashMap<>();
    private int ids = 1;

    {
        reports.put(ids,
                new Report(ids++, "Report1", 1));
        reports.put(ids,
                new Report(ids++, "Report2", 1));
        reports.put(ids,
                new Report(ids++, "Report3", 2));
        reports.put(ids,
                new Report(ids++, "Report4", 1));
        reports.put(ids,
                new Report(ids++, "Report5", 3));
        reports.put(ids,
                new Report(ids++, "Report6", 2));
    }

    public List<Report> findAll() {
        return reports.values().stream().toList();
    }

    public void create(Report report) {
        report.setId(ids);
        reports.put(ids++, report);
    }

    public void update(Report report) {
        reports.put(report.getId(), report);
    }

    public Optional<Report> findById(int reportId) {
        return Optional.ofNullable(reports.get(reportId));
    }
}
