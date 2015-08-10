package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Regardt on 8/8/2015.
 */
public class YoutubeTrailer implements Parcelable{

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    String name;
    String size;
    String source;
    String type;

    protected YoutubeTrailer(Parcel in) {
        name = in.readString();
        size = in.readString();
        source = in.readString();
        type = in.readString();
    }

    public static final Creator<YoutubeTrailer> CREATOR = new Creator<YoutubeTrailer>() {
        @Override
        public YoutubeTrailer createFromParcel(Parcel in) {
            return new YoutubeTrailer(in);
        }

        @Override
        public YoutubeTrailer[] newArray(int size) {
            return new YoutubeTrailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(source);
        dest.writeString(type);
    }
}
