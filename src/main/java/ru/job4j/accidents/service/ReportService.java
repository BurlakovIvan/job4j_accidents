package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Report;
import ru.job4j.accidents.repository.ReportMem;

import java.util.Map;

@Service
@ThreadSafe
@AllArgsConstructor
public class ReportService {

    private final ReportMem store;

    public Map<Integer, Report> findAll() {
        return store.findAll();
    }
}
