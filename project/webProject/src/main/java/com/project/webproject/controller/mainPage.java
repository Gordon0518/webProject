package com.project.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainPage {

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/mainPage";
    }

    @GetMapping("/mainPage")
    public String mainPageView() {
        return "mainPage";
    }

    @GetMapping("/test")
    public String testPage() {
        return "testPage";
    }
}