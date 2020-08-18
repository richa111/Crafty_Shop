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

public class DeleteProduct extends AppCompatActivity
{
    EditText edtPID;
    Button btnSearch;
    String pname,price,pid;
    DBHandler dbh;
    String str;
    boolean flag=false;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_product);

        edtPID=(EditText)findViewById(R.id.edtPID);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        dbh=new DBHandler(this);


        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dbh.openReadable();
                //  flag=dbh.getPid(Integer.parseInt(edtPID.getText().toString()));


                Cursor cursor = dbh.Delete_Product(Integer.parseInt(edtPID.getText().toString()));
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {

                    pid=cursor.getString(0);
                    pname=cursor.getString(1);
                    price=cursor.getString(2);
                    cursor.moveToNext();
                }

                if(edtPID.getText().toString().equals(""))
                {
                    Toast.makeText(DeleteProduct.this, "Please Enter The PID", Toast.LENGTH_SHORT).show();

                }
                else if (flag==false)
                {
                    Toast.makeText(DeleteProduct.this, "PID doesnot exists", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(DeleteProduct.this, DeleteProduct2.class);
                    intent.putExtra("Pid", pid);
                    intent.putExtra("Pname", pname);
                    intent.putExtra("Price", price);
                    startActivity(intent);


                    Log.d("Pid", "" + pid);
                    Log.d("Pname", "" + pname);
                    Log.d("Price", "" + price);

                }
            }
        });
    }
}
