package nanodegree.regi.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Regardt on 2015-06-10.
 */
public class Company implements Parcelable {
    private String name;
    private int id;

    protected Company(Parcel in) {
        name = in.readString();
        id = in.readInt();
        logo_path = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    private String logo_path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(logo_path);
    }
}
