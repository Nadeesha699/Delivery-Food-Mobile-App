package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;

public class Home extends AppCompatActivity {

    ;
    ImageView imageView,img_pro;

    TextView txt_user;

    private HorizontalScrollView horizontalScrollView;
    private Handler handler;
    private int scrollPosition = 0;
    private static final int SCROLL_DELAY = 3000;

    public int id;
    public  String name,nameTest;

    SharedPreferences sharedPreferences;

    BottomNavigationView bottomNavigationView;

    ConstraintLayout con_1,con_2,con_3,con_4,con_5,con_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView= findViewById(R.id.imageView);
        LinearLayout linearLayout = findViewById(R.id.topbanar);
        LinearLayout linearLayout2 = findViewById(R.id.foodlistbar1);
        LinearLayout linearLayout3 = findViewById(R.id.foodlistbar2);
        con_1 = findViewById(R.id.con_1);
        con_2 = findViewById(R.id.con_2);
        con_3 = findViewById(R.id.con_3);
        con_4 = findViewById(R.id.con_4);
        con_5 = findViewById(R.id.con_5);
        con_6 = findViewById(R.id.con_6);

        con_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        con_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        con_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        con_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        con_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        con_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        txt_user = findViewById(R.id.txtuser);
        img_pro = findViewById(R.id.img_pro);
        loadImageAndDisplay();

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        nameTest = sharedPreferences.getString("USERN",null);
        if(nameTest == null){
            txt_user.setText(sharedPreferences.getString("uemail",null));
        }
        else{
            txt_user.setText(nameTest);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,OrderItem.class);
                startActivity(intent);
            }
        });

        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        handler = new Handler();

        // Start the initial auto-scrolling task
        startAutoScroll();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()  {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),OrderItem.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),History.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void startAutoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Scroll the HorizontalScrollView to the next position
                scrollPosition += 700; // Adjust this value based on your item width
                horizontalScrollView.smoothScrollTo(scrollPosition, 2700);

                // Repeat the auto-scrolling task
                startAutoScroll();
            }
        }, SCROLL_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending callbacks when the activity is destroyed
        handler.removeCallbacksAndMessages(null);
    }
    private void loadImageAndDisplay() {
        File imageFile = new File(getExternalFilesDir(null), "my_pro_image.jpg");
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                img_pro.setImageBitmap(bitmap);
                img_pro.setVisibility(ImageView.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                img_pro.setImageResource(R.drawable.baseline_account_circle_24);

            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Home.this,Login.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}