package com.example.akshi.e_commerce;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by akshi on 02-08-2017.
 */

public class Splash extends AppCompatActivity {
    ImageView im;
    Animation anu; //= AnimationUtils.loadAnimation(Splash.this,R.anim.slide_down);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prfs = getSharedPreferences("LoginPrefs", 0);
        final String email = prfs.getString("email", null);
        final String password = prfs.getString("password", null);


        im=(ImageView)findViewById(R.id.img);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

      /*  anu= AnimationUtils.loadAnimation(Splash.this,R.anim.blink);
        im.startAnimation(anu);
      */  Thread th=new Thread()
        {


            public void run()
            {
                try
                {

                    sleep(5000);

                }

                catch(Exception e)
                {
                    //Toast.makeText(Splash.this, "hello", Toast.LENGTH_SHORT).show();
                }
                finally
                {
                    if(email != null && password != null){
                        startActivity(new Intent(Splash.this,MainActivity.class));

                    }

                    else{
                        startActivity(new Intent(Splash.this,Login.class));
                    }
                    finish();

                }
            }
        };
        th.start();



    }
    public void myanim(){




/*
        anu= AnimationUtils.loadAnimation(Splash.this,R.anim.blink);

        im.startAnimation(anu);
*/


    }
}