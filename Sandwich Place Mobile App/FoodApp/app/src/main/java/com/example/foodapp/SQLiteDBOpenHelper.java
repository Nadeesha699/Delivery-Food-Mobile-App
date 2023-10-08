package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBOpenHelper extends SQLiteOpenHelper {
    static final String name = "SandwichStoreDB";
    static final int version = 1;

   public SQLiteDBOpenHelper(Context context) {
        super(context, name, null, version);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("CREATE TABLE USER("+"_userid INTEGER PRIMARY KEY AUTOINCREMENT,"+"user_name TEXT,"+"email TEXT,"+"profile TEXT,"+"telphone INTEGER,"+"password TEXT,"+"type TEXT)");
       db.execSQL("CREATE TABLE ITEM("+"_itemid INTEGER PRIMARY KEY AUTOINCREMENT,"+"item_name TEXT,"+"price INTEGER,"+"discription TEXT)");
       db.execSQL("CREATE TABLE ORDERS("+"_orderid INTEGER PRIMARY KEY AUTOINCREMENT,"+"order_item TEXT,"+"price INTEGER,"+"order_time TEXT,"+"order_date TEXT,"+"deliver_time TEXT,"+"deliver_date TEXT,"+"branch TEXT)");

       addUserRecord(db,"Nadeesha Amandi","ama@gmail.com","C:\\Users\\HP\\Downloads\\b.jpg",0711764232,"123456","admin");
       addUserRecord(db,"Nadeesha Ruwandima","nadee@gmail.com","C:\\Users\\HP\\Downloads\\b.jpg",0711764232,"123456","user");
       addUserRecord(db,"Anjali Akarsha","asha@gmail.com","C:\\Users\\HP\\Downloads\\b.jpg",0711764232,"123456","deliver");

       addItemRecord(db,"Bread Sandwich",400,"dis1");
       addItemRecord(db,"Cheese Sandwich",350,"dis2");
       addItemRecord(db,"Grilled Sandwich",500,"dis5");
       addItemRecord(db,"Ham Sandwich",700,"dis6");
       addItemRecord(db,"Ice cream Sandwich",500,"dis7");
       addItemRecord(db,"Meat Ball Sandwich",600,"dis8");
       addItemRecord(db,"Olive Sandwich",800,"dis9");
       addItemRecord(db,"Prawn Sandwich",700,"dis10");
       addItemRecord(db,"Vegetable Sandwich",400,"dis4");
       addItemRecord(db,"Salman Sandwich",500,"dis5");
       addItemRecord(db,"Tuna Sandwich",700,"dis6");
       addItemRecord(db,"Beef Sandwich",500,"dis7");
       addItemRecord(db,"Nutella Sandwich",500,"dis7");
       addItemRecord(db,"Oreo Ice cream Sandwich Sandwich",500,"dis7");



       //addOrderRecord(db,"Ice cream Sandwich",500,"2023/10/05","12.30","2023/10/05","13.30","Matara");
       /*addOrderRecord(db,"Meat Ball Sandwich",600,"2023/10/06","15.30","2023/10/06","16.30","Galle");
       addOrderRecord(db,"Beef Sandwich",500,"2023/10/06","18.30","2023/10/06","19.30","Matara");
       addOrderRecord(db,"Prawn Sandwich",600,"2023/10/07","11.30","2023/10/07","12.30","Matara");
       addOrderRecord(db,"Olive Sandwich",800,"2023/10/09","09.30","2023/10/09","10.30","Galle");*/

    }

    private void addUserRecord(SQLiteDatabase db,String Name,String email,String profile,int tel,String password,String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name",Name);
        contentValues.put("email",email);
        contentValues.put("profile",profile);
        contentValues.put("telphone",tel);
        contentValues.put("password",password);
        contentValues.put("type",type);
        db.insert("USER",null,contentValues);
    }

    private void addItemRecord(SQLiteDatabase db,String name,int price,String dis){
       ContentValues contentValues = new ContentValues();
       contentValues.put("item_name",name);
       contentValues.put("price",price);
       contentValues.put("discription",dis);
       db.insert("ITEM",null,contentValues);
    }

    private void addOrderRecord(SQLiteDatabase db, String name, int price, String order_time, String order_date,String deliver_time,String deliver_date,String branch){
       ContentValues contentValues = new ContentValues();
       contentValues.put("order_item",name);
       contentValues.put("price",price);
       contentValues.put("order_time",order_time);
       contentValues.put("order_date",order_date);
       contentValues.put("deliver_time",deliver_time);
       contentValues.put("deliver_date",deliver_date);
       contentValues.put("branch",branch);
       db.insert("ORDERS",null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
