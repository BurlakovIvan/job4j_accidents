package ru.job4j.accidents.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ThreadSafe
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }
}
