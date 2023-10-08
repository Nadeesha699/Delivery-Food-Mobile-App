package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class OrderItem extends AppCompatActivity {

    ImageView img_back;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

    Cursor cursor;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<ListItem> itemList;
    private SearchView searchView;

    BottomNavigationView bottomNavigationView;

    Button btn_order_pen;
    ImageView imgpending;

    String item1,price1,item2,price2,item3,price3,item4,price4,item5,price5,item6,price6,item7,price7,item8,price8,item9,price9,item10,price10,item11,price11,item12,price12,item13,price13,item14,price14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        img_back = findViewById(R.id.img_back_o3);
        imgpending = findViewById(R.id.img_order_pending);

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        int x=1;
        while(x<=14) {
            cursor = sqLiteDatabase.query("ITEM", new String[]{"item_name", "price"}, "_itemid =?", new String[]{String.valueOf(x)}, null, null, null);
            if(x==1){
                cursor.moveToFirst();
                item1 = cursor.getString(0);
                price1 =cursor.getString(1);
            }
            else if (x == 2){
                cursor.moveToFirst();
                item2 = cursor.getString(0);
                price2 =cursor.getString(1);
            }
            else if (x == 3){
                cursor.moveToFirst();
                item3 = cursor.getString(0);
                price3 =cursor.getString(1);
            }
            else if (x == 4){
                cursor.moveToFirst();
                item4 = cursor.getString(0);
                price4 =cursor.getString(1);
            }
            else if (x == 5){
                cursor.moveToFirst();
                item5 = cursor.getString(0);
                price5 =cursor.getString(1);
            }
            else if (x == 6){
                cursor.moveToFirst();
                item6 = cursor.getString(0);
                price6 =cursor.getString(1);
            }
            else if (x == 7){
                cursor.moveToFirst();
                item7 = cursor.getString(0);
                price7 =cursor.getString(1);
            }
            else if (x == 8){
                cursor.moveToFirst();
                item8 = cursor.getString(0);
                price8 =cursor.getString(1);
            }
            else if (x == 9){
                cursor.moveToFirst();
                item9 = cursor.getString(0);
                price9 =cursor.getString(1);
            }
            else if (x == 10){
                cursor.moveToFirst();
                item10 = cursor.getString(0);
                price10 =cursor.getString(1);
            }
            else if (x == 11){
                cursor.moveToFirst();
                item11 = cursor.getString(0);
                price11 =cursor.getString(1);
            }
            else if (x == 12){
                cursor.moveToFirst();
                item12 = cursor.getString(0);
                price12 =cursor.getString(1);
            }
            else if (x == 13){
                cursor.moveToFirst();
                item13 = cursor.getString(0);
                price13 =cursor.getString(1);
            }
            else if (x == 14){
                cursor.moveToFirst();
                item14 = cursor.getString(0);
                price14 =cursor.getString(1);
            }

            x++;

        }
        cursor.close();

        // Initialize data
        itemList = new ArrayList<>();
        itemList.add(new ListItem("ORDER", item1, "LKR", price1, R.drawable.breadsandwich));
        itemList.add(new ListItem("ORDER",  item2, "LKR", price2, R.drawable.cheesesandwich));
        itemList.add(new ListItem("ORDER",  item3, "LKR", price3, R.drawable.grilledchickensandwich));
        itemList.add(new ListItem("ORDER",  item4, "LKR", price4, R.drawable.hamsandwich));
        itemList.add(new ListItem("ORDER",  item5, "LKR", price5, R.drawable.icecreamsandwich));
        itemList.add(new ListItem("ORDER",  item6, "LKR", price6, R.drawable.meatballsandwich));
        itemList.add(new ListItem("ORDER",  item7, "LKR", price7, R.drawable.olivesandwich));
        itemList.add(new ListItem("ORDER",  item8, "LKR", price8, R.drawable.prawnsandwich));
        itemList.add(new ListItem("ORDER",  item9, "LKR", price9, R.drawable.vegetablesandwich));
        itemList.add(new ListItem("ORDER",  item10, "LKR", price10, R.drawable.salmonsandwich));
        itemList.add(new ListItem("ORDER",  item11, "LKR", price11, R.drawable.tunasandwiches));
        itemList.add(new ListItem("ORDER",  item12, "LKR", price12, R.drawable.beefsandwich));
        itemList.add(new ListItem("ORDER",  item13, "LKR", price13, R.drawable.nutellasandwich));
        itemList.add(new ListItem("ORDER",  item14, "LKR", price14, R.drawable.oreoicecreamsandwich));

        // Initialize the RecyclerView and Adapter
        adapter = new CustomAdapter(this, itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Implement search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderItem.this,Home.class);
                startActivity(intent);
            }
        });

        imgpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderItem.this,OrdersPending.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.order);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()  {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),History.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        return true;
                }
                return false;
            }
        });

        imgpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderItem.this,OrdersPending.class);
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
                Intent intent = new Intent(OrderItem.this,Home.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }


}
