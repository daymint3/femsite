package com.femlab.femsite.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

// テキストファイルの文章を読み取る

@Service
public class TxtService {
    public String readTxt(File file) throws IOException {
        String path = file.getAbsolutePath();
        String text = Files.lines(Paths.get(path), Charset.forName("UTF-8")).collect(Collectors.joining(System.getProperty("line.separator")));
        
        return text;
    }
}
