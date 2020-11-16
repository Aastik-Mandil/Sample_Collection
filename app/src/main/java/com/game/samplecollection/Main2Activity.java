package com.game.samplecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main2Activity extends AppCompatActivity {
    EditText edtName,  edtCity, edtState, edtMonsoon, edtSampleType, edtLatLon, edtPh,edtEc,edtTds,edtArsenic,edtNitrate,edtFluoride,edtSulphate,edtChloride,edtHardness,edtAlkalinity;//, edtPrice
    Button btnChoose, btnAdd, btnList;
    ImageView imageView;
    String phone="test",state="test",city="test",monsoon="test",sampleType="test",ph="test",ec="test",tds="test",arsenic="test",nitrate="test",fluoride="test",sulphate="test",chloride="test",
            hardness="test",alkalinity="test",sampleCollection="Sample Collection";
    String latlon="test";
    public static SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
        phone=getIntent().getStringExtra("Phone");
        state=getIntent().getStringExtra("State");
        city=getIntent().getStringExtra("City");
        monsoon=getIntent().getStringExtra("Monsoon");
        sampleType=getIntent().getStringExtra("SampleType");
        latlon= getIntent().getStringExtra("LatLon");

//        phoneText.setText(phone);
        if(!state.equals("")) { edtState.setText(state);}
        if(!state.equals("")) { edtCity.setText(city);}
        if(!state.equals("")) { edtMonsoon.setText(monsoon);}
        if(!state.equals("")) { edtSampleType.setText(sampleType);}
        if(!state.equals("")) { edtLatLon.setText(latlon);}


        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOB, city VARCHAR, state VARCHAR, monsoon VARCHAR, sampleType VARCHAR, latLon VARCHAR, ph VARCHAR, ec VARCHAR, tds VARCHAR, arsenic VARCHAR, nitrate VARCHAR, fluoride VARCHAR, sulphate VARCHAR, chloride VARCHAR, hardness VARCHAR, alkalinity VARCHAR)");
// price VARCHAR,

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sqLiteHelper.insertData(edtName.getText().toString().trim(), imageViewToByte(imageView), edtCity.getText().toString().trim(),edtState.getText().toString().trim(),
                            edtMonsoon.getText().toString().trim(), edtSampleType.getText().toString().trim(), edtLatLon.getText().toString().trim(), edtPh.getText().toString().trim(),
                            edtEc.getText().toString().trim(), edtTds.getText().toString().trim(), edtArsenic.getText().toString().trim(), edtNitrate.getText().toString().trim(),
                            edtFluoride.getText().toString().trim(), edtSulphate.getText().toString().trim(), edtChloride.getText().toString().trim(), edtHardness.getText().toString().trim(),
                            edtAlkalinity.getText().toString().trim());//, edtPrice.getText().toString().trim()
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
//                    edtPrice.setText("");
                    edtCity.setText("");
                    edtState.setText("");
                    edtMonsoon.setText("");
                    edtSampleType.setText("");
                    edtLatLon.setText("");
                    edtPh.setText("");
                    edtEc.setText("");
                    edtTds.setText("");
                    edtArsenic.setText("");
                    edtNitrate.setText("");
                    edtFluoride.setText("");
                    edtSulphate.setText("");
                    edtChloride.setText("");
                    edtHardness.setText("");
                    edtAlkalinity.setText("");
                    imageView.setImageResource(R.drawable.insaan);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, FoodList.class);
                startActivity(intent);
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        edtName =  findViewById(R.id.edtName);//activity_main2
//        edtPrice =  findViewById(R.id.edtPrice);//activity_main2
        edtCity =  findViewById(R.id.edtCity);//activity_main2
        edtState =  findViewById(R.id.edtState);//activity_main2
        edtMonsoon =  findViewById(R.id.edtMonsoon);//activity_main2
        edtSampleType =  findViewById(R.id.edtSampleType);//activity_main2
        edtLatLon =  findViewById(R.id.edtLatLon);//activity_main2
        edtPh =  findViewById(R.id.edtPh);//activity_main2
        edtEc =  findViewById(R.id.edtEc);//activity_main2
        edtTds =  findViewById(R.id.edtTds);//activity_main2
        edtArsenic =  findViewById(R.id.edtArsenic);//activity_main2
        edtNitrate =  findViewById(R.id.edtNitrate);//activity_main2
        edtFluoride =  findViewById(R.id.edtFluoride);//activity_main2
        edtSulphate =  findViewById(R.id.edtSulphate);//activity_main2
        edtChloride =  findViewById(R.id.edtChloride);//activity_main2
        edtHardness =  findViewById(R.id.edtHardness);//activity_main2
        edtAlkalinity =  findViewById(R.id.edtAlkalinity);//activity_main2
        btnChoose =  findViewById(R.id.btnChoose);//activity_main2
        btnAdd =  findViewById(R.id.btnAdd);//activity_main2
        btnList =  findViewById(R.id.btnList);//activity_main2
        imageView =  findViewById(R.id.imageView);//activity_main2
    }
}