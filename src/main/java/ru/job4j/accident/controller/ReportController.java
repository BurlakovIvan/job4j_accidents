package ru.job4j.accident.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Report;
import ru.job4j.accident.service.ReportService;

import java.util.Optional;

@Controller
@ThreadSafe
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("reports", reportService.findAll());
        return "reports";
    }

    @GetMapping("/addReport")
    public String viewCreateReport() {
        return "createReport";
    }

    @PostMapping("/saveReport")
    public String save(@ModelAttribute Report report) {
        reportService.create(report);
        return "redirect:/reports";
    }

    @GetMapping("/editReport/{reportId}")
    public String editReport(Model model, @PathVariable("reportId") int id) {
        Optional<Report> report = reportService.findById(id);
        String redirect = "redirect:/reports";
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            redirect = "editReport";
        }
        return redirect;
    }

    @PostMapping("/updateReport")
    public String updateReport(@ModelAttribute Report report) {
        reportService.update(report);
        return "redirect:/reports";
    }
}
