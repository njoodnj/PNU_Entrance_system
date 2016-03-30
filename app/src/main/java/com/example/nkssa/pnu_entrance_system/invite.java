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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class invite extends Activity {

    EditText name, Number ;
    TextView rid;
    String Rid, Name, NUmber;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        rid = (TextView) findViewById(R.id.rid);
        name = (EditText) findViewById(R.id.register_name);
        Number = (EditText) findViewById(R.id.register_number);

        String extraid= getIntent().getStringExtra("r_id");

        rid.setText(extraid);

    }

    public void invite_cancel(View v){

        startActivity(new Intent(this,visitor.class));
    }


    public void register_register(View v){
        Rid = rid.getText().toString();
        Name = name.getText().toString();
        NUmber = Number.getText().toString();

        if( rid.toString().length() == 0 ){
            rid.setError( "ID is required!" );}
        if( name.getText().toString().length() == 0 ){
            name.setError( "Name is required!" );}
        if( Number.getText().toString().length() == 0 ){
            Number.setError( "number of visits is required!" );}
        else {
            BackGround b = new BackGround();
            b.execute(Rid,Name, NUmber);
            startActivity(new Intent(this,visitor.class));}
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String rid = params[0];
            String name = params[1];
            String Number = params[2];

            String data="";
            int tmp;

            try {
                URL url = new URL("http://192.168.8.106/ES/invite.php");
                String urlParams = "rid="+rid+"&name="+name+"&Number="+Number;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close()
                ;
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
            if(s.equals("")){
                s="Data saved successfully.";
            }
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        }
    }

}

