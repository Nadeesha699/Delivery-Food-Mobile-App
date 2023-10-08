package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {

    Button btn_add_food,btn_view_food,btn_view_order;

    TextView textView;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        btn_add_food = findViewById(R.id.btn_add_food);
        textView = findViewById(R.id.txtuser);
        btn_view_food = findViewById(R.id.btn_view_food);
        btn_view_order = findViewById(R.id.btn_view_order);

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        textView.setText(sharedPreferences.getString("USERN","NO DATA"));

        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,AddFood.class);
                startActivity(intent);
            }
        });

        btn_view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,Foodview.class);
                startActivity(intent);
            }
        });

        btn_view_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,OrderView.class);
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
                Intent intent = new Intent(AdminHome.this,Login.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
