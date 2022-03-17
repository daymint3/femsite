package com.femlab.femsite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// データベースの1つのレコード分に相当
@Entity  // JPAの利用
@Data    // アクセッサなどの自動生成
@Table(name="POSTINGS")
public class Postings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int posId;     // 固有のファイルID
                           // 【注意】データベースには以下のように登録される
                           // posid         => POSID
                           // posId, pos_id => POS_ID

    @Column(nullable = false)
    private String title;  // ファイル名

    @Column(nullable = false)
    private String dir;    // ファイルが存在するディレクトリ

    @Column(nullable = false)
    private String href;   // ファイルへのパス

    // アクセッサ
    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
