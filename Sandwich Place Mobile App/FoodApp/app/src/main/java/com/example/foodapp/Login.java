package com.example.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText email , password;
    Button btnSubmit;
    TextView createAcc;

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    Cursor cursor;

    public int uid;
    public String uname,type,uemail;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.txtuname);
        password=findViewById(R.id.txtpsw);
        btnSubmit = findViewById(R.id.btn_save);

        sqLiteOpenHelper = new SQLiteDBOpenHelper(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        //String value = preferences.getString("globalValue", "");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailCheck = email.getText().toString();
                String passCheck = password.getText().toString();
                cursor = sqLiteDatabase.query("USER",new String[]{"_userid","user_name","email","password","type"},null,null,null,null,null);
                if(cursor.getCount() == 0){
                    Toast.makeText(Login.this,"No entries Exists",Toast.LENGTH_LONG).show();
                }
                if (loginCheck(cursor,emailCheck,passCheck)) {
                    if(Objects.equals(type, "admin")){
                        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERN",uname);
                        editor.putInt("UID",uid);
                        editor.commit();

                        Intent intent = new Intent(Login.this, AdminHome.class);
                        email.setText("");
                        password.setText("");
                        startActivity(intent);
                    }
                    else if(Objects.equals(type, "user")){
                        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERN",uname);
                        editor.putInt("UID",uid);
                        editor.putString("uemail",uemail);
                        editor.commit();

                        Intent intent = new Intent(Login.this, Home.class);
                        email.setText("");
                        password.setText("");
                        startActivity(intent);
                    }
                    else if(Objects.equals(type, "deliver")){
                        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERN",uname);
                        editor.putInt("UID",uid);
                        editor.commit();

                        Intent intent = new Intent(Login.this, DeliveryHome.class);
                        email.setText("");
                        password.setText("");
                        startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this,R.style.alert);
                        builder.setCancelable(true);
                        builder.setTitle("ALERT");
                        builder.setMessage("wrong credential");
                        builder.show();
                    }

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this,R.style.alert);
                    builder.setCancelable(true);
                    builder.setTitle("ALERT");
                    builder.setMessage("wrong credential");
                    builder.show();
                }
                //sqLiteDatabase.close();
            }
        });
        createAcc = findViewById(R.id.singup);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
    public boolean loginCheck(Cursor cursor,String emailCheck,String passCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(2).equals(emailCheck)) {
                if (cursor.getString(3).equals(passCheck)) {
                    uid = cursor.getInt(0);
                    uname = cursor.getString(1);
                    type = cursor.getString(4);

                    uemail = cursor.getString(2);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Login.this,StartingView.class);
        startActivity(intent);

    }
}
