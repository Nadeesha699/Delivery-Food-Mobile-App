package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class OrderView extends AppCompatActivity {

    CardView card_1,card_2,card_3,card_4,card_5,card_6;
    TextView lbl_o_id_1,lbl_o_id_2,lbl_o_id_3,lbl_o_id_4,lbl_o_id_5,lbl_o_id_6;
    TextView lbl_o_item_1,lbl_o_item_2,lbl_o_item_3,lbl_o_item_4,lbl_o_item_5,lbl_o_item_6;
    TextView lbl_o_cus_1,lbl_o_cus_2,lbl_o_cus_3,lbl_o_cus_4,lbl_o_cus_5,lbl_o_cus_6;
    TextView lbl_0_branch_1,lbl_0_branch_2,lbl_0_branch_3,lbl_0_branch_4,lbl_0_branch_5,lbl_0_branch_6;

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    SharedPreferences sharedPreferences;

    String name;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_view);

        backbtn = findViewById(R.id.backbtn);

        card_1 = findViewById(R.id.card_1);
        card_2 = findViewById(R.id.card_2);
        card_3 = findViewById(R.id.card_3);
        card_4 = findViewById(R.id.card_4);
        card_5 = findViewById(R.id.card_5);
        card_6 = findViewById(R.id.card_6);

        lbl_o_id_1 = findViewById(R.id.lbl_o_id_1);
        lbl_o_id_2 = findViewById(R.id.lbl_o_id_2);
        lbl_o_id_3 = findViewById(R.id.lbl_o_id_3);
        lbl_o_id_4 = findViewById(R.id.lbl_o_id_4);
        lbl_o_id_5 = findViewById(R.id.lbl_o_id_5);
        lbl_o_id_6 = findViewById(R.id.lbl_o_id_6);

        lbl_o_item_1 = findViewById(R.id.lbl_price1);
        lbl_o_item_2 = findViewById(R.id.lbl_o_item_2);
        lbl_o_item_3 = findViewById(R.id.lbl_o_item_3);
        lbl_o_item_4 = findViewById(R.id.lbl_o_item_4);
        lbl_o_item_5 = findViewById(R.id.lbl_o_item_5);
        lbl_o_item_6 = findViewById(R.id.lbl_o_item_6);

        lbl_o_cus_1 = findViewById(R.id.lbl_o_cus_1);
        lbl_o_cus_2 = findViewById(R.id.lbl_o_cus_2);
        lbl_o_cus_3 = findViewById(R.id.lbl_o_cus_3);
        lbl_o_cus_4 = findViewById(R.id.lbl_o_cus_4);
        lbl_o_cus_5 = findViewById(R.id.lbl_o_cus_5);
        lbl_o_cus_6 = findViewById(R.id.lbl_o_cus_6);

        lbl_0_branch_1 = findViewById(R.id.lbl_0_branch_1);
        lbl_0_branch_2 = findViewById(R.id.lbl_0_branch_2);
        lbl_0_branch_3 = findViewById(R.id.lbl_0_branch_3);
        lbl_0_branch_4 = findViewById(R.id.lbl_0_branch_4);
        lbl_0_branch_5 = findViewById(R.id.lbl_0_branch_5);
        lbl_0_branch_6 = findViewById(R.id.lbl_0_branch_6);

        card_1.setVisibility(View.INVISIBLE);
        card_2.setVisibility(View.INVISIBLE);
        card_3.setVisibility(View.INVISIBLE);
        card_4.setVisibility(View.INVISIBLE);
        card_5.setVisibility(View.INVISIBLE);
        card_6.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        name = sharedPreferences.getString("USERN","Nadee");

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("ORDERS",new String[]{"order_item","price","order_time","order_date","deliver_time","deliver_date","branch"},null,null,null,null,null);
        cursor.moveToFirst();

        int count = cursor.getCount();
        if(count == 0 ){
            new AlertDialog.Builder(this,R.style.alert).setTitle("UUNREAL").setMessage("No orders yet :( ").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(OrderView.this,AdminHome.class);
                    startActivity(intent);
                }
            }).show();
        }
        else if(count == 1){
            cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(1)},null,null,null);
            cursor.moveToFirst();
            String id = String.valueOf(cursor.getInt(0));
            lbl_o_id_1.setText(id);
            lbl_o_cus_1.setText(name);
            lbl_o_item_1.setText(cursor.getString(1));
            lbl_0_branch_1.setText(cursor.getString(2));

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
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_1.setText(id);
                    lbl_o_cus_1.setText(name);
                    lbl_o_item_1.setText(cursor.getString(1));
                    lbl_0_branch_1.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_2.setText(id);
                    lbl_o_cus_2.setText(name);
                    lbl_o_item_2.setText(cursor.getString(1));
                    lbl_0_branch_2.setText(cursor.getString(2));

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
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_1.setText(id);
                    lbl_o_cus_1.setText(name);
                    lbl_o_item_1.setText(cursor.getString(1));
                    lbl_0_branch_1.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_2.setText(id);
                    lbl_o_cus_2.setText(name);
                    lbl_o_item_2.setText(cursor.getString(1));
                    lbl_0_branch_2.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_3.setText(id);
                    lbl_o_cus_3.setText(name);
                    lbl_o_item_3.setText(cursor.getString(1));
                    lbl_0_branch_3.setText(cursor.getString(2));

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
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_1.setText(id);
                    lbl_o_cus_1.setText(name);
                    lbl_o_item_1.setText(cursor.getString(1));
                    lbl_0_branch_1.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_2.setText(id);
                    lbl_o_cus_2.setText(name);
                    lbl_o_item_2.setText(cursor.getString(1));
                    lbl_0_branch_2.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_3.setText(id);
                    lbl_o_cus_3.setText(name);
                    lbl_o_item_3.setText(cursor.getString(1));
                    lbl_0_branch_3.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_4.setText(id);
                    lbl_o_cus_4.setText(name);
                    lbl_o_item_4.setText(cursor.getString(1));
                    lbl_0_branch_4.setText(cursor.getString(2));

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
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_1.setText(id);
                    lbl_o_cus_1.setText(name);
                    lbl_o_item_1.setText(cursor.getString(1));
                    lbl_0_branch_1.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_2.setText(id);
                    lbl_o_cus_2.setText(name);
                    lbl_o_item_2.setText(cursor.getString(1));
                    lbl_0_branch_2.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_3.setText(id);
                    lbl_o_cus_3.setText(name);
                    lbl_o_item_3.setText(cursor.getString(1));
                    lbl_0_branch_3.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_4.setText(id);
                    lbl_o_cus_4.setText(name);
                    lbl_o_item_4.setText(cursor.getString(1));
                    lbl_0_branch_4.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==5){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_5.setText(id);
                    lbl_o_cus_5.setText(name);
                    lbl_o_item_5.setText(cursor.getString(1));
                    lbl_0_branch_5.setText(cursor.getString(2));

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
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_1.setText(id);
                    lbl_o_cus_1.setText(name);
                    lbl_o_item_1.setText(cursor.getString(1));
                    lbl_0_branch_1.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.INVISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==2){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_2.setText(id);
                    lbl_o_cus_2.setText(name);
                    lbl_o_item_2.setText(cursor.getString(1));
                    lbl_0_branch_2.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.INVISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==3){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_3.setText(id);
                    lbl_o_cus_3.setText(name);
                    lbl_o_item_3.setText(cursor.getString(1));
                    lbl_0_branch_3.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.INVISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x==4){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_4.setText(id);
                    lbl_o_cus_4.setText(name);
                    lbl_o_item_4.setText(cursor.getString(1));
                    lbl_0_branch_4.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.INVISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x == 5){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_5.setText(id);
                    lbl_o_cus_5.setText(name);
                    lbl_o_item_5.setText(cursor.getString(1));
                    lbl_0_branch_5.setText(cursor.getString(2));

                    card_1.setVisibility(View.VISIBLE);
                    card_2.setVisibility(View.VISIBLE);
                    card_3.setVisibility(View.VISIBLE);
                    card_4.setVisibility(View.VISIBLE);
                    card_5.setVisibility(View.VISIBLE);
                    card_6.setVisibility(View.INVISIBLE);
                }
                else if(x == 6){
                    cursor = sqLiteDatabase.query("ORDERS",new String[]{"_orderid","order_item","branch"},"_orderid=?",new String[]{String.valueOf(x)},null,null,null);
                    cursor.moveToFirst();
                    String id = String.valueOf(cursor.getInt(0));
                    lbl_o_id_6.setText(id);
                    lbl_o_cus_6.setText(name);
                    lbl_o_item_6.setText(cursor.getString(1));
                    lbl_0_branch_6.setText(cursor.getString(2));

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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderView.this,AdminHome.class);
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
                Intent intent = new Intent(OrderView.this,AdminHome.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
