package com.example.nkssa.pnu_entrance_system;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class bgscan extends AppCompatActivity {

    EditText id, password;
    String Id, Password,sname,sid;
    Context ctx=this;
    String ID=null, NAME=null, PASSWORD=null, EMAIL=null, ADDRESS=null, ROLE=null, VALID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Id = getIntent().getStringExtra("mac"); // Id is the mac
        Password = "";
        sname = getIntent().getStringExtra("r_name");
        sid = getIntent().getStringExtra("r_id");


        BackGround b = new BackGround();
        b.execute(Id, Password);
    }


    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0]; // mac
            String password = params[1]; //nothing
            String data="";
            int tmp;

            try {
                URL url = new URL("http://192.168.8.100/ES/bluetooth.php");
                String urlParams = "r_id="+id+"&r_password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                ID = user_data.getString("r_id");
                NAME = user_data.getString("r_name");
                PASSWORD = user_data.getString("r_password");
                EMAIL = user_data.getString("r_email");
                ADDRESS = user_data.getString("r_address");
                ROLE= user_data.getString("r_role");
                VALID = user_data.getString("valid");
            } catch (JSONException e) {
                e.printStackTrace();

            }
            if(VALID.equals("not_valid")){

                s="The user is not validated yet";
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, securityGuard.class);
                i.putExtra("r_id",sid);
                i.putExtra("r_name",sname);
                startActivity(i);
            }

            if(VALID.equals("valid")) {
            if(ROLE.equals("resident") ||ROLE.equals("dependent") ){
                //  s="resident or dep";
                //  Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, auth.class);
                i.putExtra("r_id", ID);
                i.putExtra("r_name", NAME);
                i.putExtra("r_password", PASSWORD);
                i.putExtra("r_email", EMAIL);
                i.putExtra("r_address", ADDRESS);
                i.putExtra("id",sid);
                i.putExtra("name",sname);


                startActivity(i);}
            if (ROLE.equals("driver")){
                //s="driver";
                //Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, auth.class);
                i.putExtra("r_id", ID);
                i.putExtra("r_name", NAME);
                i.putExtra("r_password", PASSWORD);
                i.putExtra("r_email", EMAIL);
                i.putExtra("r_address", ADDRESS);
                i.putExtra("id",sid);
                i.putExtra("name",sname);


                startActivity(i);
            }

            if (ROLE.equals("nothing")){
                s="user does not exist";
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, securityGuard.class);
                i.putExtra("r_id",sid);
                i.putExtra("r_name",sname);

                startActivity(i);
            }}

    }}

}
