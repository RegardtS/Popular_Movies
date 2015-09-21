package nanodegree.regi.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nanodegree.regi.popularmovies.Model.Movie;

/**
 * Created by regardtschindler on 15/09/21.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "Movie_APP";
    public static final String FAVORITES = "Movie_Favorite";
    Gson gson = new Gson();
    Context context;

    public SharedPreference(Context context) {
        super();
        this.context = context;
    }

    public void addFavorite(Movie Movie) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String serailizedMovie = gson.toJson(Movie);
        editor.putString(String.valueOf(Movie.getId()), serailizedMovie);
        editor.commit();

    }


    public void deleteMovie(int id){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().remove(String.valueOf(id)).apply();
    }

    public ArrayList<Movie> getFavourites(){

        ArrayList<Movie> moveies = new ArrayList<>();
        SharedPreferences settings =  context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        Map<String, ?> internalRepr = settings.getAll();
        for (String string :(Collection<String>) internalRepr.values()){
            Movie movie = gson.fromJson(string, Movie.class);
            moveies.add(movie);
        }

        return moveies;
    }



    public boolean isFavourite(int id) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.contains(String.valueOf(id));

    }
}

