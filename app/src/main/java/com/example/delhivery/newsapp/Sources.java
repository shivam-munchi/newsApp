package com.example.delhivery.newsapp;

import com.google.gson.annotations.SerializedName;

public class Sources {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("url")
    String url;

    @SerializedName("category")
    String category;

    //do something better
    @SerializedName("language")
    String lang;

    // do something better
    @SerializedName("country")
    String country;

//    public Sources(String id, String name, String description, String url, String category, String lang, String country) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.url = url;
//        this.category = category;
//        this.lang = lang;
//        this.country = country;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
