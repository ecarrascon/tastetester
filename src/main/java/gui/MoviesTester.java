package gui;

import gui.utilities.*;
import playerdata.Movie;
import playerdata.SetUpUser;
import playerdata.User;

import movies.ImdbMovies;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class MoviesTester extends JDialog {
    private JLabel resultNumberOne;
    private JLabel resultNumberTwo;
    private JTextField searchMovieUserOne;
    private JTextField searchMovieUserTwo;
    private final ImdbMovies imdbGetMovies;
    private User userNumberOne;
    private User userNumberTwo;

    public MoviesTester(){
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

        panelMovie.add(moviesButtonExit,"wrap");
        panelMovie.add(searchMovieUserOne,"w 20:300:300,wrap");
        panelMovie.add(resultNumberOne,"wrap");
        panelMovie.add(searchMovieUserTwo,"w 20:300:300,wrap");
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
                //We make the movie, set the tittle
                Movie movie = new Movie(((JTextField)e.getSource()).getText());
                String movieTitle = imdbGetMovies.getMovie(((JTextField)e.getSource()).getText());
                List<String> moviesSaved = Arrays.asList(movieTitle.split(","));
                String[] findIdMovie = moviesSaved.get(7).split("\"");
                //Set the id in the movie object
                movie.setId(findIdMovie[3]);
                Boolean isUserOne = e.getSource() == searchMovieUserOne;
                //We pass the Id to the Rating finder
                searchMovieRating(findIdMovie[3], movie, isUserOne);


            } catch (IOException ioException) {
                showMessageDialog(null,"Error searching by title");
            }
        }
    }



    //We have to find the rating by ID (We can't do it only by Title)
    private void searchMovieRating(String id,Movie movie,Boolean isUserNumberOne){
        try {
            String movieIdFinder = imdbGetMovies.getRating(id);
            List<String> movieIdFinderSplitted = Arrays.asList(movieIdFinder.split(","));
            String[] ratingFound = movieIdFinderSplitted.get(5).split("\"");
            movie.setRating(Double.parseDouble(ratingFound[3]));
            if (isUserNumberOne){
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
}
