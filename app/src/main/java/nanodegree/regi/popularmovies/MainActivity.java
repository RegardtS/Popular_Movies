package nanodegree.regi.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.Result;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;



//http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR KEY]
//http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=[YOUR KEY]

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    List<Movie> movieList = new ArrayList<>();
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        MovieAPI api = RetrofitCalls.getInstance().getRestAdapter().create(MovieAPI.class);

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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final Movie tempMovie = movieList.get(position);
            viewHolder.txtViewTitle.setText(tempMovie.getTitle());
            String imgURL = "http://image.tmdb.org/t/p/" + "w342" + tempMovie.getPoster_path();
            Picasso.with(getApplicationContext()).load(imgURL).into(viewHolder.imgViewIcon);
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


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtViewTitle;
            public ImageView imgViewIcon;
            public RelativeLayout viewMain;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.tv_title_movie);
                imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.img_poster_movie);
                viewMain = (RelativeLayout) itemLayoutView.findViewById(R.id.view_main);
            }
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }

}






























