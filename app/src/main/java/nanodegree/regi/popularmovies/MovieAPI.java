package nanodegree.regi.popularmovies;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Regardt on 2015-06-06.
 */
public interface MovieAPI {

    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR KEY]")
    public void getFeed(Callback<Results> response);


}
