package com.femlab.femsite.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import com.femlab.femsite.repository.DictionaryRepository;
import com.femlab.femsite.repository.PostingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    @Autowired
    PostingRepository postingRepo;

    @Autowired
    DictionaryRepository dictionaryRepo;

    @Autowired
    IndexingService indexing;

    private static final String FILE_PATH = "repository";

    // DBのリセットおよびindexの再構築
    public void resetIndex() throws FileNotFoundException, IOException{
        //dictionaryRepo.deleteAllInBatch();  // DB(DICTIONARYのリセット)
        postingRepo.deleteAllInBatch();;     // DB(POSTINGSのリセット)

        String[] dirList= {"/seminer"};

        for(int i = 0; i < dirList.length; i++) {
            String EXTERNAL_FILE_PATH = Paths.get(FILE_PATH+dirList[i]).toAbsolutePath().toString();  // 絶対パスに変換
            File dir = new File(EXTERNAL_FILE_PATH);
            File[] list = dir.listFiles();
            for(int j = 0; j < list.length; j++) {
                if(list[j].isFile()) {                      // ファイルのみ
                    indexing.execute(dirList[i], list[j]);  // インデクシングの実行
                }
            }
        }

        return;
    }
}
