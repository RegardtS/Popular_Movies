package nanodegree.regi.popularmovies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.Result;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.my_recycler_view)  RecyclerView mRecyclerView;
    @InjectView(R.id.toolbar)           Toolbar toolbar;


    private RecyclerView.Adapter mAdapter;
    private Context mContext;
    List<Movie> movieList = new ArrayList<>();
    MovieAPI api;
    IImageLoader someRandomLoader;

    Boolean isPopular = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        someRandomLoader = new ImageLoaderPicasso();

        setSupportActionBar(toolbar);

        api = RestAdapter.getInstance().getRestAdapter().create(MovieAPI.class);

        mContext = getApplicationContext();

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutmanager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutmanager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        requestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            Toast.makeText(mContext,R.string.toast_popular,Toast.LENGTH_LONG).show();
            isPopular = true;
        }else if(id == R.id.action_rating){
            Toast.makeText(mContext,R.string.toast_rating,Toast.LENGTH_LONG).show();
            isPopular = false;
        }
        requestData();
        return super.onOptionsItemSelected(item);
    }

    private void requestData(){
        String sorting = "";
        if(isPopular){
            sorting = Constants.POPULAR.getConstant();
            toolbar.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.action_popular));
        }else{
            sorting = Constants.RATING.getConstant();
            toolbar.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.action_rating));
        }

        api.getMovies(sorting, new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                movieList = result.getResults();
                mAdapter.notifyDataSetChanged();
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
                requestData();
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



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            final Movie tempMovie = movieList.get(position);
            viewHolder.txtViewTitle.setText(tempMovie.getTitle());
            String imgURL = Constants.URL.getConstant() + Constants.PICSIZE.getConstant()
                    + tempMovie.getPoster_path();

            someRandomLoader.LoadImage(mContext, imgURL, viewHolder);

            viewHolder.viewMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext,Detail.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(Constants.MOVIE.getConstant(), tempMovie);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
        }




        @Override
        public int getItemCount() {
            return movieList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtViewTitle;
            public ImageView imgViewIcon;
            public RelativeLayout viewMain;
            public View viewBackground;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.tv_title_movie);
                imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.img_poster_movie);
                viewMain = (RelativeLayout) itemLayoutView.findViewById(R.id.view_main);
                viewBackground = (View) itemLayoutView.findViewById(R.id.view_background);
            }
        }
    }



}































