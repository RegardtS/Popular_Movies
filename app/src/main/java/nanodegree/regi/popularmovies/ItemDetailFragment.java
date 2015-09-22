package nanodegree.regi.popularmovies;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nanodegree.regi.popularmovies.Model.Movie;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ItemDetailFragment extends Fragment {

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


    Toolbar toolbar;

    boolean isSinglePane;
    boolean showShare = false;

    FloatingActionButton fab;

    SharedPreference prefs;

    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //only share if there is an available trailer
        if (showShare) {
            inflater.inflate(R.menu.menu_detail, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share: {
                String youtubeString = Constants.YOUTUBE_URL.getConstant() + currentMovie.getTrailers().getYoutube().get(0).getSource();
                String shareBody = getResources().getString(R.string.check_out_text) + currentMovie.getTitle() + " " + youtubeString;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, currentMovie.getTrailers().getYoutube().get(0).getName());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments().getBoolean("isSinglePane")) {
            isSinglePane = true;
        }

        initViews();

        (new Thread() {
            @Override
            public void run() {
                super.run();
                currentMovie = getArguments().getParcelable("movie");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        init();
                    }
                });
            }
        }).start();
    }

    private void init() {

        String imgURL = Constants.URL.getConstant() + Constants.BACKDROPSIZE.getConstant();

        if (currentMovie.getBackdrop_path() == null) {
            imgURL += currentMovie.getPoster_path();
        } else {
            imgURL += currentMovie.getBackdrop_path();
        }

        ImageView img;

        if (isSinglePane) {

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(currentMovie.getTitle());

            img = (ImageView) getView().findViewById(R.id.img_poster);
        } else {

            TextView TitleTablet = (TextView) getView().findViewById(R.id.TabletTitle);
            TitleTablet.setText(currentMovie.getTitle());
            img = (ImageView) getView().findViewById(R.id.TabletPoster);
        }
        Picasso.with(getActivity().getApplicationContext()).load(imgURL).into(img);

        prefs = new SharedPreference(getActivity().getApplicationContext());


        makeRequest();

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefs.isFavourite(currentMovie.getId())) {
                    prefs.deleteMovie(currentMovie.getId());
                } else {
                    prefs.addFavorite(currentMovie);
                }
                updateFab();
            }
        });
        updateFab();
    }


    private void initViews() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(true);
        progressDialog.show();


        Tagline = (TextView) getView().findViewById(R.id.Tagline);
        Overview = (TextView) getView().findViewById(R.id.Overview);
        Date = (TextView) getView().findViewById(R.id.Date);
        Genre = (TextView) getView().findViewById(R.id.Genre);
        Runtime = (TextView) getView().findViewById(R.id.Runtime);
        Language = (TextView) getView().findViewById(R.id.Language);
        Popularity = (TextView) getView().findViewById(R.id.Popularity);
        Budget = (TextView) getView().findViewById(R.id.Budget);
        Revenue = (TextView) getView().findViewById(R.id.Revenue);
        LLReleaseDate = (LinearLayout) getView().findViewById(R.id.LLReleaseDate);
        LLGenre = (LinearLayout) getView().findViewById(R.id.LLGenre);
        LLOverview = (LinearLayout) getView().findViewById(R.id.LLOverview);
        LLRuntime = (LinearLayout) getView().findViewById(R.id.LLRuntime);
        LLLanguage = (LinearLayout) getView().findViewById(R.id.LLLanguage);
        LLPopularity = (LinearLayout) getView().findViewById(R.id.LLPopularity);
        LLBudget = (LinearLayout) getView().findViewById(R.id.LLBudget);
        LLRevenue = (LinearLayout) getView().findViewById(R.id.LLRevenue);


        //disable on tablet
        if (isSinglePane) {
            toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateFab() {
        if (prefs.isFavourite(currentMovie.getId())) {
            fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_24dp));
        } else {
            fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_outline_24dp));
        }
    }

    private void makeRequest() {
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

    private void failMessage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(R.string.error_message);
        alertDialogBuilder.setTitle(R.string.error_title);
        alertDialogBuilder.setPositiveButton(R.string.generic_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                makeRequest();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.generic_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void gotoSite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void initializeValues() {


        Button homePage = (Button) getView().findViewById(R.id.btnHome);
        Button imdb = (Button) getView().findViewById(R.id.btnImdb);


        if (currentMovie.getHomepage().isEmpty()) {
            homePage.setVisibility(View.GONE);
        } else {
            homePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoSite(currentMovie.getHomepage());
                }
            });
        }

        if (currentMovie.getImdb_id().isEmpty()) {
            imdb.setVisibility(View.GONE);
        } else {
            imdb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoSite(Constants.IMDBURL.getConstant() + currentMovie.getImdb_id());
                }
            });
        }


        LinearLayout lt = (LinearLayout) getView().findViewById(R.id.LLReviews);
        lt.removeAllViews();


        if (currentMovie.getReviews().getResults().size() > 0) {
            for (int i = 0; i < currentMovie.getReviews().getResults().size(); i++) {
                LinearLayout ll = (LinearLayout) getLayoutInflater(getArguments()).inflate(R.layout.movie_review_item, null);

                TextView tv = (TextView) ll.findViewById(R.id.txt_review_title);
                TextView tv2 = (TextView) ll.findViewById(R.id.txt_review_content);

                tv.setText(currentMovie.getReviews().getResults().get(i).getAuthor());
                tv2.setText(currentMovie.getReviews().getResults().get(i).getContent());


                lt.addView(ll);
            }
        } else {
            getView().findViewById(R.id.LLReviewsContainer).setVisibility(View.GONE);
        }


        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.LLTrailers);
        linearLayout.removeAllViews();

        if (currentMovie.getTrailers().getYoutube().size() > 0) {

            showShare = true;
            getActivity().invalidateOptionsMenu();

            for (int i = 0; i < currentMovie.getTrailers().getYoutube().size(); i++) {

                LinearLayout ll = (LinearLayout) getLayoutInflater(getArguments()).inflate(R.layout.movie_trailer_item, null);
                ll.setTag(i);

                TextView tv = (TextView) ll.findViewById(R.id.txt_trailer_title);


                tv.setText(currentMovie.getTrailers().getYoutube().get(i).getName());


                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_URL.getConstant() + currentMovie.getTrailers().getYoutube().get((int) v.getTag()).getSource())));
                    }
                });

                linearLayout.addView(ll);
            }
        } else {
            getView().findViewById(R.id.LLTrailerContainer).setVisibility(View.GONE);
        }


        String tagline = currentMovie.getTagline();
        if (tagline.length() == 0) {
            Tagline.setVisibility(View.GONE);
        } else {
            Tagline.setText(tagline);
        }

        String overview = currentMovie.getOverview();
        if (overview.length() == 0) {
            LLOverview.setVisibility(View.GONE);
        } else {
            Overview.setText(overview);
        }

        String date = currentMovie.getRelease_date();
        if (date.length() == 0) {
            LLReleaseDate.setVisibility(View.GONE);
        } else {
            Date.setText(date);
        }


        int runtime = currentMovie.getRuntime();
        if (runtime == 0) {
            LLRuntime.setVisibility(View.GONE);
        } else {
            Runtime.setText(String.valueOf(runtime) + " min");
        }


        String language = currentMovie.getOriginal_language();
        if (language.length() == 0) {
            LLLanguage.setVisibility(View.GONE);
        } else {
            Language.setText(language);
        }


        float b = (float) Math.round(currentMovie.getPopularity());
        Popularity.setText(String.valueOf(b) + " %");

        long budget = currentMovie.getBudget();
        if (budget == 0) {
            LLBudget.setVisibility(View.GONE);
        } else {
            Budget.setText("$ " + String.valueOf(budget));
        }


        long revenue = currentMovie.getRevenue();
        if (revenue == 0) {
            LLRevenue.setVisibility(View.GONE);
        } else {
            Revenue.setText("$ " + revenue);
        }

        String genreSting = "";
        for (int i = 0; i < currentMovie.getGenres().size(); i++) {
            if (i != 0) {
                genreSting += " | ";
            }
            genreSting += currentMovie.getGenres().get(i).getName();
        }

        if (genreSting.length() == 0) {
            LLGenre.setVisibility(View.GONE);
        } else {
            Genre.setText(genreSting);
        }


    }

}
