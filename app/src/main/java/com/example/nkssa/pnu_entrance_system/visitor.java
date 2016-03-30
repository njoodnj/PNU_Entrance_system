package com.example.nkssa.pnu_entrance_system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class visitor extends Activity implements View.OnClickListener {

    visitorphp biodata = new visitorphp();
    TableLayout visitor;


    Button buttonTambahBiodata;
    //ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();

    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        String extraid= getIntent().getStringExtra("r_id");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        visitor = (TableLayout) findViewById(R.id.tableBiodata);


        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderrId = new TextView(this);
        TextView viewHeaderName = new TextView(this);
        TextView viewHeaderRefnum = new TextView(this);
        TextView viewHeaderNumber = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        viewHeaderrId.setText("rID");
        viewHeaderName.setText("Name");
        viewHeaderRefnum.setText("Refnum");
        viewHeaderNumber.setText("Number");
        viewHeaderAction.setText("");

        viewHeaderrId.setPadding(5, 1, 5, 1);
        viewHeaderName.setPadding(5, 1, 5, 1);
        viewHeaderRefnum.setPadding(5, 1, 5, 1);
        viewHeaderNumber.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderrId);
        barisTabel.addView(viewHeaderName);
        barisTabel.addView(viewHeaderRefnum);
        barisTabel.addView(viewHeaderNumber);
        barisTabel.addView(viewHeaderAction);

        visitor.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata(extraid));

            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String name = jsonChildNode.optString("name");
                String refnum = jsonChildNode.optString("Refnum");
                String number = jsonChildNode.optString("Number");
                String rid = jsonChildNode.optString("rid");

                System.out.println("Name :" + name);
                System.out.println("refnum: "+ refnum);
                System.out.println("number" + number);
                System.out.println("rID :" + rid);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewrId = new TextView(this);
                viewrId.setText(rid);
                viewrId.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewrId);

                TextView viewName = new TextView(this);
                viewName.setText(name);
                viewName.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewName);


                TextView viewRefnum = new TextView(this);
                viewRefnum.setText(refnum);
                viewRefnum.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewRefnum);

                TextView viewNumber = new TextView(this);
                viewNumber.setText(number);
                viewNumber.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNumber);

                /*buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(rid));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));*/

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(refnum));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));

                visitor.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void visitor_cancel(View v){

        startActivity(new Intent(this,User_Home.class));
    }

    public  void invite1(View view){
        String extraid= getIntent().getStringExtra("r_id");
        Intent i = new Intent(getApplicationContext(),invite.class);
        i.putExtra("r_id",extraid);
        startActivity(i);
    }
    public void onClick(View view) {

            /*if (view.getId() == R.id.buttonTambahBiodata) {
                // Toast.makeText(visitor.this, "Button Tambah Data",
                // Toast.LENGTH_SHORT).show();

                tambahBiodata();

            } else {,
			/*
			 * Melakukan pengecekan pada data array, agar sesuai dengan index
			 * masing-masing button
			 */
        for (int i = 0; i < buttonDelete.size(); i++) {


            if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                // Toast.makeText(visitor.this, "Delete : " +
                // buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
                int refnum = buttonDelete.get(i).getId();
                deleteBiodata(refnum);

             /*(view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                // Toast.makeText(visitor.this, "Edit : " +
                // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
                int id = buttonEdit.get(i).getId();
                getDataByID(id);*/

            }


        }
    }



    public void deleteBiodata(int refnum) {
        biodata.deleteBiodata(refnum);


        finish();
        startActivity(getIntent());

    }

    public void getDataByID(int rid) {

        String nameEdit = null, refnumEdit = null, numberEdit = null ;
        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(biodata.getBiodataById(rid));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                nameEdit = jsonChildNode.optString("name");
                refnumEdit = jsonChildNode.optString("Refnum");
                numberEdit = jsonChildNode.optString("Number");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewrId = new TextView(this);
        viewrId.setText(String.valueOf(rid));
        viewrId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewrId);

        final EditText editName = new EditText(this);
        editName.setText(nameEdit);
        layoutInput.addView(editName);


        final EditText editRefnum = new EditText(this);
        editRefnum.setText(refnumEdit);
        layoutInput.addView(editRefnum);

        final EditText editNumber = new EditText(this);
        editNumber.setText(numberEdit);
        layoutInput.addView(editNumber);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setTitle("Update visitorphp");
        builderEditBiodata.setView(layoutInput);
        builderEditBiodata.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString();
                String refnum = editRefnum.getText().toString();
                String number = editNumber.getText().toString();

                System.out.println("Name : " + name + " Refnum : " + refnum + " Number : " + number);

                String laporan = biodata.updateBiodata(viewrId.getText().toString(), editName.getText().toString(),
                        editRefnum.getText().toString(), editNumber.getText().toString());

                Toast.makeText(visitor.this, laporan, Toast.LENGTH_SHORT).show();


                finish();
                startActivity(getIntent());
            }

        });

        builderEditBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderEditBiodata.show();

    }

    public void tambahBiodata() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editName = new EditText(this);
        editName.setHint("Name");
        layoutInput.addView(editName);


        final  EditText editRefnum = new EditText(this);
        editRefnum.setHint("Refnum");
        layoutInput.addView(editRefnum);

        final EditText editNumber = new EditText(this);
        editNumber.setHint("Numebr");
        layoutInput.addView(editNumber);

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setTitle("Insert visitorphp");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString();
                String refnum = editRefnum.getText().toString();
                String number = editNumber.getText().toString();

                System.out.println("Name : " + name +  " Refnum : " + refnum + " Number : " + number);

                String laporan = biodata.inserBiodata(name, refnum, number);

                Toast.makeText(visitor.this, laporan, Toast.LENGTH_SHORT).show();

				/* restart acrtivity */
                finish();
                startActivity(getIntent());
            }


        });

        builderInsertBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertBiodata.show();
    }



}


