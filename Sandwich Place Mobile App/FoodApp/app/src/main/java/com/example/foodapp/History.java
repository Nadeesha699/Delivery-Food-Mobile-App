package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class History extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    CardView card_1,card_2,card_3,card_4,card_5,card_6;
    TextView txt_o1,txt_o2,txt_o3,txt_o4,txt_o5,txt_o6;
    TextView txt_oprice1,txt_oprice2,txt_oprice3,txt_oprice4,txt_oprice5,txt_oprice6;
    TextView txt_branch1,txt_branch2,txt_branch3,txt_branch4,txt_branch5,txt_branch6;
    TextView txt_ddate1,txt_ddate2,txt_ddate3,txt_ddate4,txt_ddate5,txt_ddate6;
    TextView txt_dtime1,txt_dtime2,txt_dtime3,txt_dtime4,txt_dtime5,txt_dtime6;
    TextView txt_odate1,txt_odate2,txt_odate3,txt_odate4,txt_odate5,txt_odate6;
    TextView txt_otime1,txt_otime2,txt_otime3,txt_otime4,txt_otime5,txt_otime6;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    Cursor cursor;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_ok);
        card_1 = findViewById(R.id.card_1);
        card_2 = findViewById(R.id.card_2);
        card_3 = findViewById(R.id.card_3);
        card_4 = findViewById(R.id.card_4);
        card_5 = findViewById(R.id.card_5);
        card_6 = findViewById(R.id.card_6);

        txt_o1 = findViewById(R.id.lbl_item1);
        txt_o2 = findViewById(R.id.lbl_item2);
        txt_o3 = findViewById(R.id.lbl_item3);
        txt_o4 = findViewById(R.id.lbl_item4);
        txt_o5 = findViewById(R.id.lbl_item5);
        txt_o6 = findViewById(R.id.lbl_item6);

        txt_oprice1 = findViewById(R.id.lbl_price1);
        txt_oprice2 = findViewById(R.id.lbl_price2);
        txt_oprice3 = findViewById(R.id.lbl_price3);
        txt_oprice4 = findViewById(R.id.lbl_price4);
        txt_oprice5 = findViewById(R.id.lbl_price5);
        txt_oprice6 = findViewById(R.id.lbl_price6);

        txt_branch1 = findViewById(R.id.lbl_0_branch_1);
        txt_branch2 = findViewById(R.id.txt_branch2);
        txt_branch3 = findViewById(R.id.txt_branch3);
        txt_branch4 = findViewById(R.id.txt_branch4);
        txt_branch5 = findViewById(R.id.txt_branch5);
        txt_branch6 = findViewById(R.id.txt_branch6);

        txt_ddate1 = findViewById(R.id.txt_ddate1);
        txt_ddate2 = findViewById(R.id.txt_ddate2);
        txt_ddate3 = findViewById(R.id.txt_ddate3);
        txt_ddate4 = findViewById(R.id.txt_ddate4);
        txt_ddate5 = findViewById(R.id.txt_ddate5);
        txt_ddate6 = findViewById(R.id.txt_ddate6);

        txt_dtime1 =findViewById(R.id.txt_dtime1);
        txt_dtime2 =findViewById(R.id.txt_dtime2);
        txt_dtime3 =findViewById(R.id.txt_dtime3);
        txt_dtime4 =findViewById(R.id.txt_dtime4);
        txt_dtime5 =findViewById(R.id.txt_dtime5);
        txt_dtime6 =findViewById(R.id.txt_dtime6);

        txt_odate1 = findViewById(R.id.txt_odate1);
        txt_odate2 = findViewById(R.id.txt_odate2);
        txt_odate3 = findViewById(R.id.txt_odate3);
        txt_odate4 = findViewById(R.id.txt_odate4);
        txt_odate5 = findViewById(R.id.txt_odate5);
        txt_odate6 = findViewById(R.id.txt_odate6);

        txt_otime1 = findViewById(R.id.txt_otime1);
        txt_otime2 = findViewById(R.id.txt_otime2);
        txt_otime3 = findViewById(R.id.txt_otime3);
        txt_otime4 = findViewById(R.id.txt_otime4);
        txt_otime5 = findViewById(R.id.txt_otime5);
        txt_otime6 = findViewById(R.id.txt_otime6);

        card_1.setVisibility(View.INVISIBLE);
        card_2.setVisibility(View.INVISIBLE);
        card_3.setVisibility(View.INVISIBLE);
        card_4.setVisibility(View.INVISIBLE);
        card_5.setVisibility(View.INVISIBLE);
        card_6.setVisibility(View.INVISIBLE);

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this,Home.class);
                startActivity(intent);
            }
        });

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},null,null,null,null,null);
        cursor.moveToFirst();
        //String test = String.valueOf(cursor.getCount());
        //txt_o1.setText(test);
        int count = cursor.getCount();
        if(count == 0){
            Intent intent = new Intent(History.this,HistoryNo.class);
            startActivity(intent);
        }
        if(count == 1){
            cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(1)},null,null,null);
            cursor.moveToFirst();
            txt_o1.setText(cursor.getString(0));
            String price = String.valueOf(cursor.getInt(1));
            txt_oprice1.setText(price);
            txt_otime1.setText(cursor.getString(2));
            txt_odate1.setText(cursor.getString(3));
            if(cursor.getString(4) != null){
                txt_dtime1.setText(cursor.getString(4));
                txt_ddate1.setText(cursor.getString(5));
            }
            else{
                txt_dtime1.setText("pending");
                txt_ddate1.setText("pending");
            }
            txt_branch1.setText(cursor.getString(6));

            card_1.setVisibility(View.VISIBLE);
            card_2.setVisibility(View.INVISIBLE);
            card_3.setVisibility(View.INVISIBLE);
            card_4.setVisibility(View.INVISIBLE);
            card_5.setVisibility(View.INVISIBLE);
            card_6.setVisibility(View.INVISIBLE);

        }
        else if(count == 2){
            int x =1;
            while(x <= count){
                if(x == 1){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o1.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice1.setText(price);
                    txt_otime1.setText(cursor.getString(2));
                    txt_odate1.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime1.setText(cursor.getString(4));
                        txt_ddate1.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime1.setText("pending");
                        txt_ddate1.setText("pending");
                    }
                    txt_branch1.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o2.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice2.setText(price);
                    txt_otime2.setText(cursor.getString(2));
                    txt_odate2.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime2.setText(cursor.getString(4));
                        txt_ddate2.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime2.setText("pending");
                        txt_ddate2.setText("pending");
                    }
                    txt_branch2.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                x++;
            }

        }
        else if(count == 3){
            int x =1;
            while(x <= count){
                if(x == 1){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o1.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice1.setText(price);
                    txt_otime1.setText(cursor.getString(2));
                    txt_odate1.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime1.setText(cursor.getString(4));
                        txt_ddate1.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime1.setText("pending");
                        txt_ddate1.setText("pending");
                    }
                    txt_branch1.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o2.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice2.setText(price);
                    txt_otime2.setText(cursor.getString(2));
                    txt_odate2.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime2.setText(cursor.getString(4));
                        txt_ddate2.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime2.setText("pending");
                        txt_ddate2.setText("pending");
                    }
                    txt_branch2.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o3.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice3.setText(price);
                    txt_otime3.setText(cursor.getString(2));
                    txt_odate3.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime3.setText(cursor.getString(4));
                        txt_ddate3.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime3.setText("pending");
                        txt_ddate3.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                x++;
            }
        }
        else if(count == 4){
            int x =1;
            while(x <= count){
                if(x == 1){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o1.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice1.setText(price);
                    txt_otime1.setText(cursor.getString(2));
                    txt_odate1.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime1.setText(cursor.getString(4));
                        txt_ddate1.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime1.setText("pending");
                        txt_ddate1.setText("pending");
                    }
                    txt_branch1.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o2.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice2.setText(price);
                    txt_otime2.setText(cursor.getString(2));
                    txt_odate2.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime2.setText(cursor.getString(4));
                        txt_ddate2.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime2.setText("pending");
                        txt_ddate2.setText("pending");
                    }
                    txt_branch2.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o3.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice3.setText(price);
                    txt_otime3.setText(cursor.getString(2));
                    txt_odate3.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime3.setText(cursor.getString(4));
                        txt_ddate3.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime3.setText("pending");
                        txt_ddate3.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o4.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice4.setText(price);
                    txt_otime4.setText(cursor.getString(2));
                    txt_odate4.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime4.setText(cursor.getString(4));
                        txt_ddate4.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime4.setText("pending");
                        txt_ddate4.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                x++;
            }
        }
        else if(count == 5){
            int x =1;
            while(x <= count){
                if(x == 1){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o1.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice1.setText(price);
                    txt_otime1.setText(cursor.getString(2));
                    txt_odate1.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime1.setText(cursor.getString(4));
                        txt_ddate1.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime1.setText("pending");
                        txt_ddate1.setText("pending");
                    }
                    txt_branch1.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o2.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice2.setText(price);
                    txt_otime2.setText(cursor.getString(2));
                    txt_odate2.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime2.setText(cursor.getString(4));
                        txt_ddate2.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime2.setText("pending");
                        txt_ddate2.setText("pending");
                    }
                    txt_branch2.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o3.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice3.setText(price);
                    txt_otime3.setText(cursor.getString(2));
                    txt_odate3.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime3.setText(cursor.getString(4));
                        txt_ddate3.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime3.setText("pending");
                        txt_ddate3.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o4.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice4.setText(price);
                    txt_otime4.setText(cursor.getString(2));
                    txt_odate4.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime4.setText(cursor.getString(4));
                        txt_ddate4.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime4.setText("pending");
                        txt_ddate4.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==5){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o5.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice5.setText(price);
                    txt_otime5.setText(cursor.getString(2));
                    txt_odate5.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime5.setText(cursor.getString(4));
                        txt_ddate5.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime5.setText("pending");
                        txt_ddate5.setText("pending");
                    }
                    txt_branch5.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.VISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                x++;
            }
        }
        else if(count == 6){
            int x =1;
            while(x <= count){
                if(x == 1){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o1.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice1.setText(price);
                    txt_otime1.setText(cursor.getString(2));
                    txt_odate1.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime1.setText(cursor.getString(4));
                        txt_ddate1.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime1.setText("pending");
                        txt_ddate1.setText("pending");
                    }
                    txt_branch1.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o2.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice2.setText(price);
                    txt_otime2.setText(cursor.getString(2));
                    txt_odate2.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime2.setText(cursor.getString(4));
                        txt_ddate2.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime2.setText("pending");
                        txt_ddate2.setText("pending");
                    }
                    txt_branch2.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o3.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice3.setText(price);
                    txt_otime3.setText(cursor.getString(2));
                    txt_odate3.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime3.setText(cursor.getString(4));
                        txt_ddate3.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime3.setText("pending");
                        txt_ddate3.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o4.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice4.setText(price);
                    txt_otime4.setText(cursor.getString(2));
                    txt_odate4.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime4.setText(cursor.getString(4));
                        txt_ddate4.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime4.setText("pending");
                        txt_ddate4.setText("pending");
                    }
                    txt_branch3.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x == 5){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o5.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice5.setText(price);
                    txt_otime5.setText(cursor.getString(2));
                    txt_odate5.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime5.setText(cursor.getString(4));
                        txt_ddate5.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime5.setText("pending");
                        txt_ddate5.setText("pending");
                    }
                    txt_branch5.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.VISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x == 6){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    txt_o6.setText(cursor.getString(0));
                    String price = String.valueOf(cursor.getInt(1));
                    txt_oprice6.setText(price);
                    txt_otime6.setText(cursor.getString(2));
                    txt_odate6.setText(cursor.getString(3));
                    if(cursor.getString(4) != null){
                        txt_dtime6.setText(cursor.getString(4));
                        txt_ddate6.setText(cursor.getString(5));
                    }
                    else{
                        txt_dtime6.setText("pending");
                        txt_ddate6.setText("pending");
                    }
                    txt_branch6.setText(cursor.getString(6));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.VISIBLE);
                    card_6.setVisibility(View.VISIBLE);
                }

               
                x++;
            }
        }

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()  {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),OrderItem.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(History.this,Home.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}