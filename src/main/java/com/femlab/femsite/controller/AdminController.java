package com.femlab.femsite.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.femlab.femsite.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    AdminService administrator;

    @GetMapping("/admin")
    public String getadmin(){
        return "admin";
    }

    // データベース（Index）のリセット
    @PostMapping("/admin")
    public String executeResetIndex(Model model) throws FileNotFoundException, IOException{
        administrator.resetIndex();
        model.addAttribute("alart", "DBをリセットしました");
        return "admin";
    }
}
