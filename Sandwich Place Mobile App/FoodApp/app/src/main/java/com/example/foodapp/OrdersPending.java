package com.example.foodapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class OrdersPending extends AppCompatActivity {

    Button btn_com, btn_track;
    ImageView img_back;

    TextView txt_item1, txt_view1;

    ImageView img_op1;
    String Location;

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    ContentValues contentValues;

    String orderTime, orderDate;

    SharedPreferences sharedPreferences;

    ConstraintLayout con_1;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    double longitude;
    double latitude;

    SharedPreferences sharedPreferences02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderspending);
        btn_com = findViewById(R.id.btn_com);
        img_back = findViewById(R.id.backbtn);
        btn_track = findViewById(R.id.btn_track);
        txt_item1 = findViewById(R.id.txt_item1);
        txt_view1 = findViewById(R.id.textView1);
        img_op1 = findViewById(R.id.imgop_1);
        con_1 = findViewById(R.id.cons_1);

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Handle location updates here.
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    // Use the location data as needed.
                }
            }
        };
        startLocationUpdates();

        if(getIntent().getStringExtra("orderTime") == null){
            sharedPreferences02 = getSharedPreferences("TEMPORY DATA",MODE_PRIVATE);
           if(sharedPreferences02.getString("TOTIME",null) != null){
               Location = sharedPreferences02.getString("TLOCATION",null);
               txt_item1.setText(sharedPreferences02.getString("TITEM",null));
               String price = String.valueOf(sharedPreferences02.getInt("TPRICE",0));
               txt_view1.setText(price);
               orderTime = sharedPreferences02.getString("TOTIME",null);
               orderDate = sharedPreferences02.getString("TODATE",null);
           }
           else{
              Intent intent = new Intent(OrdersPending.this,Orders.class);
              startActivity(intent);
           }
        }
        else {
            Location = getIntent().getStringExtra("Location");
            orderTime = getIntent().getStringExtra("orderTime");
            orderDate = getIntent().getStringExtra("orderDate");
            txt_item1.setText(getIntent().getStringExtra("itemName"));
            txt_view1.setText(String.valueOf(getIntent().getIntExtra("price", 0)));

            sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
            sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

            contentValues = new ContentValues();
            contentValues.put("order_item ", txt_item1.getText().toString());
            contentValues.put("price", Integer.parseInt(txt_view1.getText().toString()));
            contentValues.put("order_time", orderTime);
            contentValues.put("order_date", orderDate);
            contentValues.put("branch", Location);
            sqLiteDatabase.insert("ORDERS", null, contentValues);

            sharedPreferences = getSharedPreferences("DELIVERYDATA", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("itemname", txt_item1.getText().toString());
            editor.putInt("itemprice", Integer.parseInt(txt_view1.getText().toString()));
            editor.putString("latitude", String.valueOf(latitude));
            editor.putString("longitude", String.valueOf(longitude));
            editor.commit();
        }


        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(Location, "Matara")) {
                    //matara - 5.954920  80.554956
                    Uri navigationIntentUri = Uri.parse("google.navigation:q=" + 5.954920 + "," + 80.554956);//creating intent with latlng
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (Objects.equals(Location, "Galle")) {
                    //galle - 6.053519  80.220978
                    Uri navigationIntentUri = Uri.parse("google.navigation:q=" + 6.053519 + "," + 80.220978);//creating intent with latlng
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences02 = getSharedPreferences("TEMPORY DATA",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences02.edit();
                editor.putString("TLOCATION",Location);
                editor.putString("TITEM",txt_item1.getText().toString());
                editor.putInt("TPRICE", Integer.parseInt(txt_view1.getText().toString()));
                editor.putString("TOTIME",orderTime);
                editor.putString("TODATE",orderDate);
                editor.commit();
                Intent intent = new Intent(OrdersPending.this, Home.class);
                startActivity(intent);
            }
        });

        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OrdersPending.this,R.style.chooses).setTitle("SANDWICH PLACE").setMessage("are you delivery confirm ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String deliverDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String deliverTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        contentValues = new ContentValues();
                       // contentValues.put("order_item ", txt_item1.getText().toString());
                       // contentValues.put("price", Integer.parseInt(txt_view1.getText().toString()));
                        //contentValues.put("order_time", orderTime);
                       // contentValues.put("order_date", orderDate);
                        contentValues.put("deliver_time", deliverTime);
                        contentValues.put("deliver_date", deliverDate);
                       // contentValues.put("branch", Location);
                        sqLiteDatabase.update("ORDERS",contentValues,"order_time=?",new String[]{orderTime});

                        sharedPreferences = getSharedPreferences("DELIVERYDATA", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("itemname",null);
                        editor.commit();

                        sharedPreferences02 = getSharedPreferences("TEMPORY DATA",MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences02.edit();
                        editor1.putString("TOTIME",null);
                        editor1.commit();


                        Intent intent = new Intent(OrdersPending.this, OrderComplete.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("no", null).show();
            }
        });

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates.
                startLocationUpdates();
            } else {
                // Permission denied. Handle this case.
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences02 = getSharedPreferences("TEMPORY DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences02.edit();
        editor.putString("TLOCATION",Location);
        editor.putString("TITEM",txt_item1.getText().toString());
        editor.putInt("TPRICE", Integer.parseInt(txt_view1.getText().toString()));
        editor.putString("TOTIME",orderTime);
        editor.putString("TODATE",orderDate);
        editor.commit();
        // Stop location updates when the activity is destroyed (optional).
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onBackPressed() {

        sharedPreferences02 = getSharedPreferences("TEMPORY DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences02.edit();
        editor.putString("TLOCATION",Location);
        editor.putString("TITEM",txt_item1.getText().toString());
        editor.putInt("TPRICE", Integer.parseInt(txt_view1.getText().toString()));
        editor.putString("TOTIME",orderTime);
        editor.putString("TODATE",orderDate);
        editor.commit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(OrdersPending.this,Home.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
