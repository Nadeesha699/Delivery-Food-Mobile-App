package com.example.foodapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddFood extends AppCompatActivity {

    EditText txtname,txtprice,txtdis;
    Button btnsave;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    ContentValues contentValues;
    ImageView img_back,img_file;

    private static final int REQUEST_CODE_PERMISSION = 101;
    private static final int REQUEST_CODE_GALLERY = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);
        txtname = findViewById(R.id.txtname);
        txtdis = findViewById(R.id.txtdis);
        txtprice = findViewById(R.id.txtprice);
        btnsave = findViewById(R.id.btn_save);
        img_back = findViewById(R.id.backbtn);
        img_file = findViewById(R.id.img_file);
        img_file.setOnClickListener(view -> openGallery());

        img_back = findViewById(R.id.backbtn);

        sqLiteOpenHelper= new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtname.getText().toString().isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddFood.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null item name");
                    builder.show();
                }
                else if(txtprice.getText().toString().isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddFood.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null price");
                    builder.show();
                }
                else if(txtdis.getText().toString().isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddFood.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null description");
                    builder.show();
                }
                else {
                    contentValues = new ContentValues();
                    contentValues.put("item_name", txtname.getText().toString());
                    contentValues.put("price", Integer.parseInt(txtprice.getText().toString()));
                    contentValues.put("discription", txtdis.getText().toString());
                    long a = sqLiteDatabase.insert("ITEM", null, contentValues);
                    if (a == 0) {
                        Toast.makeText(AddFood.this, "UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddFood.this, "ADD SUCCESS", Toast.LENGTH_SHORT).show();
                    }

                    sqLiteDatabase.close();
                }
                txtname.setText("");
                txtprice.setText("");
                txtdis.setText("");
                img_file.setImageResource(R.drawable.baseline_add_a_photo_24);

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFood.this, AdminHome.class);
                startActivity(intent);
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
            File destinationFile = new File(getExternalFilesDir(null), "my_image.jpg");
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
        File imageFile = new File(getExternalFilesDir(null), "my_image.jpg");
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                img_file.setImageBitmap(bitmap);
                img_file.setVisibility(ImageView.VISIBLE);
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


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AddFood.this,AdminHome.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }
}
