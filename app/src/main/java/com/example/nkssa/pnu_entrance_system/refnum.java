package com.example.nkssa.pnu_entrance_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class refnum extends Activity {

    String rid, name, Refnum, Number, Err;
    TextView ridTV, nameTV, RefnumTV, NumberTV, err;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refnum);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(refnum.this, securityGuard.class);
                refnum.this.startActivity(mainIntent);
                refnum.this.finish();
            }
        }, 10000);

        ridTV = (TextView) findViewById(R.id.rid);
        nameTV = (TextView) findViewById(R.id.name);
        RefnumTV = (TextView) findViewById(R.id.ref);
        NumberTV = (TextView) findViewById(R.id.number);
        err = (TextView) findViewById(R.id.err);

        rid = getIntent().getStringExtra("rid");
        name = getIntent().getStringExtra("name");
        Refnum = getIntent().getStringExtra("Refnum");
        Number = getIntent().getStringExtra("Number");
        Err = getIntent().getStringExtra("err");

        nameTV.setText("rid " + rid);
        nameTV.setText("name " + name);
        RefnumTV.setText("refrence number is: " + Refnum);
        NumberTV.setText("number of visits: " + Number);
        err.setText(Err);
    }





}


