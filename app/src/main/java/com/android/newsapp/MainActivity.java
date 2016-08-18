package com.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String newsJson = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    private ListView newsList;
    private ProgressBar loading;
    private ArrayList<NewsItem> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = (ProgressBar) findViewById(R.id.loadingNews);

        GetNewsItems task = new GetNewsItems();
        task.execute(newsJson);

        newsList = (ListView) findViewById(R.id.newsList);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String url = newsItems.get(position).getArticleUrl();
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(Uri.parse(url));
                startActivity(browser);
            }
        });
    }

    public class GetNewsItems extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            Integer result = 0;
            HttpURLConnection urlConnection;

            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                // 200 = HTTP OK
                if (statusCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    newsItemArrayList(response.toString());
                    result = 1; //Success
                } else {
                    result = 0; //Failed to fetch data
                }

            } catch (Exception e) {
                Log.d("JSON Test", e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {

            loading.setVisibility(View.GONE);

            if (newsItems != null) {
                if (result == 1) {
                    NewsArrayAdapter adapter = new NewsArrayAdapter(MainActivity.this, newsItems);
                    newsList.setAdapter(adapter);
                } else {
                    Log.d("Async Task", "Failed to get data");
                }
            }
        }


        private void newsItemArrayList(String json_url) {

            newsItems = new ArrayList<>();

            try {
                JSONObject response = new JSONObject(json_url);
                JSONObject newsResponse = response.getJSONObject("response");
                JSONArray newsArray = newsResponse.getJSONArray("results");

                Log.d("JsonTask", "Posts size " + newsArray.length());

                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject news = newsArray.getJSONObject(i);

                    NewsItem newsItem = new NewsItem();

                    newsItem.setArticleTitle(news.getString("webTitle"));
                    newsItem.setArticleUrl(news.getString("webUrl"));
                    newsItem.setSectionName(news.getString("sectionName"));

                    JSONArray tagsArray = news.getJSONArray("tags");
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject author = tagsArray.getJSONObject(j);
                        newsItem.setArticleAuthor(author.getString("webTitle"));
                    }

                    newsItems.add(newsItem);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
