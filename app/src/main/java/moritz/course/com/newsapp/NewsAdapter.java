package moritz.course.com.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Moritz on 14.04.2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title);
        titleTextView.setText(currentNews.getTitle());

        TextView trailTextView = (TextView) listItemView.findViewById(R.id.news_trailer);
        trailTextView.setText(currentNews.getTrailText());

        TextView categoryTextView = (TextView) listItemView.findViewById(R.id.news_category);
        categoryTextView.setText(currentNews.getCategory());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.news_date);
        String dateTime = currentNews.getDate();
        if (!dateTime.isEmpty()) {
            dateTextView.setText(dateTime.replaceAll("[TZ]", " "));
        }

        TextView contributorTextView = (TextView) listItemView.findViewById(R.id.news_contributor);
        contributorTextView.setText(currentNews.getContributor());

        return listItemView;
    }
}
