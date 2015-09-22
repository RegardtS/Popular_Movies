package nanodegree.regi.popularmovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nanodegree.regi.popularmovies.Model.Movie;
import nanodegree.regi.popularmovies.Model.MovieResult;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RegardtS on 2015-07-27.
 */
public class ItemListFragment extends Fragment {

    private Callbacks mCallbacks = sDummyCallbacks;

    private RecyclerView.Adapter mAdapter;
    private Context mContext;
    ArrayList<Movie> movieList = new ArrayList<>();
    MovieAPI api;
    IImageLoader someRandomLoader;

    int sortingStyle = 0;

    RecyclerView mRecyclerView;
    MarginDecoration decorator;

    SharedPreferences prefs;

    LinearLayoutManager mLayoutManager;

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void OnItemSelected(Movie selectedMovie) {
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_list_view, container, false);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(movieList.size() > 0){
            outState.putParcelableArrayList(Constants.MOVIE_KEYLIST.getConstant(), movieList);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.MOVIE_KEYLIST.getConstant())) {
            movieList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_KEYLIST.getConstant());
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        someRandomLoader = new ImageLoaderPicasso();
        api = RestAdapter.getInstance().getRestAdapter().create(MovieAPI.class);
        mContext = getActivity().getApplicationContext();


        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        decorator = new MarginDecoration(mContext);

        mRecyclerView.addItemDecoration(decorator);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter();



        mRecyclerView.setAdapter(mAdapter);

        prefs = mContext.getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        if(prefs.contains(Constants.SORTING_PREF.getConstant())){
            sortingStyle = prefs.getInt(Constants.SORTING_PREF.getConstant(),0);
        }

        if (movieList.isEmpty()) {
            requestData();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            Toast.makeText(mContext, R.string.toast_popular, Toast.LENGTH_LONG).show();
            sortingStyle = 0;
            prefs.edit().putInt(Constants.SORTING_PREF.getConstant(), 0).apply();
            requestData();
        } else if (id == R.id.action_rating) {
            Toast.makeText(mContext, R.string.toast_rating, Toast.LENGTH_LONG).show();
            sortingStyle = 1;
            prefs.edit().putInt(Constants.SORTING_PREF.getConstant(),1).apply();
            requestData();
        }else if(id == R.id.action_favourite){
            SharedPreference x = new SharedPreference(getActivity().getApplicationContext());
            movieList = x.getFavourites();
            if(movieList == null){
                movieList = new ArrayList<>();
            }
            mAdapter.notifyDataSetChanged();
        }

        return super.onContextItemSelected(item);
    }

    private void requestData() {
        String sorting = "";
        if (sortingStyle == 0) {
            sorting = Constants.POPULAR.getConstant();
        } else {
            sorting = Constants.RATING.getConstant();
        }

        api.getMovies(sorting, new Callback<MovieResult>() {
            @Override
            public void success(MovieResult movieResult, Response response) {
                movieList = movieResult.getResults();
                mAdapter.notifyDataSetChanged();
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
                requestData();
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


    public interface Callbacks {
        void OnItemSelected(Movie selectedMovie);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.itemView.setTag("null");
        }

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
            String imgURL = Constants.URL.getConstant() + Constants.PICSIZE.getConstant() + tempMovie.getPoster_path();

            someRandomLoader.LoadImage(mContext, imgURL, viewHolder);

            viewHolder.viewMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.OnItemSelected(tempMovie);
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
                viewBackground = itemLayoutView.findViewById(R.id.view_background);
            }
        }
    }

}
