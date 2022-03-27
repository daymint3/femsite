package com.femlab.femsite.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.femlab.femsite.domain.Postings;
import com.femlab.femsite.domain.Result;
import com.femlab.femsite.repository.PostingRepository;
import com.femlab.femsite.service.SearchService;
import com.femlab.femsite.service.TxtService;
import com.femlab.femsite.service.WordService;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {

    @Autowired
    PostingRepository postingRepo;

    @Autowired
    SearchService searcher;

    @Autowired
    WordService msword;

    @Autowired
    TxtService txt;

    final private String path ="repository";
    
    // 【注意】再検索のために変数qを同名で保持すること
    //         Googleでの検索にも使うため, 変数名はq
    @GetMapping("/search")
    public String getSearchHtml(@ModelAttribute("q") String q, Model model) throws IOException {
        List<Pair<Integer,Integer>> res = searcher.findPosidAndPosition(q);
        List<Result> result = new ArrayList<>();

        for(Pair<Integer, Integer> p : res) {
            int posId = p.getKey();
            Postings data = postingRepo.findById(posId);

            String title = data.getTitle();
            String href  = data.getHref();

            String text = "";
            String ext = title.substring(title.lastIndexOf(".")+1);  // 拡張子を得る（docx, txt）
            String FILE_PATH = path + data.getDir() + "/" + data.getTitle();
            String EXTERNAL_FILE_PATH = Paths.get(FILE_PATH).toAbsolutePath().toString();  // 絶対パスに変換
            File file = new File(EXTERNAL_FILE_PATH);

            if(ext.equals("docx") || ext.equals("doc")) {  // Wordファイル
                text = msword.readText(file);
            } else if(ext.equals("txt")) {  // テキストファイル
                text = txt.readTxt(file);
            }

            Integer value = p.getValue();
            if(value == null) value = 0;

            int beginIndex = Math.max(0, value-20);
            int endIndex   = Math.min(value+124, text.length());
            String snippet = text.substring(beginIndex, endIndex);
            
            if(beginIndex > 0) snippet = " ... " + snippet;
            if(endIndex < text.length()) snippet += " ... " ;
            
            result.add(new Result(title, href, snippet));
        }

        model.addAttribute("words", q);  // 再検索のために変数qを保持
        model.addAttribute("result", result);

        return "search";
    }
}
