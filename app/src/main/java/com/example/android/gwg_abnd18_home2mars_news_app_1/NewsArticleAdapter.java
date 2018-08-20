package com.example.android.gwg_abnd18_home2mars_news_app_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link NewsArticleAdapter} knows how to create a list item layout for each news article
 * in the data source (a list of {@link NewsArticle} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    /**
     * Constructs a new {@link NewsArticleAdapter}.
     *
     * @param context      of the app
     * @param newsArticles is the list of newsArticles, which is the data source of the adapter
     */
    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    /**
     * Returns a list item view that displays information about the news article at the given position
     * in the list of news articles.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news article at the given position in the list of news articles
        NewsArticle currentNewsArticle = getItem(position);

        // Find the TextView with view ID article_title
        TextView articleTitleView = (TextView) listItemView.findViewById(R.id.article_title);
        // Display the title of the current news article in that TextView
        articleTitleView.setText(currentNewsArticle.getTitle());

        // Find the TextView with view ID article_section
        TextView articleSectionView = (TextView) listItemView.findViewById(R.id.article_section);
        // Display the section of the current news article in that TextView
        articleSectionView.setText(currentNewsArticle.getSection());

        // Find the TextView with view ID article_byline
        TextView articleBylineView = (TextView) listItemView.findViewById(R.id.article_byline);
        //check if byline (author) info is available, if not, set TextView to invisible
        String byline = currentNewsArticle.getByline();
        if (byline.equals(null) || byline.equals("")) {
            articleBylineView.setVisibility(View.GONE);
        } else {
            // Display the byline of the current news article in that TextView
            articleBylineView.setText(byline);
        }

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.article_date);
        //check if article date info is available
        String articleDate = currentNewsArticle.getDate();
        if (articleDate == null || articleDate == "") {
            dateView.setText("");
        } else {
            // Format the date string (i.e. "2018-08-19" from 2018-08-19T20:08:56Z)
            // Display the date of the current news article in that TextView
            dateView.setText(articleDate.substring(0, 9));
        }

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
