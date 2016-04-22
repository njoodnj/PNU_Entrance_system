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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class update extends Activity {

    Button u_submit;
    EditText emailaddress, password, repassword, currentpass;
    String email, pass, repass, cupass, id, passwordU, address, name, welcome;
    TextView ID,PASS;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update);
        emailaddress = (EditText) findViewById(R.id.emailaddress);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        currentpass = (EditText) findViewById(R.id.currentpass);
        ID = (TextView) findViewById(R.id.update_id);
        id = getIntent().getStringExtra("r_id");
        name = getIntent().getStringExtra("r_name");
        email = getIntent().getStringExtra("r_email");
        address = getIntent().getStringExtra("r_address");
        welcome = getIntent().getStringExtra("r_name");
        ID.setText(id);

    }

    public void update_cancel(View v){

        onBackPressed();
    }


    public void update_function(View v) {

        id = getIntent().getStringExtra("r_id");
        passwordU = getIntent().getStringExtra("r_password");
        email = emailaddress.getText().toString();
        pass = password.getText().toString();
        repass = repassword.getText().toString();
        cupass = currentpass.getText().toString();
        address = getIntent().getStringExtra("r_address");
        name = getIntent().getStringExtra("r_name");
        welcome = getIntent().getStringExtra("r_name");

        if (currentpass.getText().toString().length() == 0) {
            currentpass.setError("current password is required for updating!");
        }
        if (emailaddress.getText().toString().length() == 0) {
            email = getIntent().getStringExtra("r_email");
        }
        if (password.getText().toString().length() == 0) {
            pass = getIntent().getStringExtra("r_password");
        }
        if (password.getText().toString().length() != 0) {
            if (!pass.equals(repass)) {
                repassword.setError("Password dose not match");
            }
        }

        if (!cupass.equals(passwordU))
        {currentpass.setError("wrong current password");}


        else {
            BackGround backU = new BackGround();
            backU.execute(id, email, pass, cupass, address, name, welcome);
        }
    }

    class BackGround extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String email = params[1];
            String pass = params[2];
            String cupass = params[3];
            String address = params[4];
            String name = params[5];
            String welcome = params[6];
            String data = "";
            int tmp;


            try {
                URL url = new URL("http://192.168.8.100/ES/update.php");
                String urlParams = "id="+id+"&email="+email+"&pass="+pass+"&cupass="+cupass+"&address="+address+"&name="+name+"&welcome="+welcome;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.equals("")){
                s="Data updated successfully.";
            }
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();

            Intent i = new Intent(ctx, User_Home.class);
            i.putExtra("r_id", id);
            i.putExtra("r_email", email);
            i.putExtra("r_address", address);
            i.putExtra("r_name", name);
            i.putExtra("r_name", welcome);
            i.putExtra("r_password",pass);
            startActivity(i);

        }
    }
}