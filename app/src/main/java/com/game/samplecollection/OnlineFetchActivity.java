package com.game.samplecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineFetchActivity extends AppCompatActivity {

    EditText editId;
    Button fetch;
    TextView tvbottleId,tvcity,tvstate,tvmonsoon,tvsampleType,tvlatLon,tvph,tvec,tvtds,tvarsenic,tvnitrate,tvfluoride,tvsulphate,tvchloride,tvhardness,tvalkalinity;

    DatabaseReference rootRef, demoRef;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_fetch);
        editId = findViewById(R.id.searchId);
        fetch = findViewById(R.id.btnFetch);
        tvbottleId = findViewById(R.id.tvbottleId);
        tvcity = findViewById(R.id.tvCity);
        tvstate = findViewById(R.id.tvState);
        tvmonsoon = findViewById(R.id.tvMonsoon);
        tvsampleType = findViewById(R.id.tvSampleType);
        tvlatLon = findViewById(R.id.tvlatLon);
        tvph = findViewById(R.id.tvPh);
        tvec = findViewById(R.id.tvEc);
        tvtds = findViewById(R.id.tvTds);
        tvarsenic = findViewById(R.id.tvArsenic);
        tvnitrate = findViewById(R.id.tvNitrate);
        tvfluoride = findViewById(R.id.tvFluoride);
        tvsulphate = findViewById(R.id.tvSulphate);
        tvchloride = findViewById(R.id.tvChloride);
        tvhardness = findViewById(R.id.tvHardness);
        tvalkalinity = findViewById(R.id.tvAlkalinity);
        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        demoRef = rootRef.child("demo");

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String key = editId.getText().toString();
                demoRef.child(key).child("city").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String city = dataSnapshot.getValue(String.class);
                        tvcity.setText(city);
                        tvbottleId.setText(key);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("state").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String state = dataSnapshot.getValue(String.class);
                        tvstate.setText(state);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("monsoon").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String monsoon = dataSnapshot.getValue(String.class);
                        tvmonsoon.setText(monsoon);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("sample_type").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String sampletype = dataSnapshot.getValue(String.class);
                        tvsampleType.setText(sampletype);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("lat_lon").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String latLon = dataSnapshot.getValue(String.class);
                        tvlatLon.setText(latLon);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("ph").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ph = dataSnapshot.getValue(String.class);
                        tvph.setText(ph);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("ec").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ec = dataSnapshot.getValue(String.class);
                        tvec.setText(ec);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("tds").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String tds = dataSnapshot.getValue(String.class);
                        tvtds.setText(tds);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("arsenic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String arsenic = dataSnapshot.getValue(String.class);
                        tvarsenic.setText(arsenic);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("nitrate").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nitrate = dataSnapshot.getValue(String.class);
                        tvnitrate.setText(nitrate);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("fluoride").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String fluoride = dataSnapshot.getValue(String.class);
                        tvfluoride.setText(fluoride);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("sulphate").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String sulphate = dataSnapshot.getValue(String.class);
                        tvsulphate.setText(sulphate);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("chloride").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String chloride = dataSnapshot.getValue(String.class);
                        tvchloride.setText(chloride);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("hardness").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String hardness = dataSnapshot.getValue(String.class);
                        tvhardness.setText(hardness);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                demoRef.child(key).child("alkalinity").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String alkalinity = dataSnapshot.getValue(String.class);
                        tvalkalinity.setText(alkalinity);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
