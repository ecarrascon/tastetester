package gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.utilities.*;
import playerdata.Movie;
import playerdata.SetUpUser;
import playerdata.User;

import movies.ImdbMovies;
import net.miginfocom.swing.MigLayout;
import settings.ApiKeys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

public class MoviesTester extends JFrame {
    private JList choooseMoviesUOne;
    private DefaultListModel listModelUOne;
    private JList choooseMoviesUTwo;
    private DefaultListModel listModelUTwo;
    private JLabel infoAndWinner;
    private JLabel resultNumberOne;
    private JLabel resultNumberTwo;
    private JTextField searchMovieUserOne;
    private JTextField searchMovieUserTwo;
    private final ImdbMovies imdbGetMovies;
    private User userNumberOne;
    private User userNumberTwo;
    private Gson gson;
    private List<Movie> movieList;
    private boolean isUserOne;
    private Menu menu;
    private JPanel panelMovie;
    private Boolean moviesMenuVisible = true;
    private Boolean menuVisible = false;

    public MoviesTester(Menu menu) {
        this.menu = menu;

        enterKeyAndUsers();

        setBackground(Color.WHITE);
        gson = new Gson();
        setTitle("MoviesTester");


        //The Api of Imdb
        imdbGetMovies = new ImdbMovies();

        //Window without borders
        setUndecorated(true);

        //The window can be resized and moved
        WindowSettings.prepareWindow(this);

        //Window's ImgIco
        URL imgLogo = getClass().getResource("/logo.png");
        setIconImage(new ImageIcon(imgLogo).getImage());

        setSize(670, 300);
        setLocationRelativeTo(null);

        //Add all the components
        add(componentsMovies());


    }

    //Panel of the components
    private JPanel componentsMovies() {
        panelMovie = new JPanel();
        panelMovie.setLayout(new MigLayout());
        panelMovie.setBackground(Color.WHITE);
        //Exit Button
        JButton moviesButtonExit = new JButton();
        moviesButtonExit.addActionListener(new CloseListener());
        ButtonSettings.prepareButton(moviesButtonExit, "/exit.png");

        //Adding all the "search movie" components to each user
        //The text field
        searchMovieUserOne = new JTextField();
        searchMovieUserOne.addActionListener(new SearchTitle());
        searchMovieUserTwo = new JTextField();
        searchMovieUserTwo.addActionListener(new SearchTitle());
        //Each score
        resultNumberOne = new JLabel(userNumberOne.getName() + " taste score: ");
        resultNumberOne.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberOne.setForeground(Color.BLACK);
        resultNumberTwo = new JLabel(userNumberTwo.getName() + " taste score: ");
        resultNumberTwo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberTwo.setForeground(Color.BLACK);
        //List to choose exact movie after searching title
        choooseMoviesUOne = new JList();
        choooseMoviesUOne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseMoviesUOne.addMouseListener(selectMovie);

        listModelUOne = new DefaultListModel();
        choooseMoviesUOne.setModel(listModelUOne);

        choooseMoviesUTwo = new JList();
        choooseMoviesUTwo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseMoviesUTwo.addMouseListener(selectMovie);

        listModelUTwo = new DefaultListModel();
        choooseMoviesUTwo.setModel(listModelUTwo);

        //Info about the rules. And the winner

        infoAndWinner = new JLabel("<html><center>Each user have to search <br> the same number of movies</html>");
        infoAndWinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        infoAndWinner.setForeground(Color.BLACK);

        panelMovie.add(moviesButtonExit);
        panelMovie.add(infoAndWinner, "wrap, gapleft 12");
        panelMovie.add(searchMovieUserOne, "w 20:216:216");
        panelMovie.add(searchMovieUserTwo, "w 20:216:216, gapleft 223, wrap");
        panelMovie.add(choooseMoviesUOne, "w 20:286:286");
        panelMovie.add(choooseMoviesUTwo, "wrap, gapleft 223");
        panelMovie.add(resultNumberOne);
        panelMovie.add(resultNumberTwo, "gapleft 223");


        return panelMovie;
    }


    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MoviesTester.this.setVisible(false);
            menu.setVisible(true);
        }
    }

    //Search the title to get the ID, is needed for search the rating
    private class SearchTitle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                //Send the title to search
                String movieTitle = imdbGetMovies.getMovie(((JTextField) e.getSource()).getText());
                //Setup the Json data
                String movieData = movieTitle.substring(movieTitle.indexOf("results") + 9,
                        movieTitle.indexOf("errorMessage") - 2);
                //All the movies with that title (And similar)
                java.lang.reflect.Type movieListType = new TypeToken<ArrayList<Movie>>() {
                }.getType();

                movieList = gson.fromJson(movieData, movieListType);

                isUserOne = e.getSource() == searchMovieUserOne;

                if (isUserOne) {
                    //Adding all the titles in the list
                    listModelUOne.clear();
                    for (Movie movie : movieList) {

                        listModelUOne.addElement(movie.getTitle() + " " + movie.getDescription());
                        JLabel titleMovie = new JLabel(movie.getTitle());
                    }
                } else {
                    listModelUTwo.clear();
                    for (Movie movie : movieList) {

                        listModelUTwo.addElement(movie.getTitle() + " " + movie.getDescription());
                    }
                }

            } catch (IOException ioException) {
                showMessageDialog(null, "Error searching by title");
            }
        }
    }

    private final MouseListener selectMovie = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Select one title of the list send to check the rating and clear the list
            super.mouseClicked(e);
            if (isUserOne) {
                searchMovieRating(movieList.get(choooseMoviesUOne.getSelectedIndex()));
                listModelUOne.clear();


            } else {
                searchMovieRating(movieList.get(choooseMoviesUTwo.getSelectedIndex()));
                listModelUTwo.clear();
            }

            //Changing the winner
            if (userNumberOne.getMovies().size() == userNumberTwo.getMovies().size() && userNumberOne.ratingAverage() > userNumberTwo.ratingAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberOne.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberOne.ratingAverage()) + "</html>");
            } else if (userNumberOne.getMovies().size() == userNumberTwo.getMovies().size() && userNumberOne.ratingAverage() < userNumberTwo.ratingAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberTwo.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberTwo.ratingAverage()) + "</html>");

            } else {
                infoAndWinner.setText("<html><center>Each user have to search <br> the same number of movies</html>");
            }

        }
    };

    //We have to find the rating by ID (We can't do it only by Title)
    private void searchMovieRating(Movie movie) {

        try {
            String movieIdFinder = imdbGetMovies.getRating(movie.getId());
            Movie movieRating = gson.fromJson(movieIdFinder, Movie.class);
            movie.setRating(movieRating.getImDb());
            //The average of the rating is done in User.class
            if (isUserOne) {
                userNumberOne.addMovieToUser(movie);
                resultNumberOne.setText(userNumberOne.getName() + " taste score: " + String.format("%.2f", userNumberOne.ratingAverage()));

            } else {
                userNumberTwo.addMovieToUser(movie);
                resultNumberTwo.setText(userNumberTwo.getName() + " taste score: " + String.format("%.2f", userNumberTwo.ratingAverage()));
            }


        } catch (IOException e) {
            showMessageDialog(null, "Error searching by TitleID");
        }


    }

    private void enterKeyAndUsers() {
        //Setup Key
        boolean close = false;
        ApiKeys.setImdbKey("SetUpYourKey");
        while (!close && (ApiKeys.getImdbKey() == null || !ApiKeys.imdbKey.startsWith("k"))) {
            if (ApiKeys.getImdbKey() == null) {
                int exitMoviesTester = showConfirmDialog(null, "Are you sure that you want to not enter MoviesTester?", "", YES_NO_OPTION);
                if (exitMoviesTester == YES_OPTION) {
                    moviesMenuVisible = false;
                    menuVisible = true;
                    close = true;
                }
            } else {
                ApiKeys.setImdbKey(showInputDialog("Insert your IMDb-Api key"));
            }
        }

        if (!(ApiKeys.getImdbKey() == null) && ApiKeys.getImdbKey().startsWith("k")) {
            //Making two players, NumberOne is the left and NumberTwo is the right
            userNumberOne = SetUpUser.setUpUser(1);
            userNumberTwo = SetUpUser.setUpUser(2);
        } else {
            userNumberOne = SetUpUser.setUpUser("deficere");
            userNumberTwo = SetUpUser.setUpUser("deficere");
        }



    }

    public Boolean getMoviesMenuVisible() {
        return moviesMenuVisible;
    }

    public Boolean getMenuVisible() {
        return menuVisible;
    }
}
