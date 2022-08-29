import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.Menu;
import movies.ImdbMovies;
import playerdata.Movie;

import javax.swing.*;
import java.io.IOException;


public class TasteTester {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });

    }
}
