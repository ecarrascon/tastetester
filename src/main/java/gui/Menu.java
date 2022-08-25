package gui;


import gui.utilities.ComponentMover;
import gui.utilities.ComponentResizer;
import net.miginfocom.swing.MigLayout;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Menu extends JFrame {


    //App Menu, select mode window
    public Menu() {
        super("TasteTester");
        setUndecorated( true );
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);

        ComponentMover mover= new ComponentMover();
        mover.setChangeCursor(false);
        mover.setDragInsets(new Insets(5,5,5,5));
        mover.registerComponent(this);

        URL imgLogo = getClass().getResource("/logo.png");
        setIconImage(new ImageIcon(imgLogo).getImage());


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        add(menuDistribution());
        setLocationRelativeTo(null);



    }
    //Base of Distribution of the components
    private JPanel menuDistribution(){

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("fill,wrap","[]","[][][][]"));
        buildMiGForm(panel);
        return panel;
    }

    //Creation and distribution of the components
    private void buildMiGForm(JPanel panel){






        JButton buttonMovie = new JButton();
        JButton buttonMusic = new JButton();
        JButton buttonBook = new JButton();

        //Add img icons to the buttons and clear the button background
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


        } catch (Exception ex) {
            showMessageDialog(null,"Menu Icons Not loaded");
        }



        //Adding Font and Title
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Maytra.ttf"));
            JLabel title = new JLabel("TasteTester",SwingConstants.CENTER);
            title.setFont(fontTitle.deriveFont(65f));
            title.setForeground(Color.CYAN);
            title.setOpaque(true);
            title.setBackground(Color.GRAY);

            panel.add(title,"growy 1,growx");

        } catch (IOException e) {
            showMessageDialog(null,"Font not found");
        } catch (FontFormatException e) {
            showMessageDialog(null,"Font not found");
        }
        panel.setBackground(Color.BLACK);
        panel.add(buttonMovie, "align center");
        panel.add(buttonBook, "align center");
        panel.add(buttonMusic, "align center");
    }


}
