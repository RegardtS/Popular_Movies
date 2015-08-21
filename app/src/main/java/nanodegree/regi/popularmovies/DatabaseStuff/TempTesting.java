package nanodegree.regi.popularmovies.DatabaseStuff;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nanodegree.regi.popularmovies.DatabaseStuff.Comment;
import nanodegree.regi.popularmovies.DatabaseStuff.CommentsDataSource;
import nanodegree.regi.popularmovies.R;

public class TempTesting extends ListActivity {

    private CommentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_testing);

        dataSource = new CommentsDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Comment> values = dataSource.getAllComments();

        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);

    }

    public void onClick(View view){

        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()){
            case R.id.add:
                String[] comments = new String[] {"Cool","Very nice","Hate it"};
                int nextInt = new Random().nextInt(3);
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if(getListAdapter().getCount() > 0){
                    comment = (Comment) getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();

    }



}
