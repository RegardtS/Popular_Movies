package nanodegree.regi.popularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Picasso;


public class ImageLoaderPicasso implements IImageLoader {
    @Override
    public void LoadImage(Object... params) {
        Context mContext = (Context) params[0];
        String imgURL = (String) params[1];
        final ItemListFragment.MyAdapter.ViewHolder viewHolder = (ItemListFragment.MyAdapter.ViewHolder) params[2];

//        Jake Wharton helped with this
//        http://jakewharton.com/coercing-picasso-to-play-with-palette/

        Picasso.with(mContext)
                .load(imgURL)
                .transform(PaletteTransformation.instance())
                .error(R.drawable.no_available)
                .placeholder(R.drawable.loading)
                .into(viewHolder.imgViewIcon, new com.squareup.picasso.Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) viewHolder.imgViewIcon.getDrawable()).getBitmap();
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        viewHolder.viewBackground.setBackgroundColor(palette.getDarkVibrantColor(R.color.primary));
                    }
                });
    }
}
