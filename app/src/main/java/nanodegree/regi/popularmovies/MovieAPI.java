package nanodegree.regi.popularmovies;

import java.util.List;

import nanodegree.regi.popularmovies.Model.Company;
import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.Result;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface MovieAPI {

     String API_KEY = "";



    @GET("/3/discover/movie?api_key=" + API_KEY)
    void getMovies(@Query("sort_by") String sort_by, Callback<Result> response);

    @GET("/3/movie/{id}?api_key=" + API_KEY)
    void getMovie(@Path("id")int id, Callback<Movie> response);

    @GET("/3/company/{id}?api_key=" + API_KEY)
    void getCompany(@Path("id")int id, Callback<Company> response);


}
