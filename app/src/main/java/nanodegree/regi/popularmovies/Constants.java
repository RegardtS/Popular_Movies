package nanodegree.regi.popularmovies;

/**
 * Created by RegardtS on 2015-06-18.
 */
public enum Constants {

    MOVIE("movie"),
    URL("http://image.tmdb.org/t/p/"),
    PICSIZE("w342"),
    BACKDROPSIZE("w780"),
    POPULAR("popularity.desc"),
    RATING("vote_average.desc"),
    IMDBURL("http://www.imdb.com/title/");


    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}