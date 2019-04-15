package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsListView extends BaseAdapter {
    Context ctx;
    LayoutInflater flater;
    private List<News> ls = new ArrayList<News>();
    public void setData(List<News> data) {
        ls.addAll(data);
        notifyDataSetChanged();
    }
    NewsListView(Context c)
    {
        ctx = c;
        flater = (LayoutInflater)(ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = flater.inflate(R.layout.list_layout,null);

        TextView t1 = (TextView)(view.findViewById(R.id.t1));
        final News tt = (News) getItem(position);

        t1.setText(tt.getHeading());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Heading is : " + tt.getHeading(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, ViewSavedNewsDetailsActivity.class);
                i.putExtra("data", tt);
                ctx.startActivity(i);
            }
        });
        return view;
    }
}
