package com.mymovieslist.service;

import com.mymovieslist.My.Movies.List.model.Movie;
import com.mymovieslist.My.Movies.List.repo.MovieRepo;
import com.mymovieslist.My.Movies.List.service.MovieService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ImdbService {

    public static String get_url_response(HttpURLConnection connection, String url){
        String result = new String();
        try {
            URL search_url = new URL(url);
            connection = (HttpURLConnection)search_url.openConnection();
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

    public static Movie[] getMovies(String search) {
        HttpURLConnection connection = null;
        final String myKey = "k_qa229e40";
        final String search_request = "https://imdb-api.com/en/API/SearchMovie/"+myKey+"/";
        final String ratings_request = "https://imdb-api.com/en/API/Ratings/"+myKey+"/";
        String search_response = get_url_response(connection, search_request+search);
        JSONObject search_object = new JSONObject(search_response);
        JSONArray search_results = search_object.getJSONArray("results");
        System.out.println("Search Results "+ search_results);
        Movie[] MovieArray = new Movie[search_results.length()];
        for (int i=0; i<MovieArray.length; i++) {
            JSONObject movieJSON = search_results.getJSONObject(i);
            String movieName = movieJSON.getString("title");
            String id = movieJSON.getString("id");
            String ratings_response = get_url_response(connection, ratings_request+id);
            JSONObject ratingsJSON = new JSONObject(ratings_response);
            String launchDate = "";
            String rating = "";
            String imageUrl = movieJSON.getString("image");
            try {
                launchDate = ratingsJSON.getString("year");
            } catch (JSONException e) {
                ;
            }
            try {
                rating = ratingsJSON.getString("imDb");;
            } catch (JSONException e) {
                ;
            }

            Movie LoopMovie = new Movie(id, movieName, launchDate, rating, imageUrl);
            System.out.println(LoopMovie);

            MovieArray[i] = LoopMovie;
        }
        System.out.println(MovieArray);
        return MovieArray;
    }

}
