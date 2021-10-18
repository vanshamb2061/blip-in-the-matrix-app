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

        try{
            URL url=new URL("https://api.themoviedb.org/3/discover/movie?api_key="+mykey+"&language=en-US"
                    +"&sort_by=with_genres="+genres+"&include_adult="+adult+"&with_watch_monetization_types=free");
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null)
            {
                response.append(line);
                response.append("\r");
            }
            reader.close();
            String result = response.toString();
            //System.out.print(result);


            JSONObject jsonObject1 = new JSONObject(result);
            JSONArray jsonArray= jsonObject1.getJSONArray("results");

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String genre_ids = jsonObject.getString("genre_ids");
                String id = jsonObject.getString("id");
                String original_language = jsonObject.getString("original_language");
                //movie.setName( jsonObject.getString("original_title"));
                String overview = jsonObject.getString("overview");
                //movie.setImgSrc( "https://image.tmdb.org/t/p/w500/"+jsonObject.getString("poster_path"));
                //movie.setYear(jsonObject.getString("release_date"));
                String video = jsonObject.getString("video");
                System.out.println(genre_ids+" --> "+id+" --> "+original_language);
            }


        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }

}
