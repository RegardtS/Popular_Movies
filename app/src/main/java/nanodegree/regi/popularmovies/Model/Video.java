package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RegardtS on 2015-08-07.
 */
public class Video implements Parcelable {
    String id;
    String iso_639_1;
    String key;
    String name;
    String site;
    int size;
    String type;

//    {
//        "id": 211672,
//            "results": [
//        {
//            "id": "54badb64c3a3684046001c73",
//                "iso_639_1": "en",
//                "key": "eisKxhjBnZ0",
//                "name": "Minions Official Trailer #1 (2015) - Despicable Me Prequel HD",
//                "site": "YouTube",
//                "size": 720,
//                "type": "Trailer"
//        },
//        {
//            "id": "54e01475c3a36855c7002767",
//                "iso_639_1": "en",
//                "key": "Myv_Z8CReDU",
//                "name": "Trailer 3",
//                "site": "YouTube",
//                "size": 720,
//                "type": "Trailer"
//        }
//        ]
//    }
//    https://www.youtube.com/watch?v=Myv_Z8CReDU [add key]




    protected Video(Parcel in) {
        id = in.readString();
        iso_639_1 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readInt();
        type = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso_639_1);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }
}
