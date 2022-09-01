package playerdata;

import javax.swing.*;

public class SetUpUser {
    //User generator
    public static User setUpUser(int numberPlayer) {
        return new User(JOptionPane.showInputDialog("Name of user number " + numberPlayer));
    }

}
