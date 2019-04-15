package com.example.news;

import java.io.Serializable;

//Model Class
public class News implements Serializable {
    private String heading;
    private String url;
    private String author;
    private String pubDate;
    private String snippet;
private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
    public News(int id,String heading, String url, String author, String pubDate, String snippet) {
        this.id = id;
        this.heading = heading;
        this.url = url;
        this.author = author;
        this.pubDate = pubDate;
        this.snippet = snippet;
    }

    public News(String heading, String url, String author, String pubDate, String snippet) {
        this.heading = heading;
        this.url = url;
        this.author = author;
        this.pubDate = pubDate;
        this.snippet = snippet;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
