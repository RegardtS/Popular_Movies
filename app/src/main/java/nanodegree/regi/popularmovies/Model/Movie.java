package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Regardt on 2015-06-06.
 */
public class Movie implements Parcelable {

    int id;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    Double popularity;
    String title;
    String backdrop_path;

    long budget;
    String homepage;
    String imdb_id;
    String original_language;
    long revenue;
    int runtime;
    String tagline;
    Boolean video;

    List<Genre> genres;
    List<Company> production_companies;
    List<Country> production_countries;

    TrailerResult trailers;

    public ReviewsResult getReviews() {
        return reviews;
    }

    ReviewsResult reviews;




    Movie(Parcel in) {
        id = in.readInt();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        budget = in.readLong();
        homepage = in.readString();
        imdb_id = in.readString();
        original_language = in.readString();
        revenue = in.readLong();
        runtime = in.readInt();
        tagline = in.readString();

        popularity = in.readDouble();
        video = in.readByte() !=0 ;
        genres = new ArrayList<>();
        in.readTypedList(genres, Genre.CREATOR);

        production_companies = new ArrayList<>();
        in.readTypedList(production_companies, Company.CREATOR);

        production_countries = new ArrayList<>();
        in.readTypedList(production_countries, Country.CREATOR);

        trailers = in.readParcelable(TrailerResult.class.getClassLoader());
        reviews = in.readParcelable(ReviewsResult.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeLong(budget);
        dest.writeString(homepage);
        dest.writeString(imdb_id);
        dest.writeString(original_language);
        dest.writeLong(revenue);
        dest.writeInt(runtime);
        dest.writeString(tagline);

        dest.writeDouble(popularity);

        dest.writeTypedList(genres);
        dest.writeTypedList(production_companies);
        dest.writeTypedList(production_countries);
        dest.writeParcelable(trailers, flags);
        dest.writeParcelable(reviews, flags);
    }

    public TrailerResult getTrailers() {return trailers;}

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
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

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public long getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

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

    public Boolean getVideo() {
        return video;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Company> getProduction_companies() {
        return production_companies;
    }

    public List<Country> getProduction_countries() {
        return production_countries;
    }

}
