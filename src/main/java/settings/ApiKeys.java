package settings;

import javax.swing.*;
import java.io.*;

public class ApiKeys {
    private static final File keysFile = new File("keys.txt");

    //Add your IMDB Api here, you need to create one https://imdb-api.com/Identity/Account/Manage
    public static String imdbKey = "YourKey";

    public static String getImdbKey() {
        return imdbKey;
    }

    public static void setImdbKey(String imdbKey) {
        ApiKeys.imdbKey = imdbKey;
    }

    public static File getKeysFile() {
        return keysFile;
    }

    public static void writeKey(String key){
        try (Writer writer = new BufferedWriter(new FileWriter(keysFile.getName()))) {
            writer.write(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
