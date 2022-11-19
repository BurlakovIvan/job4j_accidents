package ru.job4j.accident.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/addAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.findAllAccidentType());
        model.addAttribute("rules", accidentService.findAllRule());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam(name = "ruleId", required = false)
                       List<Integer> ruleId) {
        accident.setRules(accidentService.findRule(ruleId));
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(Model model, @RequestParam("id") int id) {
        Optional<Accident> accident = accidentService.findById(id);
        String redirect = "index";
        if (accident.isPresent()) {
            model.addAttribute("accident", accident.get());
            model.addAttribute("types", accidentService.findAllAccidentType());
            model.addAttribute("rules", accidentService.findAllRule());
            redirect = "formUpdateAccident";
        }
        return redirect;
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 @RequestParam(name = "ruleId", required = false)
                                 List<Integer> ruleId) {
        accident.setRules(accidentService.findRule(ruleId));
        accidentService.update(accident);
        return "redirect:/index";
    }
}
