package com.game.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperationActivity extends AppCompatActivity {

    Button addButton, viewButton;
    String phone="test",sampleCollection="Sample Collection";
    //String state="test",city="test",monsoon="test",sampleType="test";
    //String latitute=0,longitude=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        addButton=findViewById(R.id.addButton);
        viewButton=findViewById(R.id.viewButton);

        phone=getIntent().getStringExtra("Phone");
        /*state=getIntent().getStringExtra("State");
        city=getIntent().getStringExtra("City");
        monsoon=getIntent().getStringExtra("Monsoon");
        sampleType=getIntent().getStringExtra("SampleType");
        latitute= Double.parseDouble(getIntent().getStringExtra("Latitute"));
        longitude= Double.parseDouble(getIntent().getStringExtra("Longitude"));*/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OperationActivity.this,PlaceActivity.class);
                intent.putExtra("Phone",phone);
                startActivity(intent);
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OperationActivity.this,OnlineFetchActivity.class);
                startActivity(intent);
            }
        });
    }
}
