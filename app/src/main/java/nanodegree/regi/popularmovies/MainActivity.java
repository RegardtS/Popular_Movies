package nanodegree.regi.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.Result;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    List<Movie> movieList = new ArrayList<>();
    private Context mContext;
    MovieAPI api;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        api = RetrofitCalls.getInstance().getRestAdapter().create(MovieAPI.class);

        mContext = getApplicationContext();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutmanager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutmanager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        requestData(true);
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
            Toast.makeText(mContext,"Changing to popular",Toast.LENGTH_LONG).show();
            requestData(true);
        }else if(id == R.id.action_rating){
            Toast.makeText(mContext,"Changing to rating",Toast.LENGTH_LONG).show();
            requestData(false);
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestData(Boolean isPopular){
        String sorting = isPopular ? "popularity.desc" : "vote_average.desc";

        api.getMovies(sorting, new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                movieList = result.getResults();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
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
            String imgURL = "http://image.tmdb.org/t/p/" + "w342" + tempMovie.getPoster_path();

            /*
            Jake Wharton helped with this
            http://jakewharton.com/coercing-picasso-to-play-with-palette/
            */
            Picasso.with(mContext)
                    .load(imgURL)
                    .transform(PaletteTransformation.instance())
                    .into(viewHolder.imgViewIcon, new com.squareup.picasso.Callback.EmptyCallback() {
                        @Override public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) viewHolder.imgViewIcon.getDrawable()).getBitmap();
                            Palette palette = PaletteTransformation.getPalette(bitmap);
                            viewHolder.viewBackground.setBackgroundColor(palette.getDarkVibrantColor(R.color.primary));
                        }
                    });

            viewHolder.viewMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"test at position " + position, Toast.LENGTH_LONG).show();
                    Intent x = new Intent(mContext,Detail.class);

                    Bundle b = new Bundle();
                    b.putSerializable("test",tempMovie);
                    x.putExtras(b);

                    startActivity(x);
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































