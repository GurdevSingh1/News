package com.example.news;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;


//Dialog notification
public class MyDialog extends Dialog {

    Activity a1;
    Dialog d;

    public MyDialog(Activity a) {
        super(a);
        this.a1 = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);

    }

}