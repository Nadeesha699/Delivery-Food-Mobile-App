package com.example.foodapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class profile extends AppCompatActivity {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    private Button btnEdit;
    private Button btnSave;

    ImageView img_add_photo;

    TextView lbl_repass,txt_email;
    EditText txt_repass,txt_uname,txt_pnum,txt_pass;

    SharedPreferences sharedPreferences;

    Cursor cursor;

    BottomNavigationView bottomNavigationView;

    ImageView img_back,img_profile;

    private static final int REQUEST_CODE_PERMISSION = 101;
    private static final int REQUEST_CODE_GALLERY = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        btnEdit = findViewById(R.id.btnedit);
        btnSave = findViewById(R.id.btn_save);
        lbl_repass = findViewById(R.id.lbl_repass);
        txt_repass = findViewById(R.id.txtpsw2);
        img_add_photo = findViewById(R.id.imh_add_pro);
        txt_uname = findViewById(R.id.txtuname);
        txt_pnum = findViewById(R.id.txtpnum);
        txt_pass = findViewById(R.id.txtpsw);
        txt_email = findViewById(R.id.txt_email);
        img_back = findViewById(R.id.img_back_o2);
        img_profile = findViewById(R.id.img_profile);

        img_add_photo.setOnClickListener(view -> openGallery());

        btnSave.setVisibility(View.INVISIBLE);
        lbl_repass.setVisibility(View.INVISIBLE);
        txt_repass.setVisibility(View.INVISIBLE);
        img_add_photo.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        int id =sharedPreferences.getInt("UID",0);

        cursor = sqLiteDatabase.query("USER",new String[]{"user_name","email","telphone","password"},"_userid=?",new String[]{String.valueOf(id)},null,null,null,null);
        cursor.moveToFirst();
        txt_uname.setText(cursor.getString(0));
        txt_email.setText(cursor.getString(1));
        String num = String.valueOf(cursor.getInt(2));
        txt_pnum.setText(num);
        txt_pass.setText(cursor.getString(3));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this,Home.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pass.setText("");
                btnSave.setVisibility(View.VISIBLE);
                lbl_repass.setVisibility(View.VISIBLE);
                txt_repass.setVisibility(View.VISIBLE);
                img_add_photo.setVisibility(View.VISIBLE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt_uname.getText().toString();
                String num = txt_pnum.getText().toString();
                String pass = txt_pass.getText().toString();
                String repass = txt_repass.getText().toString();
                if(name.isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(profile.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null name");
                    builder.show();
                }
                else if(num.isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(profile.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null telephone no");
                    builder.show();
                }
                else if(pass.isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(profile.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null password");
                    builder.show();
                }
                else if(!(pass.equals(repass))){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(profile.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("not match between new password nad confirm password");
                    builder.show();
                }
                else{
                    saveDataToDatabase(txt_uname.getText().toString(),Integer.parseInt(txt_pnum.getText().toString()),txt_pass.getText().toString());
                }

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(),History.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Save the selected image to a file
            saveImageToFile(selectedImageUri);

            // Load and display the saved image
            loadImageAndDisplay();
        }
    }

    private void saveImageToFile(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File destinationFile = new File(getExternalFilesDir(null), "my_pro_image.jpg");
            OutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageAndDisplay() {
        File imageFile = new File(getExternalFilesDir(null), "my_pro_image.jpg");
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                img_profile.setImageBitmap(bitmap);
                img_profile.setVisibility(ImageView.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now open the gallery
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied. Cannot open gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDataToDatabase(String username, int pnum, String psw) {

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);

        contentValues = new ContentValues();
        contentValues.put("user_name",username);
        contentValues.put("telphone",pnum);
        contentValues.put("password",psw);
        int a =sqLiteDatabase.update("USER",contentValues,"_userid=?",new String[]{String.valueOf(sharedPreferences.getInt("UID", 0))});
        if(a == 0){
           Toast.makeText(this,"not updated",Toast.LENGTH_SHORT).show();
        }
        else if(a ==1 ){
            Toast.makeText(this, "updated user", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabase.close();
        btnSave.setVisibility(View.INVISIBLE);
        lbl_repass.setVisibility(View.INVISIBLE);
        txt_repass.setVisibility(View.INVISIBLE);
        img_add_photo.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(profile.this,Home.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
