package gui;

import apis.Imdb;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.utilities.ButtonSettings;
import gui.utilities.WindowSettings;
import net.miginfocom.swing.MigLayout;
import playerdata.Anime;
import playerdata.SetUpUser;
import playerdata.User;
import settings.ApiKeys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

public class AnimeTester extends JFrame {
    private User userNumberOne;
    private User userNumberTwo;
    private JList choooseAnimeUOne;
    private JList choooseAnimeUTwo;
    private DefaultListModel listModelUOne;
    private DefaultListModel listModelUTwo;
    private JLabel infoAndWinner;
    private JLabel resultNumberOne;
    private JLabel resultNumberTwo;
    private JLabel selectedAnimeUserOne;
    private JLabel selectedAnimeUserTwo;
    private JTextField searchAnimeUserOne;
    private JTextField searchAnimeUserTwo;
    private List<Anime> animeList;
    private boolean isUserOne;
    private boolean animeMenuVisible = true;
    private boolean menuVisible = false;
    private final Imdb imdbGetAnime;
    private final Gson gson;
    private final Menu menu;

    public AnimeTester(Menu menu) {
        this.menu = menu;
        enterKeyAndUsers();

        setBackground(Color.WHITE);
        gson = new Gson();
        setTitle("AnimeTester");

        //The Api of Imdb
        imdbGetAnime = new Imdb();

        //Window without borders
        setUndecorated(true);

        //The window can be resized and moved
        WindowSettings.prepareWindow(this);

        //Window's ImgIco
        URL imgLogo = getClass().getResource("/logo.png");
        assert imgLogo != null : "No window's logo found";
        setIconImage(new ImageIcon(imgLogo).getImage());

        setSize(670, 300);
        setLocationRelativeTo(null);

        //Add all the components
        add(componentsAnime());
    }

    //Panel of the components
    private JPanel componentsAnime() {
        //Setting the Panel
        JPanel panelAnime = new JPanel();
        panelAnime.setLayout(new MigLayout());
        panelAnime.setBackground(Color.WHITE);

        //Exit and minimize Button
        JButton animeButtonExit = new JButton();
        animeButtonExit.addActionListener(new CloseListener());
        ButtonSettings.prepareButton(animeButtonExit, "/exit.png");

        JButton animeButtonMinimize = new JButton();
        animeButtonMinimize.addActionListener(new MinimizeListener());
        ButtonSettings.prepareButton(animeButtonMinimize, "/minimize.png");

        //Info about the rules. And the winner
        infoAndWinner = new JLabel("<html><center>Each user have to search <br> the same number of anime</html>");
        infoAndWinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        infoAndWinner.setForeground(Color.BLACK);

        //Adding all the "search anime" components to each user
        //The text field
        searchAnimeUserOne = new JTextField();
        searchAnimeUserOne.addActionListener(new SearchTitle());
        searchAnimeUserTwo = new JTextField();
        searchAnimeUserTwo.addActionListener(new SearchTitle());
        //Each score
        resultNumberOne = new JLabel(userNumberOne.getName() + " taste score: ");
        resultNumberOne.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberOne.setForeground(Color.BLACK);
        resultNumberTwo = new JLabel(userNumberTwo.getName() + " taste score: ");
        resultNumberTwo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberTwo.setForeground(Color.BLACK);
        //List to choose exact anime after searching title
        choooseAnimeUOne = new JList();
        choooseAnimeUOne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseAnimeUOne.addMouseListener(selectAnime);

        listModelUOne = new DefaultListModel();
        choooseAnimeUOne.setModel(listModelUOne);

        choooseAnimeUTwo = new JList();
        choooseAnimeUTwo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseAnimeUTwo.addMouseListener(selectAnime);

        listModelUTwo = new DefaultListModel();
        choooseAnimeUTwo.setModel(listModelUTwo);

        //Showing the anime selected
        selectedAnimeUserOne = new JLabel();
        selectedAnimeUserTwo = new JLabel();

        panelAnime.add(animeButtonExit, "split 2");
        panelAnime.add(animeButtonMinimize);
        panelAnime.add(infoAndWinner, "wrap, gapleft 8");
        panelAnime.add(searchAnimeUserOne, "w 20:216:216");
        panelAnime.add(searchAnimeUserTwo, "w 20:216:216, gapleft 223, wrap");
        panelAnime.add(choooseAnimeUOne, "w 20:286:286");
        panelAnime.add(choooseAnimeUTwo, "wrap, gapleft 223");
        panelAnime.add(resultNumberOne);
        panelAnime.add(resultNumberTwo, "gapleft 223, wrap");
        panelAnime.add(selectedAnimeUserOne);
        panelAnime.add(selectedAnimeUserTwo, "gapleft 223");

        return panelAnime;
    }


    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AnimeTester.this.setVisible(false);
            menu.setVisible(true);
        }
    }

    //Search the title to get the ID, is needed for search the rating
    private class SearchTitle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Send the title to search
                String animeTitle = imdbGetAnime.getAnime(((JTextField) e.getSource()).getText());
                //Setup the Json data
                String animeData = animeTitle.substring(animeTitle.indexOf("results") + 9,
                        animeTitle.indexOf("errorMessage") - 2);
                //All the anime with that title (And similar)
                java.lang.reflect.Type animeListType = new TypeToken<ArrayList<Anime>>() {
                }.getType();

                animeList = gson.fromJson(animeData, animeListType);

                isUserOne = e.getSource() == searchAnimeUserOne;
                if (isUserOne) {
                    //Adding all the titles in the list
                    listModelUOne.clear();
                    for (Anime animeInList : animeList) {
                        listModelUOne.addElement(animeInList.getTitle() + " " + animeInList.getDescription());
                    }
                } else {
                    listModelUTwo.clear();
                    for (Anime animeInlist : animeList) {
                        listModelUTwo.addElement(animeInlist.getTitle() + " " + animeInlist.getDescription());
                    }
                }

            } catch (IOException ioException) {
                showMessageDialog(null, "Error searching by title");
            }
        }
    }

    private final MouseListener selectAnime = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Select one title of the list send to check the rating and clear the list
            super.mouseClicked(e);
            if (isUserOne) {
                searchAnimeRating(animeList.get(choooseAnimeUOne.getSelectedIndex()));
                listModelUOne.clear();


            } else {
                searchAnimeRating(animeList.get(choooseAnimeUTwo.getSelectedIndex()));
                listModelUTwo.clear();
            }

            //Changing the winner
            if (userNumberOne.getAnime().size() == userNumberTwo.getAnime().size() && userNumberOne.ratingAnimeAverage() > userNumberTwo.ratingAnimeAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberOne.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberOne.ratingAnimeAverage()) + "</html>");
            } else if (userNumberOne.getAnime().size() == userNumberTwo.getAnime().size() && userNumberOne.ratingAnimeAverage() < userNumberTwo.ratingAnimeAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberTwo.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberTwo.ratingAnimeAverage()) + "</html>");
            } else {
                infoAndWinner.setText("<html><center>Each user have to search <br> the same number of anime</html>");
            }

        }
    };

    //We have to find the rating by ID (We can't do it only by Title)
    private void searchAnimeRating(Anime animeGiven) {
        try {
            String animeIdFinder = imdbGetAnime.getRating(animeGiven.getId());
            Anime animeRating = gson.fromJson(animeIdFinder, Anime.class);
            animeGiven.setRating(animeRating.getImDb());
            //The average of the rating is done in User.class
            if (isUserOne) {
                userNumberOne.addAnimeToUser(animeGiven);
                resultNumberOne.setText(userNumberOne.getName() + " taste score: " + String.format("%.2f", userNumberOne.ratingAnimeAverage()));
                selectedAnimeUserOne.setText("<html>" + selectedAnimeUserOne.getText().replaceAll("<html>|</html>", "") + "<br>" + animeGiven.getTitle() + " " + animeGiven.getDescription() + "</html>");
            } else {
                userNumberTwo.addAnimeToUser(animeGiven);
                resultNumberTwo.setText(userNumberTwo.getName() + " taste score: " + String.format("%.2f", userNumberTwo.ratingAnimeAverage()));
                selectedAnimeUserTwo.setText("<html>" + selectedAnimeUserTwo.getText().replaceAll("<html>|</html>", "") + "<br>" + animeGiven.getTitle() + " " + animeGiven.getDescription() + "</html>");
            }
        } catch (IOException e) {
            showMessageDialog(null, "Error searching by TitleID");
        }
    }

    private void enterKeyAndUsers() {
        //Setup Key
        try {
            if (!(ApiKeys.getKeysFile().createNewFile())){
                ApiKeys.setImdbKey(Files.readString(ApiKeys.getKeysFile().toPath()));
            }
        } catch (IOException e) {
            showMessageDialog(null, "Error creating file");
        }

        try {
            if (Files.readString(ApiKeys.getKeysFile().toPath()) == null || !(Files.readString(ApiKeys.getKeysFile().toPath()).startsWith("k"))){
                boolean close = false;
                ApiKeys.setImdbKey("YourKey");
                while (!close && (ApiKeys.getImdbKey() == null || !ApiKeys.imdbKey.startsWith("k"))) {
                    if (ApiKeys.getImdbKey() == null) {
                        int exitAnimeTester = showConfirmDialog(null, "Are you sure that you want to not enter AnimeTester?", "", YES_NO_OPTION);
                        if (exitAnimeTester == YES_OPTION) {
                            animeMenuVisible = false;
                            menuVisible = true;
                            close = true;
                        }
                    } else {
                        ApiKeys.setImdbKey(showInputDialog("Insert your IMDb-Api key"));
                    }
                }
            }
        } catch (IOException e) {
            showMessageDialog(null, "Error finding file");
        }


        //Making two players, NumberOne is the left and NumberTwo is the right
        if (!(ApiKeys.getImdbKey() == null) && ApiKeys.getImdbKey().startsWith("k")) {
            ApiKeys.writeKey(ApiKeys.getImdbKey());

            userNumberOne = SetUpUser.setUpUser(1);
            userNumberTwo = SetUpUser.setUpUser(2);
        } else {
            userNumberOne = new User("deficere");
            userNumberTwo = new User("deficere");
        }
    }

    private class MinimizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AnimeTester.this.setState(Frame.ICONIFIED);
        }
    }

    public boolean getAnimeMenuVisible() {
        return animeMenuVisible;
    }

    public boolean getMenuVisible() {
        return menuVisible;
    }
}

