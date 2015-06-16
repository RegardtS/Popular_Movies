package nanodegree.regi.popularmovies.Model;

/**
 * Created by Regardt on 2015-06-10.
 */
public class Company {
    private String name;
    private int id;

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
}
