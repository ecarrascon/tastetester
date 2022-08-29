package playerdata;

public class Movie {

    //Movie attributes
    private String title;
    private String id;
    private Double rating;
    private String image;
    private Double imDb;
    private String description;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public Movie(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public Movie(String title, String id, String image) {
        this.title = title;
        this.id = id;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getImDb() {
        return imDb;
    }

    public void setImDb(Double imDb) {
        this.imDb = imDb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", rating=" + rating +
                ", img='" + image + '\'' +
                '}';
    }
}
