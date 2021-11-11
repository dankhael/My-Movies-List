package com.mymovieslist.model;

import com.google.gson.JsonObject;

public class ImdbMovie {
    JsonObject searchJson;
    String ratingsJson;

    public ImdbMovie(JsonObject searchJson, String ratingsJson){
        this.searchJson = searchJson;
        this.ratingsJson = ratingsJson;
    }

    public JsonObject getSearchJson(){
        return this.searchJson;
    }

    public void setSearchJson(JsonObject searchJson){
        this.searchJson = searchJson;
    }

    public String getRatingsJson(){
        return this.ratingsJson;
    }

    public void setRatingsJson(String ratingsJson){
        this.ratingsJson = ratingsJson;
    }
}
