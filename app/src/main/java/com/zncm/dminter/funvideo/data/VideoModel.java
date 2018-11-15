package com.zncm.dminter.funvideo.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 */
@Entity
public class VideoModel  extends Base{
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String tag;
    private String desc;
    private String url;
    private int like;//1喜欢
    private int type;//1正常，2失效

    private String ex1;
    private String ex2;
    private String ex3;
    private String ex4;
    private String ex5;

    private int exi1;
    private int exi2;
    private int exi3;
    private int exi4;
    private int exi5;


    public VideoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public VideoModel(String name, String tag, String url) {
        this.name = name;
        this.tag = tag;
        this.url = url;
    }

    public VideoModel(String url) {
        this.url = url;
    }

    @Generated(hash = 670586550)
    public VideoModel(Long id, String name, String tag, String desc, String url,
            int like, int type, String ex1, String ex2, String ex3, String ex4,
            String ex5, int exi1, int exi2, int exi3, int exi4, int exi5) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.desc = desc;
        this.url = url;
        this.like = like;
        this.type = type;
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.ex5 = ex5;
        this.exi1 = exi1;
        this.exi2 = exi2;
        this.exi3 = exi3;
        this.exi4 = exi4;
        this.exi5 = exi5;
    }

    @Generated(hash = 809074874)
    public VideoModel() {
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }

    public String getEx5() {
        return ex5;
    }

    public void setEx5(String ex5) {
        this.ex5 = ex5;
    }

    public int getExi1() {
        return exi1;
    }

    public void setExi1(int exi1) {
        this.exi1 = exi1;
    }

    public int getExi2() {
        return exi2;
    }

    public void setExi2(int exi2) {
        this.exi2 = exi2;
    }

    public int getExi3() {
        return exi3;
    }

    public void setExi3(int exi3) {
        this.exi3 = exi3;
    }

    public int getExi4() {
        return exi4;
    }

    public void setExi4(int exi4) {
        this.exi4 = exi4;
    }

    public int getExi5() {
        return exi5;
    }

    public void setExi5(int exi5) {
        this.exi5 = exi5;
    }
}
