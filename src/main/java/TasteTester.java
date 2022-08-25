import gui.Menu;

import javax.swing.*;

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
