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

public class Menu extends JFrame {
    //App Menu, select mode window
    public Menu() {
        super("TasteTester");
        setUndecorated(true);
        WindowSettings.prepareWindow(this);

        //Window's ImgIco
        URL imgLogo = getClass().getResource("/logo.png");
        setIconImage(new ImageIcon(imgLogo).getImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(864, 366);
        add(menuDistribution());
        setLocationRelativeTo(null);

    }

    //Base of Distribution of the components
    private JPanel menuDistribution() {
        JPanel panel = new JPanel();

        panel.setLayout(new MigLayout(" fill", "[][][]", "[1mm!][][]"));
        buildMiGForm(panel);
        return panel;
    }

    //Creation and distribution of the components
    private void buildMiGForm(JPanel panel) {
        //Exit Button
        JButton buttonExit = new JButton();
        buttonExit.addActionListener(new CloseListener());

        //The other buttons in the main menu
        JButton buttonMovie = new JButton();
        buttonMovie.addActionListener(new OpenMovieTester());
        /* Future features Rating music books etc
        JButton buttonMusic = new JButton();
        JButton buttonBook = new JButton();
        */

        ButtonSettings.prepareButton(buttonMovie, "/cinema.png");
        /*  Future features Rating music books etc
        ButtonSettings.prepareButton(buttonMusic,"/music.png");
        ButtonSettings.prepareButton(buttonBook,"/book.png");
         */
        ButtonSettings.prepareButton(buttonExit, "/exit.png");


        //Exit Button added before Title
        panel.add(buttonExit, "wrap");

        //Adding Font and Title
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Voga-Medium.otf"));
            JLabel title = new JLabel("TasteTester", SwingConstants.CENTER);
            title.setFont(fontTitle.deriveFont(Font.BOLD, 125f));
            title.setForeground(Color.BLACK);


            Font fontSubTitle = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Voga-Medium.otf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontSubTitle);
            JLabel subTitle = new JLabel("<html><center><b>Welcome! Click the movie's button to test your taste <br/> You can do it alone or with a friend. Who will have a better taste? Good luck! </b> </html>", SwingConstants.CENTER);
            subTitle.setFont(fontSubTitle.deriveFont(34f));
            subTitle.setForeground(Color.decode("#122c3b"));


            panel.add(title, "grow, span, wrap");
            panel.add(subTitle, "grow, span, wrap");


        } catch (IOException e) {
            showMessageDialog(null, "Font not found");
        } catch (FontFormatException e) {
            showMessageDialog(null, "Font not found");
        }


        //Adding all the Buttons
        panel.add(buttonMovie, "align center, span");

        /*  Future features Rating music books etc
        panel.add(buttonBook, "align center");
        panel.add(buttonMusic, "align center");
         */

    }

    //Listener open Movies Tester Window
    private class OpenMovieTester implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MoviesTester moviesTester = new MoviesTester();
                    moviesTester.setVisible(true);
                    moviesTester.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    Menu.this.setVisible(false);
                }
            });
        }
    }

    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
