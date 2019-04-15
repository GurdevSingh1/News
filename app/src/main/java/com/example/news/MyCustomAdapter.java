package com.example.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

//FragmentAdapter
public class MyCustomAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<News> ar1;
    public MyCustomAdapter(FragmentManager fm, int NumOfTabs, ArrayList<News> ar
    ) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        ar1 = ar;
    }

    @Override
    public Fragment getItem(int position) {

        System.out.println("2");
        switch (position) {
            case 0:
                FirstFragment tab1 = new FirstFragment();
                Bundle args = new Bundle();
                args.putSerializable("k1",ar1);
                tab1.setArguments(args);
                return tab1;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}