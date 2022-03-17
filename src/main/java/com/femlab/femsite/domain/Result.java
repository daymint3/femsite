package com.femlab.femsite.domain;

// 目的：検索結果で表示するResultのまとまり
public class Result {
    public String fileName;  // ファイル名
    public String href;      // パス
    public String snippet;   // スニペット

    public Result(String fileName, String href, String snippet) {
        this.fileName = fileName;
        this.href     = href;
        this.snippet  = snippet;
    }
}
