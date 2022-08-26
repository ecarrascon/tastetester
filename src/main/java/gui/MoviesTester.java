package gui;

import gui.utilities.ComponentMover;
import gui.utilities.ComponentResizer;
import gui.utilities.PanelBackgroundImg;
import movies.imdbMovies;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import static javax.swing.JOptionPane.showMessageDialog;

public class MoviesTester extends JDialog {

    private JTextField searchMovie;
    private imdbMovies imdbGetMovies;
    private String movieTitle;

    public MoviesTester(){

        setTitle("MoviesTester");
        imdbGetMovies = new imdbMovies();

        //Resize and Move
        setUndecorated(true);
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);

        ComponentMover mover = new ComponentMover();
        mover.setChangeCursor(false);
        mover.setDragInsets(new Insets(5, 5, 5, 5));
        mover.registerComponent(this);

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


        //Add img icons to the buttons(And the ExitButton) and clear the buttons background
        try {
            URL imgExit = getClass().getResource("/exit.png");
            moviesButtonExit.setIcon(new ImageIcon(imgExit));
            moviesButtonExit.setBorder(BorderFactory.createEmptyBorder());
            moviesButtonExit.setContentAreaFilled(false);
        } catch (Exception ex) {
            showMessageDialog(null, "Icon/s Not loaded");
        }

        searchMovie = new JTextField();
        searchMovie.addActionListener(new SearchTitle());


        panelMovie.add(moviesButtonExit,"wrap");
        panelMovie.add(searchMovie,"w 20:300:300");




        return panelMovie;


    }
    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class SearchTitle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                movieTitle = imdbGetMovies.getMovie(searchMovie.getText());
                showMessageDialog(null,movieTitle);
            } catch (IOException ioException) {
                showMessageDialog(null,"Error searching title");
            }
        }
    }
}
