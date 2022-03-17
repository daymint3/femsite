package com.femlab.femsite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.atilika.kuromoji.ipadic.Token;
import com.femlab.femsite.domain.Dictionary;
import com.femlab.femsite.domain.Postings;
import com.femlab.femsite.repository.DictionaryRepository;
import com.femlab.femsite.repository.PostingRepository;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 検索アルゴリズムの実装
@Service
public class SearchService {

    @Autowired
    PostingRepository postingRepo;

    @Autowired
    DictionaryRepository dictionaryRepo;

    @Autowired
    MorphemeService morpheme;
    
    // 目的：検索ワードからファイルIDと単語の出現位置を取得
    // 入力：検索ワードtext
    // 出力：リスト（pos_id, position）
    // 
    // スコアリングによって検索順位が変化する
    // ここでは, 
    public List<Pair<Integer, Integer>> findPosidAndPosition(String text) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();  // (pos_id,position)のリストで返す;

        String[] words = text.split("[ 　]",0);           // textを全角半角ごとに分割
        Map<Integer,Integer> score    = new HashMap<>();  // map<pos_id, score>
        Map<Integer,Integer> position = new HashMap<>();  // map<pos_id, position>

        // スコアリング
        for(String word : words) {
            // タイトルに含まれている場合
            List<Postings> postingList = postingRepo.findByTitleLike("%" + word + "%");

            for(Postings p : postingList) {
                if(!score.containsKey(p.getPosId())) {  // ない -> 0で初期化
                    score.put(p.getPosId(), 0);
                }

                // 現在のスコアを更新
                int correntScore = score.get(p.getPosId());
                score.put(p.getPosId(), correntScore+100);
            }

            // 本文に含まれている場合
            List<Token> tokens = morpheme.analysis(word);      // 形態素解析
            for(Token token : tokens) {
                String type  = token.getAllFeaturesArray()[0]; // 動詞、名詞など
                String mword = "";

                if(type.equals("名詞")) {
                    mword = token.getSurface();
                } else if(type.equals("動詞")){
                    mword = token.getBaseForm();
                } else continue;

                List<Dictionary> dic = dictionaryRepo.findByWordLike("%" + mword + "%");
                for(Dictionary d : dic) {
                    if(!score.containsKey(d.getPosId())){      //ない -> 0で初期化
                        score.put(d.getPosId(), 0);
                    }

                    int now=score.get(d.getPosId());
                    score.put(d.getPosId(), now+1);

                    if(!position.containsKey(d.getPosId())){  // 最初に現れたポジション
                        position.put(d.getPosId(), d.getPosition());
                    }
                }
            }
        }

        // スコアでソート
        List<Entry<Integer, Integer>> es = new ArrayList<>(score.entrySet());
        es.sort(Entry.comparingByValue());
        
        // 大きい方から返す
        int n = es.size();
        for(int i = 0; i < n; i++) {
            int posId = es.get(n-1-i).getKey();
            result.add(new Pair<Integer, Integer>(posId, position.get(posId)));
        }

        /*result.stream().forEach(p->{
            System.out.println( "key=" + p.getKey() + " value=" + p.getValue() );
         });*/
        return result;
    }
}
