package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


//Custom List View Adapter
public class CustomListView extends BaseAdapter {
    Context ctx;
    LayoutInflater flater;
    private List<News> ls = new ArrayList<News>();
    ProgressBar pb;

    // Data will be set inside the arraylist
    public void setData(List<News> data) {
        ls.addAll(data);
        notifyDataSetChanged();
    }
    //Constructor
    CustomListView(Context c)
    {
        ctx = c;
        flater = (LayoutInflater)(ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    //returns the item present at the specified position
    public Object getItem(int position) {
        return ls.get(position);
    }
    @Override
    //returns the Id of the item
    //WE will use the same index numbers as id of our item
    public long getItemId(int position) {
        return position;
    }
    @Override
    //This method is called for every item
    //view contains the reference of the current item in design

    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = flater.inflate(R.layout.list_layout,null);
        TextView t1 = (TextView)(view.findViewById(R.id.t1));
        final News tt = (News) getItem(position);

        t1.setText(tt.getHeading());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, NewsDetailsActivity.class);
                i.putExtra("data",tt);
                ctx.startActivity(i);
            }
        });
        return view;
    }
}

