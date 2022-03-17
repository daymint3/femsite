package com.femlab.femsite.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.atilika.kuromoji.ipadic.Token;
import com.femlab.femsite.domain.Dictionary;
import com.femlab.femsite.domain.Postings;
import com.femlab.femsite.repository.DictionaryRepository;
import com.femlab.femsite.repository.PostingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexingService {

    @Autowired    
    PostingRepository postingRepo;

    @Autowired
    DictionaryRepository dictionaryRepo;

    @Autowired
    WordService msword;

    @Autowired
    TxtService txt;

    @Autowired
    MorphemeService morpheme;

    // @Async
    public void execute(String dir, File file) throws FileNotFoundException, IOException {
        String title = file.getName();
        String href = dir;
        if(href.equals("/seminer")) href = "/download";
        String ext = title.substring(title.lastIndexOf(".")+1);  // 拡張子を得る（docx, txt）

        // DB(POSTING)に追加
        Postings pdata = new Postings();
        pdata.setTitle(title);
        pdata.setDir(dir);
        pdata.setHref(href + "/" + title);
        postingRepo.saveAndFlush(pdata);

        int posId = pdata.getPosId();

        String text = "";

        // テキストの取得
        if(ext.equals("docx") || ext.equals("doc")){  // wordfile
            text = msword.readText(file);
        }else if(ext.equals("txt")) {
            text = txt.readTxt(file);
        }

        // 形態素解析
        List<Token> tokens = morpheme.analysis(text);

        // DB(DICTIONARY)へ登録
        List<Dictionary> dataList = new ArrayList<Dictionary>();
        for(Token token : tokens){
            String type = token.getAllFeaturesArray()[0];  // 動詞、名詞など
            String word = "";

            if(type.equals("名詞")) {
                word = token.getSurface();
            } else if(type.equals("動詞")) {
                word = token.getBaseForm();
            } else continue;

            Dictionary ddata = new Dictionary();
            ddata.setWord(word);
            ddata.setPosId(posId);
            ddata.setPosition(token.getPosition());
            dataList.add(ddata);
        }

        dictionaryRepo.saveAll(dataList);

        return;
    }
}
