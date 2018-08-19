package com.example.android.gwg_abnd18_home2mars_news_app_1;


/**
 * An {@link NewsArticle} object contains information related to a single news article.
 */
public class NewsArticle {

    /** Title of the news article */
    private String mNewsTitle;

    /** Section of the news article */
    private String mNewsSection;

    /** Byline (authors) of the news article */
    private String mNewsByline;

    /** Date of the news article */
    private String mNewsDate;

    /** Website URL of the news article */
    private String mNewsUrl;

    /**
     * Constructs a new {@link NewsArticle} object.
     *
     * @param title is the title of the news article
     * @param section is the section where the news article is located within the newspaper
     * @param byline is the author(s) of the news article
     * @param date is the date when the news article was published on the web
     * @param url is the website URL to find the rest of the news article
     */
    public NewsArticle(String title, String section, String byline, String date, String url) {
        mNewsTitle = title;
        mNewsSection = section;
        mNewsByline = byline;
        mNewsDate = date;
        mNewsUrl = url;
    }

    /**
     * Returns the title of the news article.
     */
    public String getTitle() {
        return mNewsTitle;
    }

    /**
     * Returns the section of the news article.
     */
    public String getSection() {
        return mNewsSection;
    }

    /**
     * Returns the byline of the news article.
     */
    public String getByline() {
        return mNewsByline;
    }

    /**
     * Returns the date of the news article.
     */
    public String getDate() {
        return mNewsDate;
    }

    /**
     * Returns the website URL to find the rest of the news article
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }
}
