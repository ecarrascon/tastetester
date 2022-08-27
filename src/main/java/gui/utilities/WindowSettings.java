package gui.utilities;

import java.awt.*;

public class WindowSettings {
    //The window can be resized and moved
    public static void prepareWindow(Window window){
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(window);
        ComponentMover mover = new ComponentMover();
        mover.setChangeCursor(false);
        mover.setDragInsets(new Insets(5, 5, 5, 5));
        mover.registerComponent(window);

    }
}
