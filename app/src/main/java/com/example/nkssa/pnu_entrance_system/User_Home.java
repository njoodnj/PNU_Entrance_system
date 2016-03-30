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

public class User_Home extends Activity {

    String id , name,passwordU, email, address, welcome, pass ;
    TextView idTV ,nameTV, emailTV , addressTV, welcomeTV;
    final Context context = this;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);

        idTV = (TextView) findViewById(R.id.home_id);
        nameTV = (TextView) findViewById(R.id.home_name);
        emailTV = (TextView) findViewById(R.id.home_email);
        addressTV = (TextView) findViewById(R.id.home_address);
        welcomeTV = (TextView) findViewById(R.id.welcomename);

        id = getIntent().getStringExtra("r_id");
        name = getIntent().getStringExtra("r_name");
        email = getIntent().getStringExtra("r_email");
        address = getIntent().getStringExtra("r_address");
        welcome = getIntent().getStringExtra("r_name");
        pass = getIntent().getStringExtra("r_password");

        idTV.setText("ID No:" + id);
        nameTV.setText("Name: " + name);
        emailTV.setText("Email:" + email);
        addressTV.setText("Address:" + address);
        welcomeTV.setText(" " + name);

    }

    public void update_update (View v){
        Intent i = new Intent(ctx, update.class);
        i.putExtra("r_id", id);
        i.putExtra("r_password", pass);
        i.putExtra("r_address", address);
        i.putExtra("r_email",email);
        i.putExtra("r_name", name);
        i.putExtra("r_name", welcome);
        startActivity(i);}

    public  void logout(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int iid) {
                        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(id, "");
                        editor.putString(passwordU, "");
                        editor.putString(name, "");
                        editor.putString(email, "");
                        editor.putString(address, "");
                        editor.commit();
                        Intent intent = new Intent(User_Home.this, MainActivity.class);
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

    public  void visitor(View view){
        Intent i = new Intent(getApplicationContext(),visitor.class);
        i.putExtra("r_id",id);
        startActivity(i);}

}


