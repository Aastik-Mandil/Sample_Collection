package com.game.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseListAdapter;

public class ViewSampleActivity extends AppCompatActivity {

    ListView listSample;
    String phone="test",sampleCollection="Sample Collection";
    //String state="test",city="test",monsoon="test",sampleType="test";
    //String latlon="test";
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sample);
        listSample=findViewById(R.id.listSample);

        phone=getIntent().getStringExtra("Phone");
        /*state=getIntent().getStringExtra("State");
        city=getIntent().getStringExtra("City");
        monsoon=getIntent().getStringExtra("Monsoon");
        sampleType=getIntent().getStringExtra("SampleType");
        latitute= Double.parseDouble(getIntent().getStringExtra("Latitute"));
        longitude= Double.parseDouble(Objects.requireNonNull(getIntent().getStringExtra("Longitude")));*/

        Query query = FirebaseDatabase.getInstance().getReference().child(sampleCollection).child(phone);
        FirebaseListOptions<SampleInfo> info=new FirebaseListOptions.Builder<SampleInfo>()
                .setLayout(R.layout.sample_layout)
                .setQuery(query,SampleInfo.class)
                .build();
        adapter = new FirebaseListAdapter(info) {
            @Override
            protected void populateView(View view, Object model, int position) {
                TextView newState = view.findViewById(R.id.newState);
                TextView newCity = view.findViewById(R.id.newCity);
                TextView newMonsoon = view.findViewById(R.id.newMonsoon);
                TextView newSampleType = view.findViewById(R.id.newSampleType);
                TextView newDesc = view.findViewById(R.id.newDesc);
//                TextView newLatLon = view.findViewById(R.id.newLatLon);
                SampleInfo info = (SampleInfo) model;
                newState.setText(getItem(position).toString());
                newCity.setText(getItem(position).toString());
                newMonsoon.setText(getItem(position).toString());
                newSampleType.setText(getItem(position).toString());
                newDesc.setText(getItem(position).toString());
                //newLatLon.setText("("+getItem(position).toString()+","+getItem(position).toString()+")");
            }
        };
        listSample.setAdapter(adapter);
    }
}
