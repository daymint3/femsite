package com.femlab.femsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FemsiteController {

    // メインページの表示
    @GetMapping("/")
    public String getFemsiteHtml() {
        return "femsite";
    }
}