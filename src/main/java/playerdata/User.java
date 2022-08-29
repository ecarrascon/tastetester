package playerdata;

import java.util.ArrayList;

public class User {
    private String name = "Set name";

    //Save all the movies data given in the movie mode
    private ArrayList<Movie> movies = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    //Add one movie to the user
    public void addMovieToUser(Movie movie) {
        movies.add(movie);
    }

    public double ratingAverage() {
        double allRatings = 0;
        for (Movie movie : movies) {
            allRatings += movie.getRating();
        }
        return allRatings / movies.size();
    }

    public String getName() {
        return name;
    }

    //Get one saved movie
    public Movie getMovieUser(int index) {
        return movies.get(index);
    }

}
