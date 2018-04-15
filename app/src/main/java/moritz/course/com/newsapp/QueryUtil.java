package moritz.course.com.newsapp;

import android.net.Uri;

import java.util.List;

/**
 * Created by Moritz on 14.04.2018.
 */

public class QueryUtil {

    public static final String REQUEST_URL = "https://content.guardianapis.com/search";

    public static final String API_KEY = "275e5e77-68c3-4c43-a7f2-37a63f157af9";
    public static final String QUERY_PARAM_KEY = "api-key";
    public static final String QUERY_PARAM_LIMIT = "page-size";
    public static final String QUERY_PARAM_FIELDS = "show-fields";
    public static final String QUERY_PARAM_TAGS = "show-tags";

    private static final String TAG = QueryUtil.class.getSimpleName();

    private QueryUtil() {
    }

    public static List<News> fetchNews(String urlString) {
        return JsonUtil.processNewsJson(JsonUtil.getNewsJson(urlString));
    }

    public static String fetchNewsArticleBody(String urlString){
        return JsonUtil.processNewsArticleJson(JsonUtil.getNewsJson(getQueryParamBody(urlString)));
    }

    private static String getQueryParamBody(String query){
        Uri baseUri = Uri.parse(QueryUtil.REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(QUERY_PARAM_KEY, API_KEY);
        uriBuilder.appendQueryParameter(QUERY_PARAM_FIELDS, "trailText, body");
        uriBuilder.appendQueryParameter(QUERY_PARAM_TAGS, "contributor");

        return uriBuilder.toString();
    }

    public static Uri.Builder buildDefaultQuery(){
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(QUERY_PARAM_KEY, API_KEY);
        uriBuilder.appendQueryParameter(QUERY_PARAM_FIELDS, "trailText");
        uriBuilder.appendQueryParameter(QUERY_PARAM_TAGS, "contributor");
        return uriBuilder;
    }
}
