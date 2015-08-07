package nanodegree.regi.popularmovies;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import nanodegree.regi.popularmovies.Model.Movie;

public class ItemListActivity extends AppCompatActivity implements ItemListFragment.Callbacks {

    private boolean mTwoPane;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.

        }

       fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //getFragmentManager().putFragment(outState,"fragment",fragment);
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState,fragment.getClass().getName(),fragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState !=null){
            fragment = getSupportFragmentManager().getFragment(savedInstanceState,fragment.getClass().getName());
        }
    }

    @Override
    public void OnItemSelected(Movie selectedMovie) {
        if(mTwoPane){
            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.MOVIE.getConstant(),selectedMovie);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }else{
            Intent mIntent = new Intent(getApplicationContext(), ItemDetailActivity.class);
            mIntent.putExtra(Constants.MOVIE.getConstant(),selectedMovie);
            startActivity(mIntent);
        }
    }
}































