package com.example.nkssa.pnu_entrance_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Driver_Home extends Activity {


    String rid, dname, dpassword, dwelcome;
    TextView didTV, dnameTV, dwelcomeTV;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__home);


        didTV = (TextView) findViewById(R.id.driver_id);
        dnameTV = (TextView) findViewById(R.id.driver_name);
        dwelcomeTV = (TextView) findViewById(R.id.dwelcomename);

        rid = getIntent().getStringExtra("r_id");
        dname = getIntent().getStringExtra("r_name");
        dpassword = getIntent().getStringExtra("r_password");
        dwelcome = getIntent().getStringExtra("r_name");


        didTV.setText("ID No:" + rid);
        dnameTV.setText("Name: " + dname);
        dwelcomeTV.setText(" " + dname);


    }

    public void logout(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int iid) {
                        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(rid, "");
                        editor.putString(dpassword, "");
                        editor.putString(dname, "");
                        editor.commit();
                        Intent intent = new Intent(Driver_Home.this, MainActivity.class);
                        startActivity(intent);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int iid) {

                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

    }
}





