package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Foodview extends AppCompatActivity {
    CardView card_1,card_2,card_3,card_4,card_5,card_6;
    TextView lbl_item1,lbl_item2,lbl_item3,lbl_item4,lbl_item5,lbl_item6;
    TextView lbl_price1,lbl_price2,lbl_price3,lbl_price4,lbl_price5,lbl_price6;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    Cursor cursor;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        card_1 = findViewById(R.id.card_1);
        card_2 = findViewById(R.id.card_2);
        card_3 = findViewById(R.id.card_3);
        card_4 = findViewById(R.id.card_4);
        card_5 = findViewById(R.id.card_5);
        card_6 = findViewById(R.id.card_6);

        lbl_item1 = findViewById(R.id.lbl_item1);
        lbl_item2 = findViewById(R.id.lbl_item2);
        lbl_item3 = findViewById(R.id.lbl_item3);
        lbl_item4 = findViewById(R.id.lbl_item4);
        lbl_item5 = findViewById(R.id.lbl_item5);
        lbl_item6 = findViewById(R.id.lbl_item6);

        lbl_price1 = findViewById(R.id.lbl_price1);
        lbl_price2 = findViewById(R.id.lbl_price2);
        lbl_price3 = findViewById(R.id.lbl_price3);
        lbl_price4 = findViewById(R.id.lbl_price4);
        lbl_price5 = findViewById(R.id.lbl_price5);
        lbl_price6 = findViewById(R.id.lbl_price6);

        img_back = findViewById(R.id.backbtn);



        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(1)},null,null,null);
        cursor.moveToFirst();

        lbl_item1.setText(cursor.getString(0));
        String price = String.valueOf(cursor.getInt(1));
        lbl_price1.setText(price);

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(2)},null,null,null);
        cursor.moveToFirst();

        lbl_item2.setText(cursor.getString(0));
        String price1 = String.valueOf(cursor.getInt(1));
        lbl_price2.setText(price1);

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(3)},null,null,null);
        cursor.moveToFirst();

        lbl_item3.setText(cursor.getString(0));
        String price2 = String.valueOf(cursor.getInt(1));
        lbl_price3.setText(price2);

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(4)},null,null,null);
        cursor.moveToFirst();

        lbl_item4.setText(cursor.getString(0));
        String price3 = String.valueOf(cursor.getInt(1));
        lbl_price4.setText(price3);

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(5)},null,null,null);
        cursor.moveToFirst();

        lbl_item5.setText(cursor.getString(0));
        String price4 = String.valueOf(cursor.getInt(1));
        lbl_price5.setText(price4);

        cursor = sqLiteDatabase.query("ITEM",new String[]{"item_name","price"},"_itemid=?",new String[]{String.valueOf(6)},null,null,null);
        cursor.moveToFirst();

        lbl_item6.setText(cursor.getString(0));
        String price5 = String.valueOf(cursor.getInt(1));
        lbl_price6.setText(price5);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Foodview.this, AdminHome.class);
                startActivity(intent);
            }
        });



        }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Foodview.this,Login.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
    }

