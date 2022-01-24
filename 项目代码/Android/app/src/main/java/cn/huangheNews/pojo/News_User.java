package cn.huangheNews.pojo;

import java.util.Date;

public class News_User {
    private int id;
    private String headline;
    private String author;
    private String content;
    private Date date;
    private String type;
    private int click;
    private int comment;
    private int first;
    private String petname;
    private String sex;

    public News_User(int id, String headline, String author, String content, Date date, String type, int click, int comment, int first, String petname, String sex) {
        this.id = id;
        this.headline = headline;
        this.author = author;
        this.content = content;
        this.date = date;
        this.type = type;
        this.click = click;
        this.comment = comment;
        this.first = first;
        this.petname = petname;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
