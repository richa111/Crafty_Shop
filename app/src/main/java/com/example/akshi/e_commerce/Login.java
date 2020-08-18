package com.example.akshi.e_commerce;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by akshi on 02-08-2017.
 */

public class Login extends AppCompatActivity {
    EditText et1, et2;
    Button b1, b2;
    String email, password;
    TextView tv1;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences("LoginPrefs", 0);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et1 = (EditText) findViewById(R.id.edtEmail);
        et2 = (EditText) findViewById(R.id.edtPassword);
        b1 = (Button) findViewById(R.id.btnLogin);
        b2 = (Button) findViewById(R.id.btnCancel);
        tv1 = (TextView) findViewById(R.id.txtRegi);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et1.getText().toString();


                password = et2.getText().toString();


                if ((et1.length() == 0) || (!isValidEmail(email)) || (et2.length() == 0)) {

                    if (email.length() == 0) {
                        et1.setError("Email ID not entered");
                        et1.requestFocus();
                    }
                    if (!isValidEmail(email)) {
                        et1.setError("Invalid Email");
                        et1.requestFocus();
                    }
                    if (et2.length() == 0) {

                        et2.setError("Please Enter password");
                        et2.requestFocus();
                    }
                } else {
                    log();
                }


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText(null);
                et2.setText(null);

            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        });

    }

    public void log() {

        String e = et1.getText().toString();
        String p = et2.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(this, "Checking....", "Please Wait...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, "url", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                loading.dismiss();
                //  String getst=s.toString()
                getmy(s);

                // Toast.makeText(Login.this, "" + s.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                loading.dismiss();
                Toast.makeText(Login.this, "Plz try again.?", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("registerEmail", email);
                Log.d("Email", email);
                parameters.put("registerPass", password);
                Log.d("Pass", password);
//                parameters.put("registerGender", "male");
//                parameters.put("registerEmail", "biju@gmail.com");
//                parameters.put("registerContact", "9461970054");
//                parameters.put("registerCity", "Alwar");
//                parameters.put("registerAddress", "Jaipur");
//                parameters.put("registerSecurityAns", "Dance");
//                parameters.put("registerPass", "1234");


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(Login.this);
        rQueue.add(request);
    }



    public void getmy(String res) {

        try {
            JSONObject j = new JSONObject(res);
            Log.d("LoginTest", j.toString());

            String sts = j.getString("msg");

            if (sts.equals("true")) {

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("email", et1.getText().toString());
                editor.putString("password", et2.getText().toString());
                editor.commit();

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(Login.this, "Plz try again..?" + sts, Toast.LENGTH_LONG).show();

            }

            // Toast.makeText(MainActivity.this, "" + sts, Toast.LENGTH_LONG).show();
        } catch (Exception g) {


        }


    }



    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
