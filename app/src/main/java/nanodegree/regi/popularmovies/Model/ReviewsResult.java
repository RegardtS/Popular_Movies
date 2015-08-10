package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Regardt on 8/8/2015.
 */
public class ReviewsResult implements Parcelable{

    public List<Review> getResults() {
        return results;
    }

    List<Review> results;

    protected ReviewsResult(Parcel in) {
        results = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<ReviewsResult> CREATOR = new Creator<ReviewsResult>() {
        @Override
        public ReviewsResult createFromParcel(Parcel in) {
            return new ReviewsResult(in);
        }

        @Override
        public ReviewsResult[] newArray(int size) {
            return new ReviewsResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
}
