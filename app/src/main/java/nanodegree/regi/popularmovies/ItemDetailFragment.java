package nanodegree.regi.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nanodegree.regi.popularmovies.Model.Movie;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Movie currentMovie;

    TextView Tagline;
    TextView Overview;
    TextView Date;
    TextView Genre;
    TextView Runtime;
    TextView Language;
    TextView Popularity;
    TextView Budget;
    TextView Revenue;

    LinearLayout LLReleaseDate;
    LinearLayout LLGenre;
    LinearLayout LLOverview;
    LinearLayout LLRuntime;
    LinearLayout LLLanguage;
    LinearLayout LLPopularity;
    LinearLayout LLBudget;
    LinearLayout LLRevenue;

    Context mContext;

    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Tagline= (TextView) getView().findViewById(R.id.Tagline);
        Overview= (TextView) getView().findViewById(R.id.Overview);
        Date= (TextView) getView().findViewById(R.id.Date);
        Genre= (TextView) getView().findViewById(R.id.Genre);
        Runtime= (TextView) getView().findViewById(R.id.Runtime);
        Language= (TextView) getView().findViewById(R.id.Language);
        Popularity= (TextView) getView().findViewById(R.id.Popularity);
        Budget= (TextView) getView().findViewById(R.id.Budget);
        Revenue= (TextView) getView().findViewById(R.id.Revenue);
        LLReleaseDate= (LinearLayout) getView().findViewById(R.id.LLReleaseDate);
        LLGenre= (LinearLayout) getView().findViewById(R.id.LLGenre);
        LLOverview= (LinearLayout) getView().findViewById(R.id.LLOverview);
        LLRuntime= (LinearLayout) getView().findViewById(R.id.LLRuntime);
        LLLanguage= (LinearLayout) getView().findViewById(R.id.LLLanguage);
        LLPopularity= (LinearLayout) getView().findViewById(R.id.LLPopularity);
        LLBudget= (LinearLayout) getView().findViewById(R.id.LLBudget);
        LLRevenue= (LinearLayout) getView().findViewById(R.id.LLRevenue);

        toolbar = (Toolbar)  getView().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentMovie = getActivity().getIntent().getParcelableExtra(Constants.MOVIE.getConstant());




        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(currentMovie.getTitle());

        String imgURL = Constants.URL.getConstant() + Constants.BACKDROPSIZE.getConstant();

        if (currentMovie.getBackdrop_path() == null) {
            imgURL += currentMovie.getPoster_path();
        } else {
            imgURL += currentMovie.getBackdrop_path();
        }

        ImageView img = (ImageView) getView().findViewById(R.id.img_poster);
        Picasso.with(getActivity().getApplicationContext()).load(imgURL).into(img);

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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
//                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void homePageTapped(View v){
        gotoSite(currentMovie.getHomepage());
    }

    public void imdbTapped(View v) {
        gotoSite(Constants.IMDBURL.getConstant() + currentMovie.getImdb_id());
    }

    private void gotoSite(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    private void initializeValues() {
        String tagline = currentMovie.getTagline();
        if(tagline.length() == 0){
            Tagline.setVisibility(View.GONE);
        }else{
            Tagline.setText(tagline);
        }

        String overview = currentMovie.getOverview();
        if(overview.length() == 0){
            LLOverview.setVisibility(View.GONE);
        }else{
            Overview.setText(overview);
        }

        String date = currentMovie.getRelease_date();
        if(date.length() == 0){
            LLReleaseDate.setVisibility(View.GONE);
        }else{
            Date.setText(date);
        }


        int runtime = currentMovie.getRuntime();
        if(runtime == 0){
            LLRuntime.setVisibility(View.GONE);
        }else{
            Runtime.setText(String.valueOf(runtime) + " min");
        }



        String language = currentMovie.getOriginal_language();
        if(language.length() == 0){
            LLLanguage.setVisibility(View.GONE);
        }else{
            Language.setText(language);
        }


        float b = (float)Math.round(currentMovie.getPopularity());
        Popularity.setText(String.valueOf(b) + " %");

        long budget = currentMovie.getBudget();
        if(budget == 0){
            LLBudget.setVisibility(View.GONE);
        }else{
            Budget.setText("$ " + String.valueOf(budget));
        }


        long revenue = currentMovie.getRevenue();
        if(revenue == 0){
            LLRevenue.setVisibility(View.GONE);
        }else{
            Revenue.setText("$ " + revenue);
        }

        String genreSting = "";
        for (int i = 0; i < currentMovie.getGenres().size(); i++) {
            if(i!=0){
                genreSting+=" | ";
            }
            genreSting += currentMovie.getGenres().get(i).getName();
        }

        if(genreSting.length() == 0){
            LLGenre.setVisibility(View.GONE);
        }else{
            Genre.setText(genreSting);
        }


    }

}
