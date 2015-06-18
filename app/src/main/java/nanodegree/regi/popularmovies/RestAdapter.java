package nanodegree.regi.popularmovies;


public class RestAdapter {

    private static RestAdapter instance = new RestAdapter();

    private static final String ENDPOINT = "http://api.themoviedb.org/";

    private retrofit.RestAdapter restAdapter;

    private RestAdapter() {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();
    }

    public static RestAdapter getInstance(){
        return instance;
    }

    public retrofit.RestAdapter getRestAdapter(){
        return restAdapter;
    }
}
