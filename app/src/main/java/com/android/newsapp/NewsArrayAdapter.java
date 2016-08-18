package com.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasik on 8/14/2016.
 */

public class NewsArrayAdapter extends ArrayAdapter<NewsItem> {

    public NewsArrayAdapter(Context context, ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.newsitem, parent, false);
        }

        NewsItem newsItem = getItem(position);

        TextView newsName = (TextView) listItemView.findViewById(R.id.articleTitle);
        newsName.setText(newsItem.getArticleTitle());

        TextView newsUrl = (TextView) listItemView.findViewById(R.id.articleUrl);
        newsUrl.setText(newsItem.getArticleUrl());

        TextView newsId = (TextView) listItemView.findViewById(R.id.articleID);
        newsId.setText(newsItem.getSectionName());

        TextView author = (TextView) listItemView.findViewById(R.id.articleAuthor);
        author.setText(newsItem.getArticleAuthor());

        return listItemView;
    }

}
