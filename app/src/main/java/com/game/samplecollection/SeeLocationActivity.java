package com.game.samplecollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeeLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    Button backButton;
    Spinner elementSpinner;
    double latitute=0,longitude=0;
//    String phone = "test", state = "test", city = "test", latlon="test";
//    FusedLocationProviderClient mLocationClient;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    LocationCallback mLocationCallback;
    HandlerThread mHandlerThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_location);
        backButton=findViewById(R.id.backButton);
        elementSpinner=findViewById(R.id.elementSpinner);
//        phone = getIntent().getStringExtra("Phone");
//        state = getIntent().getStringExtra("State");
//        city = getIntent().getStringExtra("City");

        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(SeeLocationActivity.this);

        String[] str={"All", "Arsenic", "Nitrate", "Fluoride", "Sulphate", "Chloride", "Hardness", "Alkalinity"};
        for (int i=0;i<str.length;i++){
            list.add(str[i]);
        }
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        elementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SeeLocationActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                ArrayList<String> listEle = new ArrayList<>();
                Cursor cursor = Main2Activity.sqLiteHelper.getData("SELECT * FROM FOOD");
                listEle.clear();
                String str=adapterView.getItemAtPosition(i).toString();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);//error
                    String name = cursor.getString(1);
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
                    if(i>0 && (arsenic.equals(str) || nitrate.equals(str)|| fluoride.equals(str)|| sulphate.equals(str)|| chloride.equals(str)||
                            hardness.equals(str)|| alkalinity.equals(str))) {
                        listEle.add(latLon);
                    }
                }
                adapter.notifyDataSetChanged();
//                Log.d("Checking",list.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        elementSpinner.setAdapter(adapter);
        initGoogleMap();
//        mLocationClient = new FusedLocationProviderClient(this);
//        mLocationCallback = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if(locationResult==null){
//                    return;
//                }
//                final Location location = locationResult.getLastLocation();
//                Toast.makeText(SeeLocationActivity.this, location.getLatitude()+", "+location.getLongitude(), Toast.LENGTH_SHORT).show();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        latlon="("+location.getLatitude()+" , "+location.getLongitude()+")";
//                        gotoLocation(location.getLatitude(),location.getLongitude());
//                        MarkerOptions options = new MarkerOptions();
//                        options.position(new LatLng(location.getLatitude(),location.getLongitude()));
//                        mGoogleMap.addMarker(options);
//                        Log.d("Checking", "thread name: "+Thread.currentThread().getName());
//                    }
//                });
//                Log.d("Checking", location.getLatitude()+", "+location.getLongitude());
//                Log.d("Checking", "thread name: "+Thread.currentThread().getName());
//
//            }
//        };
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeeLocationActivity.this,FoodList.class);
//                intent.putExtra("Phone",phone);
//                intent.putExtra("State",state);
//                intent.putExtra("City",city);
//                intent.putExtra("LatLon", latlon);
                startActivity(intent);
            }
        });
    }

//    private void geoLocate(View view){
//        hideSoftKeyboard(view);
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            //location return by latitute and longitude
//            List<Address> addressList = geocoder.getFromLocation(latitute,longitude,3);
//
//            // location return by address
//            //List<Address> addressList = geocoder.getFromLocationName(locationName,1);
//            if(addressList.size()>0){
//                Address address = addressList.get(0);
//                gotoLocation(address.getLatitude(),address.getLongitude());
//                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
//                Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
//                Log.d("Checking", address.getLocality());
//
//            }
//            for(Address address:addressList){
//                Log.d("Checking", address.getAddressLine(address.getMaxAddressLineIndex()));
//
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void hideSoftKeyboard(View view){
//        InputMethodManager imn = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imn.hideSoftInputFromWindow(view.getWindowToken(),0);
//    }


    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
    }

    private void initGoogleMap() {
        if(isService()){
            if(isGPSEnabled()) {
                if (checkLocationPermission()) {
                    Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map_fragment);
                    supportMapFragment.getMapAsync(this);

                } else {
                    requestLocationPermission();
                }
            }
        }
    }

    private void gotoLocation(double lat, double lon){
        LatLng latLng = new LatLng(lat,lon);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
        mGoogleMap.moveCamera(cameraUpdate); // for setting initial position
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("Checking", "map is ready");
        mGoogleMap=map;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gotoLocation(latitute,longitude);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnable){
            return true;
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS permission")
                    .setMessage("GPS is required")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 9003);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private boolean isService() {
        GoogleApiAvailability googltApi = GoogleApiAvailability.getInstance();
        int result = googltApi.isGooglePlayServicesAvailable(this);
        if(result == ConnectionResult.SUCCESS){
            return true;
        }
        else if(googltApi.isUserResolvableError(result)){
            Dialog dialog = googltApi.getErrorDialog(this, result,9002, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface task) {
                    Toast.makeText(SeeLocationActivity.this, "Dialog is canclled by user", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        else{
            Toast.makeText(this, "Play services are required by this applications", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void requestLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //api >= 23
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9001 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.maptype_none:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.maptype_normal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.maptype_satellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.maptype_terrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.maptype_hybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void getCurrentLocation() {
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
//            return;
//        }
//        mLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                if(task.isSuccessful()){
//                    Location location = task.getResult();
//                    gotoLocation(location.getLatitude(),location.getLongitude());
//                    MarkerOptions options = new MarkerOptions();
//                    options.position(new LatLng(location.getLatitude(),location.getLongitude()));
//                    options.title("Your Location");
//                    mGoogleMap.addMarker(options);
//                }
//                else{
//                    Log.d("Checking", "location connot found");
//                }
//
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9003){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(providerEnabled){
                Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "GPS  not enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void getLocationUpdates(){
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(120000);
//        locationRequest.setFastestInterval(30000);
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//            return;
//        }
//        mHandlerThread = new HandlerThread("LocationCallbacksThread");
//        mHandlerThread.start();
//        mLocationClient.requestLocationUpdates(locationRequest,mLocationCallback,mHandlerThread.getLooper());
//    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(mLocationCallback != null){
//            mLocationClient.removeLocationUpdates(mLocationCallback);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandlerThread!=null) {
            mHandlerThread.quit();
        }
    }
}
