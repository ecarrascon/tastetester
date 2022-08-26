package gui;


import gui.utilities.ComponentMover;
import gui.utilities.ComponentResizer;
import gui.utilities.PanelBackgroundImg;
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

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 1000);
        add(menuDistribution());
        setLocationRelativeTo(null);

    }

    //Base of Distribution of the components
    private JPanel menuDistribution() {
        PanelBackgroundImg panel = new PanelBackgroundImg(getClass().getResource("/background.png"));
        panel.setLayout(new MigLayout("fill,debug", "[][][]", "[1mm!][][]"));
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
        JButton buttonMusic = new JButton();
        JButton buttonBook = new JButton();

        //Add img icons to the buttons(And the ExitButton) and clear the buttons background
        try {
            URL imgMovie = getClass().getResource("/cinema.png");
            buttonMovie.setIcon(new ImageIcon(imgMovie));
            buttonMovie.setBorder(BorderFactory.createEmptyBorder());
            buttonMovie.setContentAreaFilled(false);

            URL imgMusic = getClass().getResource("/music.png");
            buttonMusic.setIcon(new ImageIcon(imgMusic));
            buttonMusic.setBorder(BorderFactory.createEmptyBorder());
            buttonMusic.setContentAreaFilled(false);

            URL imgBook = getClass().getResource("/book.png");
            buttonBook.setIcon(new ImageIcon(imgBook));
            buttonBook.setBorder(BorderFactory.createEmptyBorder());
            buttonBook.setContentAreaFilled(false);

            URL imgExit = getClass().getResource("/exit.png");
            buttonExit.setIcon(new ImageIcon(imgExit));
            buttonExit.setBorder(BorderFactory.createEmptyBorder());
            buttonExit.setContentAreaFilled(false);
        } catch (Exception ex) {
            showMessageDialog(null, "Menu Icons Not loaded");
        }

        //Exit Button added before Title
        panel.add(buttonExit, "wrap");

        //Adding Font and Title
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Maytra.ttf"));
            JLabel title = new JLabel("TasteTester", SwingConstants.CENTER);
            title.setFont(fontTitle.deriveFont(65f));

            panel.add(title, "grow,span ,wrap");

        } catch (IOException e) {
            showMessageDialog(null, "Font not found");
        } catch (FontFormatException e) {
            showMessageDialog(null, "Font not found");
        }

        //Adding all the Buttons
        panel.add(buttonMovie, "align center");
        panel.add(buttonBook, "align center");
        panel.add(buttonMusic, "align center");
    }

    //Listener open Movies Tester Window
    private class OpenMovieTester implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MoviesTester moviesTester = new MoviesTester();
                    moviesTester.setVisible(true);
                    moviesTester.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
