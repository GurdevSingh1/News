package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ViewSavedNewsDetailsActivity extends AppCompatActivity {

    MySqliteHanlder db;
    News n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_news_details);

        n= (News)getIntent().getExtras().get("data");

        db = new MySqliteHanlder(ViewSavedNewsDetailsActivity.this);

        TextView headtv = findViewById(R.id.tt1);
        TextView auttv = findViewById(R.id.tt2);
        TextView datetv = findViewById(R.id.tt3);
        TextView sniptv = findViewById(R.id.tt4);
        TextView urltv = findViewById(R.id.tt5);

        headtv.setText(n.getHeading());
        auttv.setText(n.getAuthor());
        datetv.setText(n.getPubDate());
        sniptv.setText(n.getSnippet());
        urltv.setText(n.getUrl());

        Button b = findViewById(R.id.b23);

        //Button Click Code
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteNews(n);

                Snackbar snackbar = Snackbar
                        .make( v, "Deleted Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(ViewSavedNewsDetailsActivity.this, ViewSavedNews.class);
                        startActivity(i);
                    }
                }, 2 * 1000);

            }
        });
    }
}
