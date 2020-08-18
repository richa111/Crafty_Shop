package com.example.akshi.e_commerce;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by akshi on 02-08-2017.
 */

public class EditProduct extends AppCompatActivity
{
    EditText edtPID;
    Button btnSearch;
    DBHandler dbh;
    String pname,description,price,pid;
    boolean flag=false;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        edtPID=(EditText)findViewById(R.id.edtPID);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        dbh=new DBHandler(this);

        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dbh.openReadable();
                flag = dbh.getPid(Integer.parseInt(edtPID.getText().toString()));

                Cursor cursor = dbh.Edit_Product(Integer.parseInt(edtPID.getText().toString()));
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {


                    pid = cursor.getString(0);
                    pname = cursor.getString(1);
                    description = cursor.getString(2);
                    price = cursor.getString(3);
                    cursor.moveToNext();
                }


                if (edtPID.getText().toString().equals(""))
                {
                    Toast.makeText(EditProduct.this, "Please Enter The Product ID", Toast.LENGTH_SHORT).show();

                } else if (flag == false) {
                    Toast.makeText(EditProduct.this, "Product ID doesnot exists", Toast.LENGTH_SHORT).show();
                } else {


                    Intent intent = new Intent(EditProduct.this, EditProduct2.class);
                    intent.putExtra("Pid", pid);
                    intent.putExtra("Pname", pname);
                    intent.putExtra("Description", description);
                    intent.putExtra("Price", price);
                    startActivity(intent);

                    Log.d("Pid", "" + pid);
                    Log.d("Pname", "" + pname);
                    Log.d("Description", "" + description);
                    Log.d("Price", "" + price);
                }
            }

        });
    }



}

