package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ViewSavedNews extends AppCompatActivity {
    MySqliteHanlder db;
    private ListView ls;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_news);
        db = new MySqliteHanlder(this);

        ArrayList<News> ar  = db.getAllNews();

        ls = (ListView) findViewById(R.id.newsls);
        ls.setEmptyView(findViewById(R.id.textView7));

        NewsListView adapter = new NewsListView(ViewSavedNews.this);
        adapter.setData(ar);

        ls.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainNewsActivity.class);
        startActivity(i);
    }
}
