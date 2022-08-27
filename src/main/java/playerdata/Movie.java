package playerdata;

public class Movie {

    //Movie attributes
    private String title;
    private String id;
    private Double rating;
    private String img;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public Movie(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public Movie(String title, String id, String img) {
        this.title = title;
        this.id = id;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
