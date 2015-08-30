package nanodegree.regi.popularmovies;

import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.MovieResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface MovieAPI {

    String API_KEY = "7b71926f5bf6346606881805a612d87c";

    @GET("/3/discover/movie?api_key=" + API_KEY)
    void getMovies(@Query("sort_by") String sort_by, Callback<MovieResult> response);

    @GET("/3/movie/{id}?api_key=" + API_KEY+"&append_to_response=trailers,reviews")
    void getMovie(@Path("id")int id, Callback<Movie> response);


}
