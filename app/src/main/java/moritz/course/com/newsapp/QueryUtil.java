package moritz.course.com.newsapp;

import java.util.List;

/**
 * Created by Moritz on 14.04.2018.
 */

public class QueryUtil {

    public static final String QUERY = "https://content.guardianapis.com/search?api-key=275e5e77-68c3-4c43-a7f2-37a63f157af9&show-tags=contributor&show-fields=trailText";
    public static final String QUERY_DEFAULT = "https://content.guardianapis.com/search";
    private static final String QUERY_PARAM_BODY = "?api-key=275e5e77-68c3-4c43-a7f2-37a63f157af9&show-fields=body";

    private static final String API_KEY = "api-key=275e5e77-68c3-4c43-a7f2-37a63f157af9";
    private static final String QUERY_PARAM_LIMIT = "page-size=";
    private static final String QUERY_PARAM_FIELDS = "show-fields=";
    private static final String QUERY_PARAM_TAGS = "show-tags=";

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
        return query + QUERY_PARAM_BODY;
    }

    public static String buildQueryFromCategory(String searchCategory) {
        return QUERY + "&q=" + searchCategory;
    }
}
