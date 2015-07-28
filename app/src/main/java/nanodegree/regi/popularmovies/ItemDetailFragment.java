package nanodegree.regi.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

//    private Movie currentMovie;
//
//    @InjectView(R.id.Tagline)       TextView Tagline;
//    @InjectView(R.id.Overview)      TextView Overview;
//    @InjectView(R.id.Date)          TextView Date;
//    @InjectView(R.id.Genre)         TextView Genre;
//    @InjectView(R.id.Runtime)       TextView Runtime;
//    @InjectView(R.id.Language)      TextView Language;
//    @InjectView(R.id.Popularity)    TextView Popularity;
//    @InjectView(R.id.Budget)        TextView Budget;
//    @InjectView(R.id.Revenue)       TextView Revenue;
//
//    @InjectView(R.id.LLReleaseDate) LinearLayout LLReleaseDate;
//    @InjectView(R.id.LLGenre)       LinearLayout LLGenre;
//    @InjectView(R.id.LLOverview)    LinearLayout LLOverview;
//    @InjectView(R.id.LLRuntime)     LinearLayout LLRuntime;
//    @InjectView(R.id.LLLanguage)    LinearLayout LLLanguage;
//    @InjectView(R.id.LLPopularity)  LinearLayout LLPopularity;
//    @InjectView(R.id.LLBudget)      LinearLayout LLBudget;
//    @InjectView(R.id.LLRevenue)     LinearLayout LLRevenue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
//        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);

//        ButterKnife.inject(this);
//
//        currentMovie = (Movie) getIntent().getSerializableExtra(Constants.MOVIE.getConstant());
//
//
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(currentMovie.getTitle());
//
//        String imgURL = Constants.URL.getConstant() + Constants.BACKDROPSIZE.getConstant();
//
//        if (currentMovie.getBackdrop_path() == null) {
//            imgURL += currentMovie.getPoster_path();
//        } else {
//            imgURL += currentMovie.getBackdrop_path();
//        }
//
//        ImageView img = (ImageView) findViewById(R.id.img_poster);
//        Picasso.with(getApplicationContext()).load(imgURL).into(img);
//
//        makeRequest();
    }

//    private void makeRequest(){
//        MovieAPI api = RestAdapter.getInstance().getRestAdapter().create(MovieAPI.class);
//        api.getMovie(currentMovie.getId(), new Callback<Movie>() {
//            @Override
//            public void success(Movie movie, Response response) {
//                currentMovie = movie;
//                initializeValues();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                failMessage();
//            }
//        });
//
//    }
//
//    private void failMessage(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage(R.string.error_message);
//        alertDialogBuilder.setTitle(R.string.error_title);
//        alertDialogBuilder.setPositiveButton(R.string.generic_yes, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                makeRequest();
//            }
//        });
//
//        alertDialogBuilder.setNegativeButton(R.string.generic_no,new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
//
//    public void homePageTapped(View v){
//        gotoSite(currentMovie.getHomepage());
//    }
//
//    public void imdbTapped(View v){
//        gotoSite(Constants.IMDBURL.getConstant() + currentMovie.getImdb_id());
//    }
//
//    private void gotoSite(String url){
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//    }
//
//
//    private void initializeValues() {
//        String tagline = currentMovie.getTagline();
//        if(tagline.length() == 0){
//            Tagline.setVisibility(View.GONE);
//        }else{
//            Tagline.setText(tagline);
//        }
//
//        String overview = currentMovie.getOverview();
//        if(overview.length() == 0){
//            LLOverview.setVisibility(View.GONE);
//        }else{
//            Overview.setText(overview);
//        }
//
//        String date = currentMovie.getRelease_date();
//        if(date.length() == 0){
//            LLReleaseDate.setVisibility(View.GONE);
//        }else{
//            Date.setText(date);
//        }
//
//
//        int runtime = currentMovie.getRuntime();
//        if(runtime == 0){
//            LLRuntime.setVisibility(View.GONE);
//        }else{
//            Runtime.setText(String.valueOf(runtime) + " min");
//        }
//
//
//
//        String language = currentMovie.getOriginal_language();
//        if(language.length() == 0){
//            LLLanguage.setVisibility(View.GONE);
//        }else{
//            Language.setText(language);
//        }
//
//
//        float b = (float)Math.round(currentMovie.getPopularity());
//        Popularity.setText(String.valueOf(b) + " %");
//
//        long budget = currentMovie.getBudget();
//        if(budget == 0){
//            LLBudget.setVisibility(View.GONE);
//        }else{
//            Budget.setText("$ " + String.valueOf(budget));
//        }
//
//
//        long revenue = currentMovie.getRevenue();
//        if(revenue == 0){
//            LLRevenue.setVisibility(View.GONE);
//        }else{
//            Revenue.setText("$ " + revenue);
//        }
//
//        String genreSting = "";
//        for (int i = 0; i < currentMovie.getGenres().size(); i++) {
//            if(i!=0){
//                genreSting+=" | ";
//            }
//            genreSting += currentMovie.getGenres().get(i).getName();
//        }
//
//        if(genreSting.length() == 0){
//            LLGenre.setVisibility(View.GONE);
//        }else{
//            Genre.setText(genreSting);
//        }
//
//
//    }

}
