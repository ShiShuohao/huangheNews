package cn.huangheNews.pojo;

public class Type {
    private String name;
    private int newsNumber;
    private int state;

    public Type(String name, int newsNumber, int state) {
        this.name = name;
        this.newsNumber = newsNumber;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewsNumber() {
        return newsNumber;
    }

    public void setNewsNumber(int newsNumber) {
        this.newsNumber = newsNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
