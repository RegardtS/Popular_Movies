package nanodegree.regi.popularmovies;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nanodegree.regi.popularmovies.Model.Movie;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Detail extends AppCompatActivity {

    private Movie currentMovie;

    @InjectView(R.id.Tagline)       TextView Tagline;
    @InjectView(R.id.Overview)      TextView Overview;
    @InjectView(R.id.Date)          TextView Date;
    @InjectView(R.id.Genre)         TextView Genre;
    @InjectView(R.id.Runtime)       TextView Runtime;
    @InjectView(R.id.Language)      TextView Language;
    @InjectView(R.id.Popularity)    TextView Popularity;
    @InjectView(R.id.Budget)        TextView Budget;
    @InjectView(R.id.Revenue)       TextView Revenue;
    @InjectView(R.id.Companies)     TextView Companies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        currentMovie = (Movie) getIntent().getSerializableExtra(Constants.MOVIE.getConstant());


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(currentMovie.getTitle());

        String imgURL = Constants.URL.getConstant() + Constants.PICSIZE.getConstant();

        if (currentMovie.getBackdrop_path() == null) {
            imgURL += currentMovie.getPoster_path();
        } else {
            imgURL += currentMovie.getBackdrop_path();
        }

        ImageView img = (ImageView) findViewById(R.id.img_poster);
        Picasso.with(getApplicationContext()).load(imgURL).into(img);


        MovieAPI api = RestAdapter.getInstance().getRestAdapter().create(MovieAPI.class);
        api.getMovie(currentMovie.getId(), new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                currentMovie = movie;
                setupStuff();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    private void setupStuff() {
        Tagline.setText(currentMovie.getTagline());
        Overview.setText(currentMovie.getOverview());
        Date.setText(currentMovie.getRelease_date());
        Runtime.setText(currentMovie.getRuntime()+"");
        Language.setText(currentMovie.getOriginal_language());
        Popularity.setText(currentMovie.getPopularity()+"");
        Budget.setText(currentMovie.getBudget()+"");
        Revenue.setText(currentMovie.getRevenue()+"");
//        Companies.setText(currentMovie.getTagline());
//        Genre.setText(currentMovie.getTagline());

    }

}
