package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TMDB{

    public void api() throws Exception
    {
        HttpURLConnection connection=null;
        final String mykey = "3ddfe235acf65e0759d82a7ee3729e67";
        String genres="Action";
        boolean adult=true;

        String genre_ids;
        String id;
        String original_language;
        String overview;
        String video;

        int pg_no=1;

        URL url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US"
                + "&include_adult=" + adult + "&page="+pg_no);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append("\r");
        }
        reader.close();
        String result = response.toString();

        JSONObject jsonObject1 = new JSONObject(result);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");


    }

}
