package com.game.samplecollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import java.util.HashMap;

public class NewSampleActivity extends AppCompatActivity {

    ImageView imageview;//
    ImageButton imagebutton;
    TextView phoneText,stateText,cityText,monsoonText,sampleTypeText,LatLon;
    Button upload,viewSample;
    EditText desc;
    String phone="test",state="test",city="test",monsoon="test",sampleType="test",sampleCollection="Sample Collection";
    String latlon="test";
    FirebaseDatabase database;
    DatabaseReference ref;
    StorageReference storageReference;
    SampleInfo info;
    Uri ImageLocation;
    int Gallery_intent=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sample);

//        imageview=findViewById(R.id.imageview);
        imagebutton=findViewById(R.id.imagebutton);
        phoneText=findViewById(R.id.phoneText);
        stateText=findViewById(R.id.stateText);
        cityText=findViewById(R.id.cityText);
        monsoonText=findViewById(R.id.monsoonText);
        sampleTypeText=findViewById(R.id.sampleTypeText);
        LatLon=findViewById(R.id.latlon);
        desc=findViewById(R.id.desc);
        upload=findViewById(R.id.upload);
        viewSample=findViewById(R.id.viewSample);

        database=FirebaseDatabase.getInstance();
        info=new SampleInfo();

        phone=getIntent().getStringExtra("Phone");
        state=getIntent().getStringExtra("State");
        city=getIntent().getStringExtra("City");
        monsoon=getIntent().getStringExtra("Monsoon");
        sampleType=getIntent().getStringExtra("SampleType");
        latlon= getIntent().getStringExtra("LatLon");

        phoneText.setText(phone);
        stateText.setText(state);
        cityText.setText(city);
        monsoonText.setText(monsoon);
        sampleTypeText.setText(sampleType);
        LatLon.setText(latlon);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(NewSampleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(NewSampleActivity.this, Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED ){
                    Log.d("Checking", "Permission granted");
                    uploadInfo();//
                }
                else{
                    ActivityCompat.requestPermissions(NewSampleActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET},9);
                }
            }
        });
        viewSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSampleActivity.this,ViewSampleActivity.class);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // acknowledging the permission
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
            uploadInfo();
        }
        else{
            Log.d("Checking", "Please provide permission ");
            Toast.makeText(this, "Please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadInfo(){
        final String State=state;
        final String City=city;
        final String Monsoon=monsoon;
        final String SampleType=sampleType;
        final String Desc=desc.getText().toString().trim();
        //ref.child(sampleCollection).child(phone).push().setValue(info);//.child(state).child(city).child(monsoon).child(sampleType)
        if(!Desc.equals("")){
            ref.addValueEventListener(new ValueEventListener() {//
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    info=new SampleInfo(State,City,Monsoon,SampleType,Desc,latlon);
                    ref.child(sampleCollection).child(phone).push().setValue(info);
                    Log.d("Checking", "Info inserted successfully");
                    Toast.makeText(NewSampleActivity.this, "Info inserted successfully", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Log.d("Checking", "Fill info");
            Toast.makeText(NewSampleActivity.this, "Fill info", Toast.LENGTH_SHORT).show();
        }
    }
//    public void btnimage(View view) {
//        Intent intent=new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent,Gallery_intent);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_intent && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            Uri fileuri = data.getData();
            imageview.setImageURI(fileuri);
            storageReference= FirebaseStorage.getInstance().getReference().child("Profile"+fileuri.getLastPathSegment());
            storageReference.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                            HashMap<String,String> hashMap=new HashMap<>();
                            ImageLocation=uri;
                            Log.d("Checking", "image inserted successfully");
                            Toast.makeText(NewSampleActivity.this, "image inserted successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewSampleActivity.this,"Not Uploaded......",Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else{
            Log.d("Checking", "image do not inserted");
            Toast.makeText(NewSampleActivity.this, "image do not inserted", Toast.LENGTH_SHORT).show();
        }
    }
}