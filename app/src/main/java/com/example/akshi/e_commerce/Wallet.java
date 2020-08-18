package com.example.akshi.e_commerce;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import android.icu.util.Calendar;

/**
 * Created by akshi on 02-08-2017.
 */

public class Wallet extends Fragment implements ImageButton.OnClickListener
{
    EditText edtDate;
    String strDate,email,amt,name,cardno,bank_name,emailid,cvv1;
    ImageButton btnCal;
    Spinner spMode;
    EditText edtAmount,edtName,edtCardNo,edtBank,edtCheckEmail,edtCVVNo;
    Button btnAdd,btnCancelAdd;

    DBHandler dbh;

    int day,month,year,amount,newAmount;

    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wallet,null,false);

        spMode = (Spinner)view.findViewById(R.id.spMode);
        edtAmount = (EditText)view.findViewById(R.id.edtAmount);
        edtName = (EditText)view.findViewById(R.id.edtName);
        edtCardNo = (EditText)view.findViewById(R.id.edtCardNo);
        edtBank = (EditText)view.findViewById(R.id.edtBank);
        edtCheckEmail = (EditText)view.findViewById(R.id.edtCheckEmail);
        edtCVVNo = (EditText)view.findViewById(R.id.edtCVVNo);

        btnAdd = (Button)view.findViewById(R.id.btnAdd);
        btnCancelAdd = (Button)view.findViewById(R.id.btnCancelAdd);

        edtDate = (EditText)view.findViewById(R.id.edtDate);
        btnCal = (ImageButton)view.findViewById(R.id.btnCal);
        btnCal.setOnClickListener(this);

        dbh = new DBHandler(getActivity());


        //Getting system Date

        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        strDate = day + "/" + month + "/" + year;
        edtDate.setText(strDate);




        //amount = Integer.parseInt(amt);
        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAmount.setText("");
                edtName.setText("");
                edtCardNo.setText("");
                edtBank.setText("");
                edtDate.setText("");
                edtCheckEmail.setText("");
                edtCVVNo.setText("");
                edtCardNo.setText("");
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                amt = edtAmount.getText().toString();
                name = edtName.getText().toString();
                cardno = edtCardNo.getText().toString();
                bank_name = edtBank.getText().toString();
                emailid = edtCheckEmail.getText().toString();
                cvv1 = edtCVVNo.getText().toString();




                if ((amt.length() == 0) || (name.length() == 0) || (cardno.length() == 0)||(cardno.length()<16) || (bank_name.length() == 0) || (emailid.length() == 0)||(!isValidEmail(emailid)) || (cvv1.length() == 0)||(cvv1.length()<3))
                {
                    if (amt.length() == 0) {
                        edtAmount.setError("Enter Amount");
                        edtAmount.requestFocus();
                    }
                    if (name.length() == 0) {
                        edtName.setError("Enter Name");
                        edtName.requestFocus();
                    }
                    if (cardno.length() == 0) {
                        edtCardNo.setError("Enter Card No");
                        edtCardNo.requestFocus();
                    } else if (cardno.length() < 16) {
                        edtCardNo.setError("Enter Valid Card Number");
                        edtCardNo.requestFocus();
                    }
                    if (bank_name.length() == 0) {
                        edtBank.setError("Enter Bank Name");
                        edtBank.requestFocus();
                    }
                    if (emailid.length() == 0) {
                        edtCheckEmail.setError("Enter Email ID");
                        edtCheckEmail.requestFocus();
                    }else
                    if (!isValidEmail(emailid)) {
                        edtCheckEmail.setError("Invalid Email");
                        edtCheckEmail.requestFocus();
                    }

                    if (cvv1.length() == 0) {
                        edtCVVNo.setError("Enter CVV No");
                        edtCVVNo.requestFocus();
                    }
                    else if (cvv1.length() < 3) {
                        edtCVVNo.setError("Enter Valid CVV No");
                        edtCVVNo.requestFocus();
                    }

                }
                else{

                    email = edtCheckEmail.getText().toString();
                    amount = Integer.parseInt(edtAmount.getText().toString());

                    //Snackbar.make(view,""+exp,Snackbar.LENGTH_SHORT).setAction("Action",null).show();

                    if (amount < 100) {
                        edtAmount.setError("Enter amount more than Rs.99");
                        edtAmount.requestFocus();
                        edtAmount.setText("");
                    }
                    else
                    {
                        // Toast.makeText(Wallet.this, ""+amount, Toast.LENGTH_SHORT).show();
                        //amt = edtAmount.getText().toString();
                        String s = dbh.CheckEmail(email);
                        Toast.makeText(getActivity(), "email = " + s, Toast.LENGTH_SHORT).show();

                        if (email.equals(s)) {

                            int amt = dbh.CheckAmount(email);
                            Toast.makeText(getActivity(), "Amount = " + amt, Toast.LENGTH_SHORT).show();

                            if (amt == 0)
                            {
                                long id1 = dbh.addAmount(email, amount);

                                if (id1 > 0)
                                {
                                    Toast.makeText(getActivity(), "Amount Added to your Wallet", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Sorry Amount will not be added to your Wallet", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                amt = amt + amount;
                                int ii = dbh.UpdateAmount(email, amt);

                                if (ii > 0)
                                {
                                    Toast.makeText(getActivity(), "Amount Updated to your Wallet", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Sorry Amount will not be Updated to your Wallet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Please First Register", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    return true;
                } else {
                    return false;
                }
            }
        });


        return view;
    }

    private boolean isValidEmail(String emailid) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailid);
        return matcher.matches();
    }


    @Override
    public void onClick(View view) {
        new DatePickerDialog(getActivity(),datePicker,day,month,year).show();


    }

    public DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth)
        {
            year = yr;
            month = monthOfYear;
            day = dayOfMonth;

            strDate = day + "/" + month + "/" + year;
            edtDate.setText(strDate);

            Toast.makeText(getActivity(), ""+strDate, Toast.LENGTH_SHORT).show();
        }
    };



}
