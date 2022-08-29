package gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.utilities.*;
import playerdata.Movie;
import playerdata.SetUpUser;
import playerdata.User;

import movies.ImdbMovies;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class MoviesTester extends JDialog {
    private JList choooseMoviesUOne;
    private DefaultListModel listModelUOne;
    private JList choooseMoviesUTwo;
    private DefaultListModel listModelUTwo;
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

    public MoviesTester(){
        gson = new Gson();
        setTitle("MoviesTester");
        //Making two players
        userNumberOne = SetUpUser.setUpUser(1);
        userNumberTwo = SetUpUser.setUpUser(2);


        imdbGetMovies = new ImdbMovies();

        //Window without borders
        setUndecorated(true);

        //The window can be resized and moved
        WindowSettings.prepareWindow(this);

        //Window's ImgIco
        URL imgLogo = getClass().getResource("/logo.png");
        setIconImage(new ImageIcon(imgLogo).getImage());

        setSize(1024, 1000);
        setLocationRelativeTo(null);
        add(componentsMovies());


    }

    //Panel of the components
    private PanelBackgroundImg componentsMovies(){
        PanelBackgroundImg panelMovie = new PanelBackgroundImg(getClass().getResource("/background.png"));
        panelMovie.setLayout(new MigLayout());

        //Exit Button
        JButton moviesButtonExit = new JButton();
        moviesButtonExit.addActionListener(new CloseListener());

        ButtonSettings.prepareButton(moviesButtonExit,"/exit.png");

        searchMovieUserOne = new JTextField();
        searchMovieUserOne.addActionListener(new SearchTitle());
        searchMovieUserTwo = new JTextField();
        searchMovieUserTwo.addActionListener(new SearchTitle());

        resultNumberOne = new JLabel(userNumberOne.getName()+" taste score: ");
        resultNumberTwo = new JLabel(userNumberTwo.getName()+" taste score: ");

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

        panelMovie.add(moviesButtonExit,"wrap");
        panelMovie.add(searchMovieUserOne,"w 20:300:300,wrap");
        panelMovie.add(choooseMoviesUOne,"wrap");
        panelMovie.add(resultNumberOne,"wrap");
        panelMovie.add(searchMovieUserTwo,"w 20:300:300,wrap");
        panelMovie.add(choooseMoviesUTwo,"wrap");
        panelMovie.add(resultNumberTwo);

        return panelMovie;
    }



    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    //Search the title to get the ID, is needed for search the rating
    private class SearchTitle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                //We send the title to search
                String movieTitle = imdbGetMovies.getMovie(((JTextField)e.getSource()).getText());
                //Setup the Json data
                String movieData = movieTitle.substring(movieTitle.indexOf("results")+9,
                        movieTitle.indexOf("errorMessage")-2);
                //All the movies with that title (And similar)
                java.lang.reflect.Type movieListType = new TypeToken<ArrayList<Movie>>(){}.getType();

                movieList = gson.fromJson(movieData,movieListType);

                isUserOne = e.getSource() == searchMovieUserOne;

                if (isUserOne){
                    listModelUOne.clear();
                    for (Movie movie: movieList){

                        listModelUOne.addElement(movie.getTitle()+" "+movie.getDescription());
                    }
                } else {
                    listModelUTwo.clear();
                    for (Movie movie: movieList){

                        listModelUTwo.addElement(movie.getTitle()+" "+movie.getDescription());
                    }
                }

            } catch (IOException ioException) {
                showMessageDialog(null,"Error searching by title");
            }
        }
    }

    //We have to find the rating by ID (We can't do it only by Title)
    private void searchMovieRating(Movie movie){

        try {
            String movieIdFinder = imdbGetMovies.getRating(movie.getId());
            Movie movieRating = gson.fromJson(movieIdFinder,Movie.class);
            movie.setRating(movieRating.getImDb());
            if (isUserOne){
                userNumberOne.addMovieToUser(movie);
                resultNumberOne.setText(userNumberOne.getName()+" taste score: "+ userNumberOne.ratingAverage());
            } else {
                userNumberTwo.addMovieToUser(movie);
                resultNumberTwo.setText(userNumberTwo.getName()+" taste score: "+ userNumberTwo.ratingAverage());
            }


        } catch (IOException e) {
            showMessageDialog(null,"Error searching by TitleID");
        }


    }

    private final MouseListener selectMovie = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (isUserOne){
                searchMovieRating(movieList.get(choooseMoviesUOne.getSelectedIndex()));
                listModelUOne.clear();

            }else{
                searchMovieRating(movieList.get(choooseMoviesUTwo.getSelectedIndex()));
                listModelUTwo.clear();
            }


        }
    };
}
