package com.android.newsapp;

public class NewsItem {

    private String sectionName;
    private String articleTitle;
    private String articleUrl;
    private String articleAuthor;

    public NewsItem() {
    }

    public NewsItem(String sectionName, String articleTitle, String articleUrl, String articleAuthor) {
        this.sectionName = sectionName;
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.articleAuthor = articleAuthor;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }
}
