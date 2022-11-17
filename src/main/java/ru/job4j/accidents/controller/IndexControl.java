package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.ReportService;

@Controller
@ThreadSafe
@AllArgsConstructor
public class IndexControl {

    private final AccidentService accidentService;
    private final ReportService reportService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll().values());
        model.addAttribute("reports", reportService.findAll().values());
        return "index";
    }
}
