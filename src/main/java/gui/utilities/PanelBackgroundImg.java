package gui.utilities;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PanelBackgroundImg extends JPanel {
    private URL imgBg;



    public PanelBackgroundImg(URL imgBg) {
        this.imgBg = imgBg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(imgBg).getImage(), 0, 0, this);
    }
}
