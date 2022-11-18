package ru.job4j.accident.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Report;
import ru.job4j.accident.repository.ReportMem;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
@AllArgsConstructor
public class ReportService {

    private final ReportMem store;

    public List<Report> findAll() {
        return store.findAll();
    }

    public void create(Report report) {
        store.create(report);
    }

    public void update(Report report) {
        store.update(report);
    }

    public Optional<Report> findById(int reportId) {
        return store.findById(reportId);
    }
}
