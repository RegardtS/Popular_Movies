package nanodegree.regi.popularmovies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nanodegree.regi.popularmovies.Model.Company;
import nanodegree.regi.popularmovies.Model.Genre;
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
    //@InjectView(R.id.Companies)     TextView Companies;


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

        String imgURL = Constants.URL.getConstant() + Constants.BACKDROPSIZE.getConstant();

        if (currentMovie.getBackdrop_path() == null) {
            imgURL += currentMovie.getPoster_path();
        } else {
            imgURL += currentMovie.getBackdrop_path();
        }

        ImageView img = (ImageView) findViewById(R.id.img_poster);
        Picasso.with(getApplicationContext()).load(imgURL).into(img);

        makeRequest();
    }

    private void makeRequest(){
        MovieAPI api = RestAdapter.getInstance().getRestAdapter().create(MovieAPI.class);
        api.getMovie(currentMovie.getId(), new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                currentMovie = movie;
                initializeValues();
            }

            @Override
            public void failure(RetrofitError error) {
                failMessage();
            }
        });

    }

    private void failMessage(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.error_message);
        alertDialogBuilder.setTitle(R.string.error_title);
        alertDialogBuilder.setPositiveButton(R.string.generic_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                makeRequest();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.generic_no,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void homePageTapped(View v){
        gotoSite(currentMovie.getHomepage());
    }
    public void imdbTapped(View v){
        gotoSite("http://www.imdb.com/title/" + currentMovie.getImdb_id());
    }




    private void gotoSite(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    private void initializeValues() {
        Tagline.setText(currentMovie.getTagline());
        Overview.setText(currentMovie.getOverview());
        Date.setText(currentMovie.getRelease_date());
        Runtime.setText(currentMovie.getRuntime()+"");
        Language.setText(currentMovie.getOriginal_language());
        Popularity.setText(currentMovie.getPopularity()+"");
        Budget.setText(currentMovie.getBudget()+"");
        Revenue.setText(currentMovie.getRevenue()+"");

        String genreSting = "";

        for (int i = 0; i < currentMovie.getGenres().size(); i++) {

            if(i!=0){
                genreSting+=" | ";
            }

            genreSting += currentMovie.getGenres().get(i).getName();
        }

        Genre.setText(genreSting);



//        Companies.setText(currentMovie.getTagline());
//        Genre.setText(currentMovie.getTagline());

    }

}
