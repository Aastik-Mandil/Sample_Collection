package com.game.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity {

    EditText cityText;
    Spinner stateSpinner;
    Button locButton;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    String state="test",city="test";
    String phone="test";
//    String latitute="test",longitude="test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        cityText=findViewById(R.id.cityText);
        stateSpinner=findViewById(R.id.stateSpinner);
        locButton=findViewById(R.id.locButton);
        phone=getIntent().getStringExtra("Phone");
        String[] str={"Andaman and Nicobar Islands", "Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa",
                      "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
                      "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttrakhand", "West Bengal"};
        for (int i=0;i<str.length;i++){
            list.add(str[i]);
        }
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state=adapterView.getItemAtPosition(i).toString();
                city=cityText.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stateSpinner.setAdapter(adapter);
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlaceActivity.this,LocationActivity.class);
                intent.putExtra("Phone",phone);
                intent.putExtra("State",state);
                intent.putExtra("City",city);
                startActivity(intent);
            }
        });
    }
}
