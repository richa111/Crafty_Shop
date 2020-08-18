package com.example.akshi.e_commerce;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by akshi on 02-08-2017.
 */

public class Registration extends AppCompatActivity {
    EditText edtFname,  edtEmail, edtContactnum, edtCity, edtAddress,  edtPassword ;

    RadioGroup rg;
    RadioButton rb;
    Button btnRegister, btnCancel;
    TextView txtBack;
    String fname, email, contactnum, city, address,  password,  gender ;
    String url="http://clothesaccessories.in//webservice/userregistration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtFname = (EditText) findViewById(R.id.edtFname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContactnum = (EditText) findViewById(R.id.edtContactnum);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        rg = (RadioGroup) findViewById(R.id.rg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        // txtBack = (TextView) findViewById(R.id.txtBack);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(id);
                gender = rb.getText().toString();
                fname= edtFname.getText().toString();
                email=edtEmail.getText().toString();
                city=edtCity.getText().toString();
                address=edtAddress.getText().toString();
                password=edtPassword.getText().toString();
                contactnum=edtContactnum.getText().toString();

                if (fname.length() == 0) {
                    edtFname.setError("First name not entered");
                    edtFname.requestFocus();
                }
                if (email.length() == 0) {
                    edtEmail.setError("Email ID not entered");
                    edtEmail.requestFocus();
                }else

                if (!isValidEmail(email)) {
                    edtEmail.setError("Invalid Email");
                    edtEmail.requestFocus();
                }
                if (contactnum.length() == 0) {
                    edtContactnum.setError("Contact Number not entered");
                    edtContactnum.requestFocus();
                }
                else if (contactnum.length()<=10)
                {
                    edtContactnum.setError("Enter Valid Contact Number");
                    edtContactnum.requestFocus();
                }
                if (city.length() == 0) {
                    edtCity.setError("City not entered");
                    edtCity.requestFocus();
                }

                if (address.length() == 0) {
                    edtAddress.setError("Address not entered");
                    edtAddress.requestFocus();
                }
                if (password.length()<8) {
                    edtPassword.setError("Password enter only 8  entered");
                    edtPassword.requestFocus();
                }

                register();

                cln();
                Intent i=new Intent(Registration.this,Login.class);
                startActivity(i);
            }
        });


    }
    public void register(){

        //Showing the progress dialog



        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                loading.dismiss();
                // Toast.makeText(Registration.this,""+s,Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loading.dismiss();

                Toast.makeText(Registration.this,"Plz try again.?",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("registerFname",fname);
                parameters.put("registerGender",gender);
                parameters.put("registerEmail", email);
                parameters.put("registerContact", contactnum);
                parameters.put("registerCity",city);
                parameters.put("registerAddress",address);
                parameters.put("registerPass", password);


                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(Registration.this);
        rQueue.add(request);

    }

    public void cln(){

        edtAddress.setText(null);
        edtCity.setText(null);
        edtContactnum.setText(null);
        edtEmail.setText(null);
        edtFname.setText(null);
        edtPassword.setText(null);
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}