package gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.utilities.*;
import playerdata.Series;
import playerdata.SetUpUser;
import playerdata.User;

import apis.Imdb;
import net.miginfocom.swing.MigLayout;
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

public class SeriesTester extends JFrame {
    private User userNumberOne;
    private User userNumberTwo;
    private JList choooseSeriesUOne;
    private JList choooseSeriesUTwo;
    private DefaultListModel listModelUOne;
    private DefaultListModel listModelUTwo;
    private JLabel infoAndWinner;
    private JLabel resultNumberOne;
    private JLabel resultNumberTwo;
    private JLabel selectedSeriesUserOne;
    private JLabel selectedSeriesUserTwo;
    private JTextField searchSerieUserOne;
    private JTextField searchSerieUserTwo;
    private List<Series> seriesList;
    private boolean isUserOne;
    private boolean seriesMenuVisible = true;
    private boolean menuVisible = false;
    private final Imdb imdbGetSeries;
    private final Gson gson;
    private final Menu menu;

    public SeriesTester(Menu menu) {
        this.menu = menu;
        enterKeyAndUsers();

        setBackground(Color.WHITE);
        gson = new Gson();
        setTitle("SeriesTester");

        //The Api of Imdb
        imdbGetSeries = new Imdb();

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
        add(componentsSeries());
    }

    //Panel of the components
    private JPanel componentsSeries() {
        //Setting the Panel
        JPanel panelSeries = new JPanel();
        panelSeries.setLayout(new MigLayout());
        panelSeries.setBackground(Color.WHITE);

        //Exit and minimize Button
        JButton seriesButtonExit = new JButton();
        seriesButtonExit.addActionListener(new CloseListener());
        ButtonSettings.prepareButton(seriesButtonExit, "/exit.png");

        JButton seriesButtonMinimize = new JButton();
        seriesButtonMinimize.addActionListener(new MinimizeListener());
        ButtonSettings.prepareButton(seriesButtonMinimize, "/minimize.png");

        //Info about the rules. And the winner
        infoAndWinner = new JLabel("<html><center>Each user have to search <br> the same number of series</html>");
        infoAndWinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        infoAndWinner.setForeground(Color.BLACK);

        //Adding all the "search series" components to each user
        //The text field
        searchSerieUserOne = new JTextField();
        searchSerieUserOne.addActionListener(new SearchTitle());
        searchSerieUserTwo = new JTextField();
        searchSerieUserTwo.addActionListener(new SearchTitle());
        //Each score
        resultNumberOne = new JLabel(userNumberOne.getName() + " taste score: ");
        resultNumberOne.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberOne.setForeground(Color.BLACK);
        resultNumberTwo = new JLabel(userNumberTwo.getName() + " taste score: ");
        resultNumberTwo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        resultNumberTwo.setForeground(Color.BLACK);
        //List to choose exact serie after searching title
        choooseSeriesUOne = new JList();
        choooseSeriesUOne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseSeriesUOne.addMouseListener(selectSeries);

        listModelUOne = new DefaultListModel();
        choooseSeriesUOne.setModel(listModelUOne);

        choooseSeriesUTwo = new JList();
        choooseSeriesUTwo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choooseSeriesUTwo.addMouseListener(selectSeries);

        listModelUTwo = new DefaultListModel();
        choooseSeriesUTwo.setModel(listModelUTwo);

        //Showing the series selected
        selectedSeriesUserOne = new JLabel();
        selectedSeriesUserTwo = new JLabel();

        panelSeries.add(seriesButtonExit, "split 2");
        panelSeries.add(seriesButtonMinimize);
        panelSeries.add(infoAndWinner, "wrap, gapleft 8");
        panelSeries.add(searchSerieUserOne, "w 20:216:216");
        panelSeries.add(searchSerieUserTwo, "w 20:216:216, gapleft 223, wrap");
        panelSeries.add(choooseSeriesUOne, "w 20:286:286");
        panelSeries.add(choooseSeriesUTwo, "wrap, gapleft 223");
        panelSeries.add(resultNumberOne);
        panelSeries.add(resultNumberTwo, "gapleft 223, wrap");
        panelSeries.add(selectedSeriesUserOne);
        panelSeries.add(selectedSeriesUserTwo, "gapleft 223");

        return panelSeries;
    }


    //Listener to exit everything
    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SeriesTester.this.setVisible(false);
            menu.setVisible(true);
        }
    }

    //Search the title to get the ID, is needed for search the rating
    private class SearchTitle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Send the title to search
                String seriesTitle = imdbGetSeries.getSeries(((JTextField) e.getSource()).getText());
                //Setup the Json data
                String seriesData = seriesTitle.substring(seriesTitle.indexOf("results") + 9,
                        seriesTitle.indexOf("errorMessage") - 2);
                //All the series with that title (And similar)
                java.lang.reflect.Type seriesListType = new TypeToken<ArrayList<Series>>() {
                }.getType();

                seriesList = gson.fromJson(seriesData, seriesListType);

                isUserOne = e.getSource() == searchSerieUserOne;
                if (isUserOne) {
                    //Adding all the titles in the list
                    listModelUOne.clear();
                    for (Series seriesInList : seriesList) {
                        listModelUOne.addElement(seriesInList.getTitle() + " " + seriesInList.getDescription());
                    }
                } else {
                    listModelUTwo.clear();
                    for (Series seriesInlist : seriesList) {
                        listModelUTwo.addElement(seriesInlist.getTitle() + " " + seriesInlist.getDescription());
                    }
                }

            } catch (IOException ioException) {
                showMessageDialog(null, "Error searching by title");
            }
        }
    }

    private final MouseListener selectSeries = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Select one title of the list send to check the rating and clear the list
            super.mouseClicked(e);
            if (isUserOne) {
                searchSeriesRating(seriesList.get(choooseSeriesUOne.getSelectedIndex()));
                listModelUOne.clear();


            } else {
                searchSeriesRating(seriesList.get(choooseSeriesUTwo.getSelectedIndex()));
                listModelUTwo.clear();
            }

            //Changing the winner
            if (userNumberOne.getSeries().size() == userNumberTwo.getSeries().size() && userNumberOne.ratingSeriesAverage() > userNumberTwo.ratingSeriesAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberOne.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberOne.ratingSeriesAverage()) + "</html>");
            } else if (userNumberOne.getSeries().size() == userNumberTwo.getSeries().size() && userNumberOne.ratingSeriesAverage() < userNumberTwo.ratingSeriesAverage()) {
                infoAndWinner.setText("<html><center>" + userNumberTwo.getName() + " is the winner!<br>With a taste of: " + String.format("%.2f", userNumberTwo.ratingSeriesAverage()) + "</html>");
            } else {
                infoAndWinner.setText("<html><center>Each user have to search <br> the same number of series</html>");
            }

        }
    };

    //We have to find the rating by ID (We can't do it only by Title)
    private void searchSeriesRating(Series seriesGiven) {
        try {
            String seriesIdFinder = imdbGetSeries.getRating(seriesGiven.getId());
            Series seriesRating = gson.fromJson(seriesIdFinder, Series.class);
            seriesGiven.setRating(seriesRating.getImDb());
            //The average of the rating is done in User.class
            if (isUserOne) {
                userNumberOne.addSeriesToUser(seriesGiven);
                resultNumberOne.setText(userNumberOne.getName() + " taste score: " + String.format("%.2f", userNumberOne.ratingSeriesAverage()));
                selectedSeriesUserOne.setText("<html>" + selectedSeriesUserOne.getText().replaceAll("<html>|</html>", "") + "<br>" + seriesGiven.getTitle() + " " + seriesGiven.getDescription() + "</html>");
            } else {
                userNumberTwo.addSeriesToUser(seriesGiven);
                resultNumberTwo.setText(userNumberTwo.getName() + " taste score: " + String.format("%.2f", userNumberTwo.ratingSeriesAverage()));
                selectedSeriesUserTwo.setText("<html>" + selectedSeriesUserTwo.getText().replaceAll("<html>|</html>", "") + "<br>" + seriesGiven.getTitle() + " " + seriesGiven.getDescription() + "</html>");
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
                        int exitSeriesTester = showConfirmDialog(null, "Are you sure that you want to not enter SeriesTester?", "", YES_NO_OPTION);
                        if (exitSeriesTester == YES_OPTION) {
                            seriesMenuVisible = false;
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
            SeriesTester.this.setState(Frame.ICONIFIED);
        }
    }

    public boolean getSeriesMenuVisible() {
        return seriesMenuVisible;
    }

    public boolean getMenuVisible() {
        return menuVisible;
    }
}

