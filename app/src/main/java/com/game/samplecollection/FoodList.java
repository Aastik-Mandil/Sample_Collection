package com.game.samplecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.game.samplecollection.Main2Activity.imageViewToByte;
import static com.game.samplecollection.Main2Activity.sqLiteHelper;

interface MainActivityDataTaskNotification {
    void notifyMainActivity(String connStatus, String connInstruction);
}

public class FoodList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Food> list;
    FoodListAdapter adapter = null;
    DatabaseReference rootRef, demoRef;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        gridView =  findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new FoodListAdapter(this, R.layout.food_items, list);
        gridView.setAdapter(adapter);
        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        demoRef = rootRef.child("demo");

        // get all data from sqlite
//        try {
            Cursor cursor = Main2Activity.sqLiteHelper.getData("SELECT * FROM FOOD");
            list.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);//error
                String name = cursor.getString(1);
//            String price = cursor.getString(2);
                byte[] image = cursor.getBlob(2);
                String city = cursor.getString(3);
                String state = cursor.getString(4);
                String monsoon = cursor.getString(5);
                String sampleType = cursor.getString(6);
                String latLon = cursor.getString(7);
                String ph = cursor.getString(8);
                String ec = cursor.getString(9);
                String tds = cursor.getString(10);
                String arsenic = cursor.getString(11);
                String nitrate = cursor.getString(12);
                String fluoride = cursor.getString(13);
                String sulphate = cursor.getString(14);
                String chloride = cursor.getString(15);
                String hardness = cursor.getString(16);
                String alkalinity = cursor.getString(17);
                list.add(new Food(name, image, id, city, state, monsoon, sampleType, latLon, ph, ec, tds, arsenic, nitrate, fluoride, sulphate, chloride, hardness, alkalinity));//,price
            }
            adapter.notifyDataSetChanged();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"View", "Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(FoodList.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0) {
                            //view
                            Cursor c = sqLiteHelper.getData("SELECT id FROM FOOD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogView(FoodList.this,arrID.get(position));
                        }
                        else if (item == 1) {
                            // update
                            Cursor c = sqLiteHelper.getData("SELECT id FROM FOOD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(FoodList.this, arrID.get(position));
                        }
                        else {
                            // delete
                            Cursor c = sqLiteHelper.getData("SELECT id FROM FOOD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void showDialogView(Activity activity, int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.view_food_activity);
        dialog.setTitle("View");
        dialog.setCancelable(true);

        ImageView imgView=dialog.findViewById(R.id.imageViewFood);//view_food_activity
        TextView edtName=dialog.findViewById(R.id.edtName);//view_food_activity
//        TextView edtPrice=dialog.findViewById(R.id.edtPrice);//view_food_activity
        TextView edtCity=dialog.findViewById(R.id.edtCity);//view_food_activity
        TextView edtState=dialog.findViewById(R.id.edtState);//view_food_activity
        TextView edtMonsoon=dialog.findViewById(R.id.edtMonsoon);//view_food_activity
        TextView edtSampleType=dialog.findViewById(R.id.edtSampleType);//view_food_activity
        TextView edtLatLon=dialog.findViewById(R.id.edtlatLon);//view_food_activity
        TextView edtPh=dialog.findViewById(R.id.edtPh);//view_food_activity
        TextView edtEc=dialog.findViewById(R.id.edtEc);//view_food_activity
        TextView edtTds=dialog.findViewById(R.id.edtTds);//view_food_activity
        TextView edtArsenic=dialog.findViewById(R.id.edtArsenic);//view_food_activity
        TextView edtNitrate=dialog.findViewById(R.id.edtNitrate);//view_food_activity
        TextView edtFluoride=dialog.findViewById(R.id.edtFluoride);//view_food_activity
        TextView edtSulphate=dialog.findViewById(R.id.edtSulphate);//view_food_activity
        TextView edtChloride=dialog.findViewById(R.id.edtChloride);//view_food_activity
        TextView edtHardness=dialog.findViewById(R.id.edtHardness);//view_food_activity
        TextView edtAlkalinity=dialog.findViewById(R.id.edtAlkalinity);//view_food_activity
        Button upload=dialog.findViewById(R.id.btnUpload);//view_food_activity

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM FOOD");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            if(id == position) {
                final String name = cursor.getString(1);
//                String price = cursor.getString(2);
                byte[] image = cursor.getBlob(2);
                Bitmap bmp= BitmapFactory.decodeByteArray(image,0,image.length);
                final String city = cursor.getString(3);
                final String state = cursor.getString(4);
                final String monsoon = cursor.getString(5);
                final String sampleType = cursor.getString(6);
                final String latLon = cursor.getString(7);
                final String ph = cursor.getString(8);
                final String ec = cursor.getString(9);
                final String tds = cursor.getString(10);
                final String arsenic = cursor.getString(11);
                final String nitrate = cursor.getString(12);
                final String fluoride = cursor.getString(13);
                final String sulphate = cursor.getString(14);
                final String chloride = cursor.getString(15);
                final String hardness = cursor.getString(16);
                final String alkalinity = cursor.getString(17);

                imgView.setImageBitmap(bmp);
                edtName.setText(name);
//                edtPrice.setText(price);
                edtCity.setText(city);
                edtState.setText(state);
                edtMonsoon.setText(monsoon);
                edtSampleType.setText(sampleType);
                edtLatLon.setText(latLon);
                edtPh.setText(ph);
                edtEc.setText(ec);
                edtTds.setText(tds);
                edtArsenic.setText(arsenic);
                edtNitrate.setText(nitrate);
                edtFluoride.setText(fluoride);
                edtSulphate.setText(sulphate);
                edtChloride.setText(chloride);
                edtHardness.setText(hardness);
                edtAlkalinity.setText(alkalinity);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        demoRef.child(name).child("city").setValue(city);
                        demoRef.child(name).child("state").setValue(state);
                        demoRef.child(name).child("monsoon").setValue(monsoon);
                        demoRef.child(name).child("sample_type").setValue(sampleType);
                        demoRef.child(name).child("lat_lon").setValue(latLon);
                        demoRef.child(name).child("ph").setValue(ph);
                        demoRef.child(name).child("ec").setValue(ec);
                        demoRef.child(name).child("tds").setValue(tds);
                        demoRef.child(name).child("arsenic").setValue(arsenic);
                        demoRef.child(name).child("nitrate").setValue(nitrate);
                        demoRef.child(name).child("fluoride").setValue(fluoride);
                        demoRef.child(name).child("sulphate").setValue(sulphate);
                        demoRef.child(name).child("chloride").setValue(chloride);
                        demoRef.child(name).child("hardness").setValue(hardness);
                        demoRef.child(name).child("alkalinity").setValue(alkalinity);
                        Toast.makeText(FoodList.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        dialog.show();
    }

    ImageView imageViewFood;
    private void showDialogUpdate(Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_food_activity);
        dialog.setTitle("Update");
        imageViewFood =  dialog.findViewById(R.id.imageViewFood);//update_food_activity
        final EditText edtName =  dialog.findViewById(R.id.edtName);//update_food_activity
//        final EditText edtPrice =  dialog.findViewById(R.id.edtPrice);//update_food_activity
        final EditText edtCity =  dialog.findViewById(R.id.edtCity);//update_food_activity
        final EditText edtState =  dialog.findViewById(R.id.edtState);//update_food_activity
        final EditText edtMonsoon =  dialog.findViewById(R.id.edtMonsoon);//update_food_activity
        final EditText edtSampleType =  dialog.findViewById(R.id.edtSampleType);//update_food_activity
        final EditText edtLatLon =  dialog.findViewById(R.id.edtLatLon);//update_food_activity
        final EditText edtPh=dialog.findViewById(R.id.edtPh);//update_food_activity
        final EditText edtEc=dialog.findViewById(R.id.edtEc);//update_food_activity
        final EditText edtTds=dialog.findViewById(R.id.edtTds);//update_food_activity
        final EditText edtArsenic=dialog.findViewById(R.id.edtArsenic);//update_food_activity
        final EditText edtNitrate=dialog.findViewById(R.id.edtNitrate);//update_food_activity
        final EditText edtFluoride=dialog.findViewById(R.id.edtFluoride);//update_food_activity
        final EditText edtSulphate=dialog.findViewById(R.id.edtSulphate);//update_food_activity
        final EditText edtChloride=dialog.findViewById(R.id.edtChloride);//update_food_activity
        final EditText edtHardness=dialog.findViewById(R.id.edtHardness);//update_food_activity
        final EditText edtAlkalinity=dialog.findViewById(R.id.edtAlkalinity);//update_food_activity
        Button btnUpdate =  dialog.findViewById(R.id.btnUpdate);//update_food_activity

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.99);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.99);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        Cursor cursor = Main2Activity.sqLiteHelper.getData("SELECT * FROM FOOD");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            if(id==position) {
                String name = cursor.getString(1);
//                String price = cursor.getString(2);
                byte[] image = cursor.getBlob(2);
                Bitmap bmp=BitmapFactory.decodeByteArray(image,0,image.length);
                String city = cursor.getString(3);
                String state = cursor.getString(4);
                String monsoon = cursor.getString(5);
                String sampleType = cursor.getString(6);
                String latLon = cursor.getString(7);
                String ph = cursor.getString(8);
                String ec = cursor.getString(9);
                String tds = cursor.getString(10);
                String arsenic = cursor.getString(11);
                String nitrate = cursor.getString(12);
                String fluoride = cursor.getString(13);
                String sulphate = cursor.getString(14);
                String chloride = cursor.getString(15);
                String hardness = cursor.getString(16);
                String alkalinity = cursor.getString(17);

                if(name!=null) { edtName.setText(name); }
                if(bmp!=null){ imageViewFood.setImageBitmap(bmp);}
//                edtPrice.setText(price);
                if(city!=null){ edtCity.setText(city); }
                if(state!=null){ edtState.setText(state); }
                if(monsoon!=null){ edtMonsoon.setText(monsoon); }
                if(sampleType!=null){ edtSampleType.setText(sampleType); }
                if(latLon!=null){ edtLatLon.setText(latLon); }
                if(ph!=null){ edtPh.setText(ph); }
                if(ec!=null){ edtEc.setText(ec); }
                if(tds!=null){ edtTds.setText(tds); }
                if(arsenic!=null){ edtArsenic.setText(arsenic); }
                if(nitrate!=null){ edtNitrate.setText(nitrate); }
                if(fluoride!=null){ edtFluoride.setText(fluoride); }
                if(sulphate!=null){ edtSulphate.setText(sulphate); }
                if(chloride!=null){ edtChloride.setText(chloride); }
                if(hardness!=null){ edtHardness.setText(hardness); }
                if(alkalinity!=null){ edtAlkalinity.setText(alkalinity); }
                break;
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        try {
                Main2Activity.sqLiteHelper.deleteData(position);
//                Main2Activity.sqLiteHelper.updateData(//
//                        position, edtName.getText().toString().trim(), Main2Activity.imageViewToByte(imageViewFood), edtCity.getText().toString().trim(), edtState.getText().toString().trim(),
//                        edtMonsoon.getText().toString().trim(), edtSampleType.getText().toString().trim(), edtLatLon.getText().toString().trim(), edtPh.getText().toString().trim(),
//                        edtEc.getText().toString().trim(), edtTds.getText().toString().trim(), edtArsenic.getText().toString().trim(), edtNitrate.getText().toString().trim(),
//                        edtFluoride.getText().toString().trim(), edtSulphate.getText().toString().trim(), edtChloride.getText().toString().trim(), edtHardness.getText().toString().trim(),
//                        edtAlkalinity.getText().toString().trim()
//                );//edtPrice.getText().toString().trim()
                            Main2Activity.sqLiteHelper.insertData(edtName.getText().toString().trim(), imageViewToByte(imageViewFood), edtCity.getText().toString().trim(),edtState.getText().toString().trim(),
                                    edtMonsoon.getText().toString().trim(), edtSampleType.getText().toString().trim(), edtLatLon.getText().toString().trim(), edtPh.getText().toString().trim(),
                                    edtEc.getText().toString().trim(), edtTds.getText().toString().trim(), edtArsenic.getText().toString().trim(), edtNitrate.getText().toString().trim(),
                                    edtFluoride.getText().toString().trim(), edtSulphate.getText().toString().trim(), edtChloride.getText().toString().trim(), edtHardness.getText().toString().trim(),
                                    edtAlkalinity.getText().toString().trim());
                updateFoodList();
                Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                        }
                        catch (Exception error) {
                            Log.e("Update error", error.getMessage());
                        }
                updateFoodList();
            }
        });

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        FoodList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });
    }

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(FoodList.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Log.d("Checking", "in update food list");
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM FOOD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = (int) cursor.getLong(0);
            String name = cursor.getString(1);
//            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(2);
            String city = cursor.getString(3);
            String state = cursor.getString(4);
            String monsoon = cursor.getString(5);
            String sampleType = cursor.getString(6);
            String latLon = cursor.getString(7);
            String ph = cursor.getString(8);
            String ec = cursor.getString(9);
            String tds = cursor.getString(10);
            String arsenic = cursor.getString(11);
            String nitrate = cursor.getString(12);
            String fluoride = cursor.getString(13);
            String sulphate = cursor.getString(14);
            String chloride = cursor.getString(15);
            String hardness = cursor.getString(16);
            String alkalinity = cursor.getString(17);
            list.add(new Food(name, image, id, city, state, monsoon, sampleType, latLon,ph,ec,tds,arsenic,nitrate,fluoride,sulphate,chloride,hardness,alkalinity));//,price
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
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
        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_online, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.requestData:
                requestDataCallback();
                break;
            case R.id.mapView:
                //map view
                Intent intent=new Intent(FoodList.this,SeeLocationActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestDataCallback() {
        String connectionInstruction = "";
        String connectionStatus = "";
        try {
            if (isNetworkReachable()) {
                DataTask dataTask = new DataTask(mainActivityDataTaskNotification);
                connectionInstruction = dataTask.execute("https://httpbin.org/get?arg1=1&arg2=2").get();
                connectionStatus = "Online";
            } else {
                connectionInstruction = "Please check your internet connection and try your request again.";
                connectionStatus = "Offline";
            }
        } catch (Exception e) {
            connectionInstruction = e.getMessage();
            connectionStatus = "error";
        }
//        mainActivityDataTaskNotification.notifyMainActivity(connectionStatus, connectionInstruction);
        if(connectionStatus == "Online"){
            final AlertDialog dialog=new AlertDialog.Builder(FoodList.this)
                    .setMessage("Do you want to upload all data in online database")
                    .setTitle("Upload Online")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //upload online and delete from local storage
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
        else if(connectionStatus == "Offline"){
            Toast.makeText(FoodList.this, connectionInstruction, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(FoodList.this, connectionStatus, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkReachable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    MainActivityDataTaskNotification mainActivityDataTaskNotification = new MainActivityDataTaskNotification() {
        @Override
        public void notifyMainActivity(String connStatus, String connInstruction) {
            Toast.makeText(FoodList.this, connStatus+"\n\n"+connInstruction, Toast.LENGTH_SHORT).show();
        }
    };
}