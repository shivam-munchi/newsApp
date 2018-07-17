package com.example.delhivery.newsapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourceResp {

    @SerializedName("status")
    String status;

    @SerializedName("sources")
    List<Sources> src;


//        public SourceResp(String status, List<Sources> src) {
//        this.status = status;
//        this.src = src;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sources> getSrc() {
        return src;
    }

    public void setSrc(List<Sources> src) {
        this.src = src;
    }
}
