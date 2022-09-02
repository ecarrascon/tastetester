package playerdata;

import java.util.ArrayList;

public class User {
    // In a future I want to make a User/EverythingRatings database, that's why I am not going to make a Abstract user and do inheritance

    private String name = "Set name";

    //Save all the movies data given in the movie mode
    private ArrayList<Movie> movies = new ArrayList<>();

    //Save all the series data given in the series mode
    private ArrayList<Series> series = new ArrayList<>();

    //Save all the anime data given in the anime mode
    private ArrayList<Anime> anime = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    //Add one movie/series/anime to the user
    public void addMovieToUser(Movie movie) {
        movies.add(movie);
    }

    public void addSeriesToUser(Series seriesGiven) {
        series.add(seriesGiven);
    }

    public void addAnimeToUser(Anime animeGiven) {
        anime.add(animeGiven);
    }

    public double ratingMovieAverage() {
        double allRatings = 0;
        for (Movie movie : movies) {
            allRatings += movie.getRating();
        }
        return allRatings / movies.size();
    }

    public double ratingSeriesAverage() {
        double allRatings = 0;
        for (Series seriesInList : series) {
            allRatings += seriesInList.getRating();
        }
        return allRatings / series.size();
    }

    public double ratingAnimeAverage() {
        double allRatings = 0;
        for (Anime animeInList : anime) {
            allRatings += animeInList.getRating();
        }
        return allRatings / anime.size();
    }

    public String getName() {
        return name;
    }

    //Get one saved movie
    public Movie getMovieUser(int index) {
        return movies.get(index);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Series> getSeries() {
        return series;
    }

    public ArrayList<Anime> getAnime() {
        return anime;
    }
}
