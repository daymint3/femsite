package com.femlab.femsite.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    private XWPFWordExtractor ex;

    public String readText(File file) throws FileNotFoundException, IOException {
        XWPFDocument doc = new XWPFDocument (new FileInputStream(file));
		ex = new XWPFWordExtractor(doc);

        String text = ex.getText();  // 空白あり
        
        return text;
    }
}
