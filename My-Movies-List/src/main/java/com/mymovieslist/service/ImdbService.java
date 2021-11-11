package com.mymovieslist.service;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mymovieslist.model.ImdbMovie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class ImdbService {
    @Value("${api.imdbKey}")
    public String myKey;

    public String searchRequest = "https://imdb-api.com/en/API/SearchMovie/";
    public String ratingsRequest = "https://imdb-api.com/en/API/Ratings/";

    public static String getUrlResponse(HttpURLConnection connection, String url){
        String result = new String();
        try {
            URL searchUrl = new URL(url);
            connection = (HttpURLConnection)searchUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line = null;
            while((line=reader.readLine())!= null)
            {
                response.append(line);
                response.append("\r");
            }
            reader.close();
            result = response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JsonArray parseSearch(String search) {
        Gson gson = new Gson();
        JsonObject searchJson = gson.fromJson(search, JsonObject.class);
        JsonArray results = new JsonArray();
        try{
            results = searchJson.getAsJsonArray("results");
        } catch (IllegalStateException e) {
            System.out.print("Error trying to get API results.\nPossibly maximum usage of API calls.");
        }
        return results;
    }

    public ArrayList<ImdbMovie> getMovies(String search) {
        System.out.println(myKey);
        System.out.println(ratingsRequest);
        System.out.println(searchRequest);
        HttpURLConnection connection = null;
        String searchResponse = getUrlResponse(connection, searchRequest+myKey+search);
        System.out.println(searchResponse);
        JsonArray searchJson = parseSearch(searchResponse);
        ArrayList<ImdbMovie> movieArray = new ArrayList<ImdbMovie>();
        for (JsonElement movieJson : searchJson ) {
            JsonObject objectJson = movieJson.getAsJsonObject();
            System.out.println(objectJson);
            System.out.println(ratingsRequest+myKey+objectJson.get("id").getAsString());
            String ratingsJson = getUrlResponse(connection, ratingsRequest+myKey+objectJson.get("id").getAsString());
            System.out.println(ratingsJson);
            ImdbMovie imdbMovie = new ImdbMovie(objectJson, ratingsJson);
            movieArray.add(imdbMovie);
        }

        return movieArray;
    }

}
