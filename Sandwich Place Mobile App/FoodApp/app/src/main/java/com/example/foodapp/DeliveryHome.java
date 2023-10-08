package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DeliveryHome extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView txt_username,txt_price,txt_item;

    Button btn_deliver;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

    Cursor cursor;

    double longitude;
    double latitude;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverhome);

        txt_username = findViewById(R.id.txtuser);
        txt_item = findViewById(R.id.txt_delitem);
        txt_price = findViewById(R.id.txt_delprice);
        btn_deliver = findViewById(R.id.btn_deliver);
        constraintLayout = findViewById(R.id.layout_hide);

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        txt_username.setText(sharedPreferences.getString("USERN","NO DATA"));

        sharedPreferences = getSharedPreferences("DELIVERYDATA",MODE_PRIVATE);
        String itemName = sharedPreferences.getString("itemname",null);
        if(itemName != null){
            txt_item.setText(sharedPreferences.getString("itemname","NO DATA"));
            String price = String.valueOf(sharedPreferences.getInt("itemprice",0));
            txt_price.setText(price);
            latitude = Double.parseDouble(sharedPreferences.getString("latitude",null));
            longitude = Double.parseDouble(sharedPreferences.getString("longitude",null));

            //always my location connect to my location as 0 so i add below two values and you are connect firebase after remove that value
            latitude = latitude +  5.931255;
            longitude = longitude + 80.591433;

        }
        else{
            constraintLayout.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(this,R.style.deliver).setTitle("SANDWICH PLACE").setMessage("No delivery food now :( ").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(DeliveryHome.this,Login.class);
                    startActivity(intent);
                }
            }).show();
        }


        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + latitude +"," + longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DeliveryHome.this,Login.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
