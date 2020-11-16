package com.game.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MonsoonActivity extends AppCompatActivity {

    Spinner monsoonSpinner,sampleTypeSpinner;
    Button next;
    ArrayList<String> listMonsoon=new ArrayList<>();
    ArrayAdapter<String> adapterMonsoon;
    ArrayList<String> listSampleType=new ArrayList<>();
    ArrayAdapter<String> adapterSampleType;
    String phone="test",state="test",city="test",monsoon="test",sampleType="test";
    String latlon="test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monsoon);

        monsoonSpinner=findViewById(R.id.monsoonSpinner);
        sampleTypeSpinner=findViewById(R.id.sampleTypeSpinner);
        next=findViewById(R.id.next);

        phone=getIntent().getStringExtra("Phone");
        state=getIntent().getStringExtra("State");
        city=getIntent().getStringExtra("City");
        latlon= getIntent().getStringExtra("LatLon");

        String[] mons={"Pre Monsoon","Post Monsoon"};
        final String[] samType={"Soil","Rock","Water"};
        adapterMonsoon=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,mons);
        adapterSampleType=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,samType);
        for (int i=0;i<mons.length;i++){
            listMonsoon.add(mons[i]);
        }
        for (int j=0;j<samType.length;j++){
            listSampleType.add(samType[j]);
        }
        monsoonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monsoon=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sampleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sampleType=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        monsoonSpinner.setAdapter(adapterMonsoon);
        sampleTypeSpinner.setAdapter(adapterSampleType);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonsoonActivity.this,Main2Activity.class);
                intent.putExtra("Phone",phone);
                intent.putExtra("State",state);
                intent.putExtra("City",city);
                intent.putExtra("Monsoon",monsoon);
                intent.putExtra("SampleType",sampleType);
                intent.putExtra("LatLon", latlon);
                startActivity(intent);
            }
        });
    }
}
