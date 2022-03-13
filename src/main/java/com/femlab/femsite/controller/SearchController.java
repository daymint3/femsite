package com.femlab.femsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {
    
    // 【注意】再検索のために変数qを同名で保持すること
    //         Googleでの検索にも使うため, 変数名はq
    @GetMapping("/search")
    public String getSearchHtml(@ModelAttribute("q") String q, Model model) {
        model.addAttribute("q", q);  // 再検索のために変数qを保持
        
        return "search";
    }
}
