package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailsActivity extends AppCompatActivity {
    MySqliteHanlder db;
    News n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        n = (News)getIntent().getExtras().get("data");


        TextView headtv = findViewById(R.id.textView);
        TextView auttv = findViewById(R.id.textView2);
        TextView datetv = findViewById(R.id.textView3);
        TextView sniptv = findViewById(R.id.textView4);
        TextView urltv = findViewById(R.id.textView5);
        db = new MySqliteHanlder(NewsDetailsActivity.this);
        headtv.setText(n.getHeading());
        auttv.setText(n.getAuthor());
        datetv.setText(n.getPubDate());
        sniptv.setText(n.getSnippet());
        urltv.setText(n.getUrl());

        Button b = findViewById(R.id.button2);

        //Button Click Code
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addNews(n);
                Toast.makeText(NewsDetailsActivity.this,"SAVED",Toast.LENGTH_LONG).show();

                Intent i = new Intent(NewsDetailsActivity.this, ViewSavedNews.class);
                startActivity(i);
            }
        });
    }
}
