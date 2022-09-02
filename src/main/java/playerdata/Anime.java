package playerdata;

public class Anime {

    //Movie attributes given by IMDbApi
    private String title;
    private String id;
    private Double rating;
    private String image;
    private Double imDb;
    private String description;

    public Anime(){
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
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


    public String getDescription() {
        return description;
    }


}
