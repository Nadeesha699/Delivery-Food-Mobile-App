package com.example.foodapp;


import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText email , phone ,pass,repass;
    TextView signin;
    Button singup;

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.txtuname);
        phone=findViewById(R.id.txtpnum);
        pass=findViewById(R.id.txtpsw);
        repass=findViewById(R.id.txtpsw2);
        singup= findViewById(R.id.btn_save);
        signin = findViewById(R.id.singin);
        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String number1 = phone.getText().toString();
                String pass1 = pass.getText().toString();
                String repass1 = repass.getText().toString();
                if(!(email1.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"))){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Register.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("invalid email");
                    builder.show();
                }
                else if(email1.isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Register.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null email");
                    builder.show();
                }
                else if(number1.isEmpty()){
                    Toast.makeText(Register.this, "Null phone number", Toast.LENGTH_SHORT).show();
                }
                /*else if(number1.matches("^(0(71|72|74|76|77|78)[0-9]{7})$")){
                    Toast.makeText(Register.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }*/
                else if(pass1.isEmpty()){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Register.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("null password");
                    builder.show();
                }
                /*else if(pass1.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$")){
                    Toast.makeText(Register.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }*/
                else if(!(pass1.equals(repass1))){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Register.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("not match between new nad confirm password");
                    builder.show();
                }
                else{
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("email",email1);
                    contentValues.put("telphone",number1);
                    contentValues.put("password",repass1);
                    contentValues.put("type","user");
                    sqLiteDatabase.insert("USER",null,contentValues);

                    Toast.makeText(Register.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.exit);
        builder.setTitle("SANDWICH PLACE").setMessage("Do you want exit now ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        }).setNegativeButton("no",null).show();
    }

}