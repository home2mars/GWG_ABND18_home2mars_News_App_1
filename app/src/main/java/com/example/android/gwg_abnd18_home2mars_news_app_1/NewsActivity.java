package com.example.android.gwg_abnd18_home2mars_news_app_1;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>> {

    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * URL for news articles data from The Guardian Newspaper dataset
     * https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906
     */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?show-fields=byline&api-key=" + BuildConfig.ApiKey;

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /** Adapter for the list of news articles */
    private NewsArticleAdapter mAdapter;

    private ListView newsListView;
    private RelativeLayout emptyLayout;
    private RelativeLayout noNewsLayout;
    private RelativeLayout noNetworkLayout;
    private RelativeLayout loadingLayout;

    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Find references to the in the layout
        newsListView = (ListView) findViewById(R.id.news_list);
        loadingLayout = (RelativeLayout) findViewById(R.id.news_loading_layout);
        noNetworkLayout = (RelativeLayout) findViewById(R.id.news_no_network_layout);
        emptyLayout = (RelativeLayout) findViewById(R.id.empty_layout);
        noNewsLayout = (RelativeLayout) findViewById(R.id.news_no_news_layout);

        newsListView.setEmptyView(emptyLayout);

        // Create a new adapter that takes an empty list of news articles as input
        mAdapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news article.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news article that was clicked on
                NewsArticle currentArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getNewsUrl());

                // Create a new intent to view the news article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
            noNewsLayout.setVisibility(View.GONE);

            // Update empty state with no connection error message
            noNetworkLayout.setVisibility(View.VISIBLE);
        }// Get a reference to the ConnectivityManager to check state of network connectivity
        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
            noNewsLayout.setVisibility(View.GONE);

            // Update empty state with no connection error message
            noNetworkLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {
        // Hide loading indicator because the data has been loaded
        loadingLayout.setVisibility(View.GONE);

        // Clear the adapter of previous news articles data
        mAdapter.clear();

        // If there is a valid list of {@link NewsArticle}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsArticles != null && !newsArticles.isEmpty()) {
            mAdapter.addAll(newsArticles);
        } else {
            networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                noNewsLayout.setVisibility(View.VISIBLE);
            } else {
                noNetworkLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
