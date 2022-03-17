package com.femlab.femsite.service;

import java.util.List;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import org.springframework.stereotype.Service;

@Service
public class MorphemeService {
    public List<Token> analysis(String text) {
        Tokenizer tokenizer = new Tokenizer() ;             // Kuromojiオブジェクト作成

        List<Token> tokens = tokenizer.tokenize(text);      // Tokenize

        // テスト用
        /*
        for (Token token : tokens) {
            System.out.println("----------------------");
            System.out.println("語幹:" + token.getBaseForm()); // Tokenの語幹
            System.out.println("品詞:" + token.getPartOfSpeechLevel1());
            System.out.println("位置:" + token.getPosition());
        }*/

        return tokens;
    }
}
