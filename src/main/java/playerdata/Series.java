package playerdata;

public class Series {

    //Movie attributes given by IMDbApi
    private String title;
    private String id;
    private Double rating;
    private String image;
    private Double imDb;
    private String description;

    public Series(){
    }

    public String getTitle() {
        return title;
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

}
