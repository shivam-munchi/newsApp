package com.example.delhivery.newsapp;

import com.google.gson.annotations.SerializedName;

public class ErrorResp {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String msg;

    public ErrorResp(String status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }
}
