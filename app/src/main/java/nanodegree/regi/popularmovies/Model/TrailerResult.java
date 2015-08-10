package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Regardt on 8/8/2015.
 */
public class TrailerResult implements Parcelable {

    public List<YoutubeTrailer> getYoutube() {
        return youtube;
    }

    List<YoutubeTrailer> youtube;


    protected TrailerResult(Parcel in) {
        youtube = in.createTypedArrayList(YoutubeTrailer.CREATOR);
    }

    public static final Creator<TrailerResult> CREATOR = new Creator<TrailerResult>() {
        @Override
        public TrailerResult createFromParcel(Parcel in) {
            return new TrailerResult(in);
        }

        @Override
        public TrailerResult[] newArray(int size) {
            return new TrailerResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(youtube);
    }
}
