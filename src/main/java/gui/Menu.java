package gui;


import gui.utilities.*;
import net.miginfocom.swing.MigLayout;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Menu extends JFrame {
    //App Menu, select mode window
    public Menu() {
        super("TasteTester");
        setUndecorated(true);
        WindowSettings.prepareWindow(this);

        //Window's ImgIco
        URL imgLogo = getClass().getResource("/logo.png");
        assert imgLogo != null : "Logo not found";
        setIconImage(new ImageIcon(imgLogo).getImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(864, 366);
        add(menuDistribution());
        setLocationRelativeTo(null);

    }

    //Base of Distribution of the components
    private JPanel menuDistribution() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new MigLayout(" fill", "[][][]", "[1mm!][][]"));
        buildMiGForm(panel);
        return panel;
    }

    //Creation and distribution of the components
    private void buildMiGForm(JPanel panel) {
        //Exit and minimize Button
        JButton buttonExit = new JButton();
        buttonExit.addActionListener(new CloseListener());
        JButton buttonMinimize = new JButton();
        buttonMinimize.addActionListener(new MinimizeListener());

        //The other buttons in the main menu
        JButton buttonMovie = new JButton();
        buttonMovie.addActionListener(e -> openMoviesTester());
        JButton buttonSeries = new JButton();
        buttonSeries.addActionListener(e -> openSeriesTester());
        /* Future features Rating music books etc
        JButton buttonMusic = new JButton();
        JButton buttonBook = new JButton();
        */

        //Prepare all the buttons, img, clear background etc
        ButtonSettings.prepareButton(buttonMovie, "/movies.png");
        ButtonSettings.prepareButton(buttonSeries, "/series.png");
        /*  Future features Rating music books etc
        ButtonSettings.prepareButton(buttonMusic,"/music.png");
        ButtonSettings.prepareButton(buttonBook,"/book.png");
         */
        ButtonSettings.prepareButton(buttonExit, "/exit.png");
        ButtonSettings.prepareButton(buttonMinimize, "/minimize.png");


        //Exit Button added before Title
        panel.add(buttonExit);
        panel.add(buttonMinimize, "wrap");

        //Adding Font and Title
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Voga-Medium.otf")));
            JLabel title = new JLabel("TasteTester", SwingConstants.CENTER);
            title.setFont(fontTitle.deriveFont(Font.BOLD, 125f));
            title.setForeground(Color.BLACK);


            Font fontSubTitle = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Voga-Medium.otf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontSubTitle);
            JLabel subTitle = new JLabel("<html><center><b>Welcome! Click the movie's button to test your taste <br/> You can do it alone or with a friend. Who will have a better taste? Good luck! </b> </html>", SwingConstants.CENTER);
            subTitle.setFont(fontSubTitle.deriveFont(34f));
            subTitle.setForeground(Color.decode("#122c3b"));


            panel.add(title, "grow, span, wrap");
            panel.add(subTitle, "grow, span, wrap");


        } catch (IOException | FontFormatException e) {
            showMessageDialog(null, "Font not found");
        }


        //Adding all the Buttons
        panel.add(buttonMovie, "align center, span, split 2");
        panel.add(buttonSeries, "align center, span");

        /*  Future features Rating music books etc
        panel.add(buttonBook, "align center");
        panel.add(buttonMusic, "align center");
         */

    }

    //Listener open Movies Tester Window
    private void openMoviesTester() {
        SwingUtilities.invokeLater(() -> {
            MoviesTester moviesTester = new MoviesTester(Menu.this);
            moviesTester.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            moviesTester.setVisible(moviesTester.getMoviesMenuVisible());
            Menu.this.setVisible(moviesTester.getMenuVisible());
        });
    }

    //Listener open Series Tester Window
    private void openSeriesTester() {
        SwingUtilities.invokeLater(() -> {
            SeriesTester seriesTester = new SeriesTester(Menu.this);
            seriesTester.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            seriesTester.setVisible(seriesTester.getSeriesMenuVisible());
            Menu.this.setVisible(seriesTester.getMenuVisible());
        });
    }


    //Listener to exit everything
    private static class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class MinimizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Menu.this.setState(Frame.ICONIFIED);
        }
    }
}
