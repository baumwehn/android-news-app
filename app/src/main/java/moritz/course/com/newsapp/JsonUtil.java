package moritz.course.com.newsapp;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moritz on 14.04.2018.
 */

public final class JsonUtil {

    private static final String TAG = JsonUtil.class.getSimpleName();

    private JsonUtil() {
    }

    /**
     * Process an JSON response and return a List with news.
     *
     * @param jsonResponse
     * @return newsArray
     */
    public static List<News> processNewsJson(String jsonResponse) {
        ArrayList<News> newsArray = new ArrayList<>();

        if(jsonResponse==null){
            return newsArray;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseObject = jsonObject.getJSONObject("response");
            JSONArray results = responseObject.optJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultJson = (JSONObject) results.get(i);
                String category = resultJson.getString("sectionName");
                String title = resultJson.getString("webTitle");
                String webUrl = resultJson.getString("webUrl");
                String apiUrl = resultJson.getString("apiUrl");
                String date = resultJson.getString("webPublicationDate");

                String contributor = optJsonContributor(resultJson.optJSONArray("tags"));

                JSONObject fields = resultJson.getJSONObject("fields");
                String trailTextHtml = fields.getString("trailText");
                // https://stackoverflow.com/questions/6502759/how-to-strip-or-escape-html-tags-in-android
                String trailText = getTextFromHtml(trailTextHtml).replaceAll("\n", "").trim();

                //storing the apiUrl allows us to fetch an api response to an article and
                // display the news html body in our app if we like to
                News news = new News(category, title, webUrl, apiUrl, date, trailText);
                news.setContributor(contributor);
                newsArray.add(news);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the news JSON results", e);
        }
        return newsArray;
    }

    /**
     * Strips all HTML tags and returns a readable String.
     * @param textHtml
     * @return
     */
    private static String getTextFromHtml(String textHtml) {
        return Html.fromHtml(textHtml).toString();
    }

    private static String optJsonContributor(JSONArray tags) {
        if (tags.length() > 0) {
            try {
                JSONObject tag = (JSONObject) tags.get(0);
                return tag.getString("webTitle");
            } catch (JSONException e) {
                Log.e(TAG, "Problem parsing the tags JSON results", e);
            }
        }
        return "";
    }

    /**
     * Process an API query and return a String as the response.
     *
     * @param urlString (String)
     * @return JSON Reponse (String)
     */
    public static String getNewsJson(String urlString) {
        try {
            return HttpUtil.makeHttpRequest(urlString);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        return null;
    }

    /**
     *
     * @param jsonResponse
     * @return
     */
    public static String processNewsArticleJson(String jsonResponse){
        String body = "";
        if(jsonResponse==null){
            return body;
        }
        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseObject = jsonObject.getJSONObject("response");
            JSONObject content = jsonObject.getJSONObject("content");
            JSONObject fields = jsonObject.getJSONObject("fields");
            body = fields.getString("body");
        }catch (JSONException e) {
            Log.e(TAG, "Problem parsing the news JSON results", e);
        }
        return getTextFromHtml(body);
    }
}
