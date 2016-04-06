package com.example.nkssa.pnu_entrance_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends Activity {

    EditText id, password;
    String Id, Password;
    Context ctx=this;
    String ID=null, NAME=null, PASSWORD=null, EMAIL=null, ADDRESS=null, ROLE=null, VALID=null;


    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        id = (EditText) findViewById(R.id.NationalID);
        password = (EditText) findViewById(R.id.passwordHP);
    }

    public void register_register(View v){
        startActivity(new Intent(this,mainReg.class));
    }
    public void contactus(View v){

        startActivity(new Intent(this,contactus.class));
    }

    public void main_login(View v){
        Id = id.getText().toString();
        Password = password.getText().toString();

        // Toast.makeText(ctx, Id, Toast.LENGTH_LONG).show();
        if( id.getText().toString().length() == 0 ){
            id.setError("ID is required!");}
        if( password.getText().toString().length() == 0 ){
            password.setError( "Password is required!" );}
        else{
            BackGround b = new BackGround();
            b.execute(Id, Password);}

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://192.168.1.108/ES/login.php");
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
                VALID= user_data.getString("valid");
            } catch (JSONException e) {
                e.printStackTrace();

            }
            if(VALID.equals("not_valid")){

                s="You are not validated yet";

                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, MainActivity.class);

                startActivity(i);
            }

            if(VALID.equals("valid")) {
                if(ROLE.equals("resident") ||ROLE.equals("dependent") ){
                    Intent i = new Intent(ctx, User_Home.class);
                    i.putExtra("r_id", ID);
                    i.putExtra("r_name", NAME);
                    i.putExtra("r_password", PASSWORD);
                    i.putExtra("r_email", EMAIL);
                    i.putExtra("r_address", ADDRESS);
                    startActivity(i);}
                if (ROLE.equals("driver")){
                    Intent i = new Intent(ctx, Driver_Home.class);
                    i.putExtra("r_id", ID);
                    i.putExtra("r_name", NAME);
                    startActivity(i);
                }
                if (ROLE.equals("security")){
                    Intent i = new Intent(ctx, securityGuard.class);
                    i.putExtra("r_id", ID);
                    i.putExtra("r_name", NAME);
                    startActivity(i);
                }

                if (ROLE.equals("nothing")){
                    s="ID or password is incorrect";

                    Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ctx, MainActivity.class);

                    startActivity(i);
                }}
        }
    }
}

