package com.example.akshi.e_commerce;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

/**
 * Created by akshi on 02-08-2017.
 */

public class DBHandler extends Activity
{
    //fields for Registration Table table
    public static final String DB_UID = "uid";
    public static final String DB_FNAME = "firstname";
    public static final String DB_LNAME = "lastname";
    public static final String DB_GENDER= "gender";
    public static final String DB_EMAIL = "email";
    public static final String DB_CONTACTNUM="contactno";
    public static final String DB_CITY="city";
    public static final String DB_ADDRESS="address";
    public static final String DB_SPINNER="ques";
    public static final String DB_SANS="securityans";
    public static final String DB_PASS = "password";
    public static final String DB_CPASS = "confirm_password";

    //Fields for Wallet Table
    public static final String EMIAL_ID = "email";
    public static final String AMOUNT = "amount";

    //fields for product table
    public static final String DB_PID="prod_id";
    public static final String DB_PNAME="prod_name";
    public static final String DB_PRODUCTIMAGE="prod_image";
    public static final String DB_PDESCRIPTION="prod_description";
    public static final String DB_PPRICE="prod_price";
    public static final String DB_PSPINNER="list";


    //constants for DB
    public static final String DB_NAME = "Fashion Street";
    public static final int DB_VERSION=1;

    public static final String DB_TABLE = "Registration";
    public static final String WALLET_TABLE = "Wallet";
    //constants for productDB
    public static final String DB_TABLE2 = "ProductEntry";

    ProductList list;
    CartItems cartlist;

    String str1,str2,str3;

    public static final String DB_CREATE = "CREATE TABLE "
            +DB_TABLE+ "("+DB_UID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +DB_FNAME+" TEXT,"
            +DB_LNAME+" TEXT,"
            +DB_GENDER+" TEXT, "
            +DB_EMAIL+" TEXT,"
            +DB_CONTACTNUM+" TEXT,"
            +DB_CITY+" TEXT,"
            +DB_ADDRESS+" TEXT,"
            +DB_SPINNER+" TEXT,"
            +DB_SANS+" TEXT,"
            +DB_PASS+" TEXT,"
            +DB_CPASS+" TEXT)";

    public static final String CREATE_WALLET = "CREATE TABLE "
            +WALLET_TABLE+ "("+EMIAL_ID+ " TEXT PRIMARY KEY,"
            +AMOUNT+ " INTEGER)";

    public static final String DB_CREATE2 = "CREATE TABLE "
            +DB_TABLE2+ "("+DB_PID+ " INTEGER PRIMARY KEY,"
            +DB_PNAME+" TEXT,"
            +DB_PRODUCTIMAGE+" BLOB,"
            +DB_PDESCRIPTION+" TEXT,"
            +DB_PPRICE+" TEXT,"
            +DB_PSPINNER+" TEXT)";

    public SQLiteDatabase db;
    public SQLHelper helper;
    public Context context;

    public DBHandler(Context context)
    {

        this.context=context;
        helper=new SQLHelper();
        db=helper.getWritableDatabase();
    }

    public long addUser(String fname, String lname, String gender, String email, String contactnum,String city, String address, String ques, String securityans,String password,String confirmpass)
    {
        ContentValues cv=new ContentValues();

        cv.put(DB_FNAME,fname);
        cv.put(DB_LNAME,lname);
        cv.put(DB_GENDER,gender);
        cv.put(DB_EMAIL,email);
        cv.put(DB_CONTACTNUM,contactnum);
        cv.put(DB_CITY,city);
        cv.put(DB_ADDRESS,address);
        cv.put(DB_SPINNER,ques);
        cv.put(DB_SANS,securityans);
        cv.put(DB_PASS,password);
        cv.put(DB_CPASS,confirmpass);
        return db.insert(DB_TABLE,null,cv);
    }


    public String CheckEmail(String email) {

        String s=null;
        String str = "SELECT * FROM " + DB_TABLE + " WHERE " + DB_EMAIL + "= '" + email + "'";
        Cursor cursor = db.rawQuery(str, null);

        if (cursor.moveToFirst()) {

            s  =   cursor.getString(4);
            Log.d("data",s);

        }
        return s;

    }

    public int CheckAmount(String email) {

        int i = 0;
        String str = "SELECT * FROM " + WALLET_TABLE + " WHERE " + DB_EMAIL + "= '" + email + "'";
        Cursor cursor = db.rawQuery(str, null);

        if (cursor.moveToFirst()) {

            i  =   Integer.parseInt(cursor.getString(1));

        }
        return i;

    }

    public int UpdateAmount(String email,int amount)
    {
        ContentValues cv = new ContentValues();
        // cv.put(EMIAL_ID,email);
        cv.put(AMOUNT,amount);

        Log.d("1234", WALLET_TABLE+" "+EMIAL_ID + " = " + email);


        return db.update(WALLET_TABLE,cv,EMIAL_ID + " = " +"'"+ email+"'",null);

    }


    public DBHandler openReadable()throws android.database.sqlite.SQLiteException
    {
        helper = new SQLHelper();
        db=helper.getReadableDatabase();
        return this;
    }

    public Cursor login(String email,String password)
    {
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_EMAIL,DB_PASS}, DB_EMAIL+ "='"+email+"' and "+DB_PASS+ "='"+password+"'",null,null,null,null);
        return cursor;
    }

    public Cursor getData(String email,String ques,String ans)
    {
        String str = "Select * from " + DB_TABLE + " where " + DB_EMAIL + " = '" +email+"' and " + DB_SPINNER + " = '"+ques+"' and " +DB_SANS + "= '" + ans + "'";

        Cursor cursor = db.rawQuery(str, null);

        return cursor;
    }

    public String getPass(String email)
    {

        String s=null;
        String str="SELECT * FROM" +DB_TABLE+ "WHERE" +DB_EMAIL+ "='"+email;
        Cursor cursor=db.rawQuery(str,null);
        if(cursor.moveToFirst())
        {
            s=cursor.getString(0);
        }
        return s;
    }

    public Vector<ProductList> getData()
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }

        return vector;

    }

    public Vector<CartItems> getCart()
    {
        Vector<CartItems> vector = new Vector<>();

        String query = "SELECT * FROM "+ DB_TABLE2;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            cartlist = new CartItems();
            cartlist.setPname(cursor.getString(1));
            cartlist.setCost(cursor.getInt(4));
            // cartlist.setQuantity(cursor.getInt());

            vector.add(cartlist);

            cursor.moveToNext();

        }

        return vector;

    }

    public Vector<ProductList> formalshirts1 (String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Vector<ProductList> productLists(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }


    public Vector<ProductList> womenshirts(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Vector<ProductList> womentsaree1(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Vector<ProductList> womensaree2(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Vector<ProductList> womensaree3(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Vector<ProductList> formalshirts3(String cat)
    {
        Vector<ProductList> vector = new Vector<>();

        String query = "Select * from "+ DB_TABLE2 + " Where " + DB_PSPINNER + " = '"+ cat + "'" ;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            list = new ProductList();
            list.setProductName(cursor.getString(1));
            list.setProductCost(cursor.getString(4));

            vector.add(list);

            cursor.moveToNext();

        }
        return vector;
    }

    public Cursor womenshirts3(int p) {

        Log.d("1234", "cursor id:= " + p);

        String s = null;

        String query = "Select * from " + DB_TABLE2 + " WHERE " + DB_PID + "=" + p;

        Cursor cursor = db.rawQuery(query, null);

        Log.d("1234", "cursor size:= " + cursor.getCount());

        return cursor;
    }

    public long addAmount(String email,int amount)
    {
        ContentValues cv = new ContentValues();

        cv.put(EMIAL_ID,email);
        cv.put(AMOUNT,amount);

        return db.insert(WALLET_TABLE,null,cv);
    }

    public long prodEntry(String pid, String pname, String product_image, String description, String price, String list)
    {
        ContentValues cv=new ContentValues();
        cv.put(DB_PID,pid);
        cv.put(DB_PNAME,pname);
        cv.put(DB_PRODUCTIMAGE,product_image);
        cv.put(DB_PDESCRIPTION,description);
        cv.put(DB_PPRICE,price);
        cv.put(DB_PSPINNER,list);
        return db.insert(DB_TABLE2,null,cv);
    }

    public boolean getPid(int pid)
    {
        int a=0;
        Cursor cursor=db.query(DB_TABLE2,new String[]{DB_PID},DB_PID+"="+pid,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            a=Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        if (a==pid)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Cursor Delete_Product(int pid)
    {
        Cursor cursor = db.query(DB_TABLE2,new String[]{DB_PID,DB_PNAME,DB_PPRICE}, DB_PID+ " = '"+pid+"'" ,null,null,null,null);
        return cursor;
    }
    public int deleteEntry(int pid)
    {
        return db.delete(DB_TABLE2,DB_PID+"="+pid,null);
    }

    public Cursor Edit_Product(int pid)
    {
        Cursor cursor = db.query(DB_TABLE2,new String[]{DB_PID,DB_PNAME,DB_PDESCRIPTION,DB_PPRICE}, DB_PID+ " = '"+pid+"'" ,null,null,null,null,null);
        return cursor;
    }

    public int updateEntry(int Pid,String Pname,String Description,String Price)
    {
        ContentValues cv = new ContentValues();
        cv.put(DB_PID,Pid);
        cv.put(DB_PNAME,Pname);
        cv.put(DB_PDESCRIPTION,Description);
        cv.put(DB_PPRICE,Price);
        return db.update(DB_TABLE2,cv,DB_PID+"="+Pid,null);
    }



    public class SQLHelper extends SQLiteOpenHelper
    {
        public SQLHelper()
        {
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DB_CREATE);//Create Registration Table
            db.execSQL(CREATE_WALLET);//Create Wallet Table
            db.execSQL(DB_CREATE2);//create product table
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS"+DB_TABLE);
            Log.d("upgrade","DATABASE TABLE upgraded from version" +oldVersion+ "to" +newVersion);
            onCreate(db);
        }
    }
}


