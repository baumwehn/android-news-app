package moritz.course.com.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import moritz.course.com.newsapp.Util.QueryUtil;

/**
 * Created by Moritz on 14.04.2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return QueryUtil.fetchNews(mUrl);
    }
}
