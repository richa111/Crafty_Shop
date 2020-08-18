package com.example.akshi.e_commerce;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by akshi on 02-08-2017.
 */

public class AdminFragment extends AppCompatActivity
{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fragment);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAdmin();

            }
        });
    }

    private void logoutAdmin()
    {
        Intent intent = new Intent(AdminFragment.this, Login.class);
        startActivity(intent);
        finish();
    }

}


