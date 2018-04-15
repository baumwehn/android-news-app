package moritz.course.com.newsapp;

/**
 * Created by Moritz on 14.04.2018.
 */

public class News {

    private String category;
    private String title;
    private String webUrl;
    private String apiUrl;
    private String date;
    private String trailText;
    private String contributor;
    private String newsArticleBody;

    public News(String category, String title, String webUrl, String apiUrl, String date, String trailText) {
        this.category = category;
        this.title = title;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.date = date;
        this.trailText = trailText;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getDate() {
        return date;
    }

    public String getTrailText() {
        return trailText;
    }

    public String getNewsArticle() {
        return newsArticleBody;
    }

    public void createNewsArticleBody() {
        String newsArticleBody = QueryUtil.fetchNewsArticleBody(apiUrl);
    }
}
