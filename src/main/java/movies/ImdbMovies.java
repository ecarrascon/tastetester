package movies;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import settings.ApiKeys;
import java.io.IOException;

public class ImdbMovies {
    private OkHttpClient client = new OkHttpClient().newBuilder().build();
     public String getMovie(String title) throws IOException {
        Request request = new Request.Builder()
                .url("https://imdb-api.com/en/API/SearchMovie/"+ApiKeys.imdbKey+"/"+title)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();

    }

    public String getRating(String id) throws IOException {
        Request request = new Request.Builder()
                .url("https://imdb-api.com/API/Ratings/"+ApiKeys.imdbKey+"/"+id)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();


    }
}
