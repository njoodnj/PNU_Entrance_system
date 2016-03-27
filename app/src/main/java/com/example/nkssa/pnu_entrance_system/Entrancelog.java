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

public class Entrancelog extends Activity implements View.OnClickListener {

    Entrancephp biodata = new Entrancephp();
    TableLayout visitor;

    Button buttonTambahBiodata;
    ArrayList<Button> buttonReport = new ArrayList<Button>();


    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrancelog);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        visitor = (TableLayout) findViewById(R.id.tableBiodata);


        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderNo = new TextView(this);
        TextView viewHeaderrId = new TextView(this);
        TextView viewHeaderName = new TextView(this);
        TextView viewHeaderTime = new TextView(this);
        TextView viewHeaderAction = new TextView(this);


        viewHeaderNo.setText("No.");
        viewHeaderrId.setText("Id");
        viewHeaderName.setText("Name");
        viewHeaderTime.setText("Time");
        viewHeaderAction.setText("");

        viewHeaderNo.setPadding(5, 1, 5, 1);
        viewHeaderrId.setPadding(5, 1, 5, 1);
        viewHeaderName.setPadding(5, 1, 5, 1);
        viewHeaderTime.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderNo);
        barisTabel.addView(viewHeaderrId);
        barisTabel.addView(viewHeaderName);
        barisTabel.addView(viewHeaderTime);
        barisTabel.addView(viewHeaderAction);

        visitor.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String name = jsonChildNode.optString("Name");
                String time = jsonChildNode.optString("Time");
                String no = jsonChildNode.optString("no.");
                String Id = jsonChildNode.optString("Id");

                System.out.println("Name:" + name);
                System.out.println("Time: " + time);
                System.out.println("no." + no);
                System.out.println("Id :" + Id);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewNo = new TextView(this);
                viewNo.setText(no);
                viewNo.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNo);

                TextView viewId = new TextView(this);
                viewId.setText(Id);
                viewId.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewId);

                TextView viewName = new TextView(this);
                viewName.setText(name);
                viewName.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewName);


                TextView viewTime = new TextView(this);
                viewTime.setText(time);
                viewTime.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewTime);


                buttonReport.add(i, new Button(this));
                buttonReport.get(i).setId(Integer.parseInt(Id));
                buttonReport.get(i).setTag("Report");
                buttonReport.get(i).setText("Report");
                buttonReport.get(i).setOnClickListener(this);
                barisTabel.addView(buttonReport.get(i));


                visitor.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {

            /*if (view.getId() == R.id.buttonTambahBiodata) {
                // Toast.makeText(visitor.this, "Button Tambah Data",
                // Toast.LENGTH_SHORT).show();

                tambahBiodata();

            } else {
			/*
			 * Melakukan pengecekan pada data array, agar sesuai dengan index
			 * masing-masing button
			 */
        for (int i = 0; i < buttonReport.size(); i++) {


            if (view.getId() == buttonReport.get(i).getId() && view.getTag().toString().trim().equals("Report")) {
                // Toast.makeText(visitor.this, "Edit : " +
                // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
                int id = buttonReport.get(i).getId();
                getDataByID(id);

            }


        }


    }

    public void getDataByID(int id) {


        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editReason = new EditText(this);
        editReason.setHint("Reason:");
        layoutInput.addView(editReason);

        final EditText editPlate = new EditText(this);
        editPlate.setHint("Car plate:");
        layoutInput.addView(editPlate);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);

        builderEditBiodata.setTitle("Report User");
        builderEditBiodata.setCancelable(false);
        builderEditBiodata.setView(layoutInput);
        builderEditBiodata.setPositiveButton("Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int iid) {
                String reason = editReason.getText().toString();
                String plate = editPlate.getText().toString();

                System.out.println("Reason : " + reason + "Plate : " + plate);

                String laporan = biodata.updateBiodata(viewId.getText().toString(), editReason.getText().toString(),
                        editPlate.getText().toString());

                Toast.makeText(Entrancelog.this, laporan, Toast.LENGTH_SHORT).show();


                finish();
                startActivity(getIntent());
            }

        });

        builderEditBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int iid) {

                Intent intent = new Intent(Entrancelog.this, Entrancelog.class);
                startActivity(intent);

            }
        });


        AlertDialog alertDialog = builderEditBiodata.create();


        alertDialog.show();

    }

}

