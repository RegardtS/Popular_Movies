package nanodegree.regi.popularmovies.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Regardt on 2015-06-06.
 */
public class Movie implements Serializable {

    private int id;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private Double popularity;
    private String title;
    private String backdrop_path;

    private long budget;
    private String homepage;
    private String imdb_id;
    private String original_language;
    private long revenue;
    private int runtime;
    private String tagline;
    private Boolean video;

    private List<Genre> genres;
    private List<Company> production_companies;
    private List<Country> production_countries;

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Company> getProduction_companies() {
        return production_companies;
    }

    public List<Country> getProduction_countries() {
        return production_countries;
    }

    public long getBudget() {
        return budget;
    }

    public String getHomepage() {return homepage;}

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Double getPopularity() {
        return popularity;
    }

}
