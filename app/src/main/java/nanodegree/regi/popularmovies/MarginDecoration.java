package nanodegree.regi.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by RegardtS on 2015-08-04.
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(view.getTag() != null && view.getTag().toString().equals("tagged")){
            return;
        }else{
            outRect.set(margin, margin, margin, margin);
            view.setTag("tagged");
        }




    }
}