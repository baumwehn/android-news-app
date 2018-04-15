package moritz.course.com.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moritz on 14.04.2018.
 */

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String TAG = NewsActivity.class.getSimpleName();
    private static final int NEWS_LOADER_ID = 0;
    private static final int ARTICLE_LOADER_ID = 1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        TextView btnTextView = (TextView) findViewById(R.id.search_category);
        btnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_text_category);
                String query = editText.getText().toString();
                if (checkConnectivity()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("q", query);
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, bundle, NewsActivity.this);
                }
            }
        });

        if (checkConnectivity()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean networkActive = networkInfo != null && networkInfo.isConnected();
        if (!networkActive) {
            ListView newsListView = (ListView) findViewById(R.id.list);
            TextView emptyView = (TextView) findViewById(R.id.empty_view);
            emptyView.setText(R.string.no_internet_connection);
            newsListView.setEmptyView(emptyView);
            progressBar.setVisibility(View.GONE);
        }
        return networkActive;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pageLimit = sharedPreferences.getString(
                getString(R.string.settings_limit_key),
                getString(R.string.settings_limit_default));

        Uri.Builder uriBuilder = QueryUtil.buildDefaultQuery();
        uriBuilder.appendQueryParameter(QueryUtil.QUERY_PARAM_LIMIT, pageLimit);

        if (bundle == null) {
            return new NewsLoader(NewsActivity.this, uriBuilder.toString());
        }
        uriBuilder.appendQueryParameter("q", bundle.getString("q"));
        return new NewsLoader(NewsActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        updateNews((ArrayList<News>) news);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateNews(new ArrayList<News>());
    }

    public void updateNews(ArrayList<News> news) {
        ListView newsListView = (ListView) findViewById(R.id.list);

        if (news.size() == 0) {
            TextView emptyView = (TextView) findViewById(R.id.empty_view);
            emptyView.setText(R.string.proper_keywords);
            newsListView.setEmptyView(emptyView);
        }
        NewsAdapter adapter = new NewsAdapter(this, news);

        newsListView.setAdapter(adapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final News news = (News) parent.getItemAtPosition(position);
                if (news.getWebUrl().isEmpty()) {
                    return;
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(news.getWebUrl())));
            }
        });
    }
}
