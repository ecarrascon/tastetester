package settings;

public class ApiKeys {
    //Add your IMDB Api here, you need to create one https://imdb-api.com/Identity/Account/Manage
    public static String imdbKey = "YourKey";

    public static String getImdbKey() {
        return imdbKey;
    }

    public static void setImdbKey(String imdbKey) {
        ApiKeys.imdbKey = imdbKey;
    }
}
