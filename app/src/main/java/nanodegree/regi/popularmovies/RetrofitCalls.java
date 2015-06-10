package nanodegree.regi.popularmovies;


import retrofit.RestAdapter;

public class RetrofitCalls {

    private static RetrofitCalls instance = new RetrofitCalls();

    private static final String ENDPOINT = "http://api.themoviedb.org/";

    private RestAdapter restAdapter;

    private RetrofitCalls() {
        restAdapter = new RestAdapter.Builder()
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();
    }

    public static RetrofitCalls getInstance(){
        return instance;
    }

    public RestAdapter getRestAdapter(){
        return restAdapter;
    }
}
