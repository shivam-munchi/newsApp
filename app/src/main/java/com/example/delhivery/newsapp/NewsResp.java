package com.example.delhivery.newsapp;

import java.util.ArrayList;

class NewsResp {
    private String status;
    private int totalResults;
    private ArrayList<ArticleStructure> articles;

    public NewsResp(String status, int totalResults, ArrayList<ArticleStructure> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<ArticleStructure> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticleStructure> articles) {
        this.articles = articles;
    }

}
