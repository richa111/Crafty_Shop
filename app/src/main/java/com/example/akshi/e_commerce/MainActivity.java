package com.example.akshi.e_commerce;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
public class MainActivity extends AppCompatActivity

     implements NavigationView.OnNavigationItemSelectedListener {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SharedPreferences prfs = getSharedPreferences("LoginPrefs", 0);
            String email = prfs.getString("email", null);
            String password = prfs.getString("password", null);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }             }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_logout) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @SuppressWarnings("StatementWithEmptyBody")
        //@Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            Fragment fragment = null;

            if (id == R.id.nav_home) {
                fragment = new Home();
                // Handle the camera action
            } else if (id == R.id.nav_man) {

                fragment = new Man();

            } else if (id == R.id.nav_women) {

                fragment = new Woman();
            } else if (id == R.id.nav_kids) {

                fragment = new Kids();

            } else if (id == R.id.nav_toys){

                fragment = new Toys();

            } else if (id == R.id.nav_contact)
            {
                fragment = new Contact();
            }
            else if (id == R.id.nav_wallet) {
                fragment = new Wallet();
            }
            else if (id == R.id.nav_offers) {
                fragment = new Offers();
            }

            else if (id == R.id.nav_edit_product) {
                startActivity(new Intent(MainActivity.this, EditProduct.class));
            }
            else if (id == R.id.nav_delete_product) {
                startActivity(new Intent(MainActivity.this, DeleteProduct.class));
            }

            else if (id == R.id.nav_exit) {

                finish();
            }

            if (fragment != null) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction tr = fragmentManager.beginTransaction();
                // tr.add(fragment, "");
                tr.replace(R.id.fragmentContainer, fragment, "dfgdfg");
                tr.commit();


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
            return true;
        }
    }
