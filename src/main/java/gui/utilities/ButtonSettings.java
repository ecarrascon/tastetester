package gui.utilities;

import javax.swing.*;
import java.net.URL;

import static javax.swing.JOptionPane.showMessageDialog;


//Add img icons to the buttons and clear the buttons background
public class ButtonSettings {
    public static void prepareButton(JButton button, String url) {
        try {
            URL imgExit = ButtonSettings.class.getResource(url);
            button.setIcon(new ImageIcon(imgExit));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
        } catch (Exception ex) {
            showMessageDialog(null, "Icon/s Not loaded");
        }
    }
}
