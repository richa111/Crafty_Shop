package com.example.akshi.e_commerce;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by akshi on 02-08-2017.
 */

public class payment  extends Fragment {

    Spinner spMode1;
    EditText edtAmt, edtName1, edtCardNo1, edtBank1, edtCheckEmail1, edtCVVNo1, edtDate1;
    Button btnPay1, btnReset;
    String strDate, email, amt, name, cardno, bank_name, emailid, cvv1;
    ImageButton btnCal1;
    TextView txtAmt,txtCardNo1,txtBank1,txtCVVNo1,txtDate1;
    static String string = "null";


    DBHandler dbh;

    int day, month, year, amount, newAmount;

    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment, null, false);

        spMode1 = (Spinner) view.findViewById(R.id.spMode1);
        edtAmt = (EditText) view.findViewById(R.id.edtAmt);
        edtName1 = (EditText) view.findViewById(R.id.edtName1);
        edtCardNo1 = (EditText) view.findViewById(R.id.edtCardNo1);
        edtBank1 = (EditText) view.findViewById(R.id.edtBank1);
        edtCheckEmail1 = (EditText) view.findViewById(R.id.edtCheckEmail1);
        edtCVVNo1 = (EditText) view.findViewById(R.id.edtCVVNo1);
        edtDate1 = (EditText) view.findViewById(R.id.edtDate1);
        btnPay1 = (Button) view.findViewById(R.id.btnPay1);
        btnReset = (Button) view.findViewById(R.id.btnReset);
        btnCal1 = (ImageButton) view.findViewById(R.id.btnCal1);

        txtAmt = (TextView)view.findViewById(R.id.txtAmt);
        txtCardNo1 = (TextView)view.findViewById(R.id.txtCardNo1);
        txtBank1 = (TextView)view.findViewById(R.id.txtBank1);
        txtDate1 = (TextView)view.findViewById(R.id.txtExpDate1);
        txtCVVNo1 = (TextView)view.findViewById(R.id.txtCvvNo1);

        dbh = new DBHandler(getActivity());


        //Getting system Date

        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        strDate = day + "/" + month + "/" + year;
        edtDate1.setText(strDate);


        //amount = Integer.parseInt(amt);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAmt.setText("");
                edtName1.setText("");
                edtCardNo1.setText("");
                edtBank1.setText("");
                edtDate1.setText("");
                edtCheckEmail1.setText("");
                edtCVVNo1.setText("");
                edtCardNo1.setText("");
            }
        });



        spMode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String str = spMode1.getAdapter().getItem(position).toString();

                // Toast.makeText(getActivity(), "position = "+str, Toast.LENGTH_SHORT).show();

                if (position == 3) {

                    string = "Wallet";

                    edtCardNo1.setEnabled(false);
                    edtBank1.setEnabled(false);
                    edtCVVNo1.setEnabled(false);
                    btnCal1.setEnabled(false);

                    //  txtCardNo1.setTextColor("FFDCD6D6");
                    // txtCardNo1.setEnabled(false);
                    //txtBank1.setEnabled(false);
                    //txtDate1.setEnabled(false);
                    //txtCVVNo1.setEnabled(false);

                }
                else
                {
                    string = "ATM";

                    edtCardNo1.setEnabled(true);
                    edtBank1.setEnabled(true);
                    edtCVVNo1.setEnabled(true);
                    btnCal1.setEnabled(true);


                    txtCardNo1.setEnabled(true);
                    txtBank1.setEnabled(true);
                    txtDate1.setEnabled(true);
                    txtCVVNo1.setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (string == "Wallet")
                {
                    amt = edtAmt.getText().toString();
                    name = edtName1.getText().toString();
                    emailid = edtCheckEmail1.getText().toString();

                    if ((amt.length() == 0) || (name.length() == 0) || (emailid.length() == 0) || (!isValidEmail(emailid))) {
                        if (amt.length() == 0) {
                            edtAmt.setError("Enter Amount");
                            edtAmt.requestFocus();
                        }
                        if (name.length() == 0) {
                            edtName1.setError("Enter Name");
                            edtName1.requestFocus();
                        }
                        if (emailid.length() == 0) {
                            edtCheckEmail1.setError("Enter Email ID");
                            edtCheckEmail1.requestFocus();
                        } else if (!isValidEmail(emailid)) {
                            edtCheckEmail1.setError("Invalid Email");
                            edtCheckEmail1.requestFocus();
                        }
                    } else {

                        email = edtCheckEmail1.getText().toString();
                        amount = Integer.parseInt(edtAmt.getText().toString());

                        String s = dbh.CheckEmail(email);
                        Toast.makeText(getActivity(), "email = " + s, Toast.LENGTH_SHORT).show();

                        if (email.equals(s)) {

                            int amt = dbh.CheckAmount(email);
                            Toast.makeText(getActivity(), "Amount = " + amt, Toast.LENGTH_SHORT).show();

                            if (amt < amount)
                            {
                                Toast.makeText(getActivity(), "Wallet does not contains the sufficient Amount", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                pay(amt, amount);
                            }
                        }
                        else
                        {
                            edtCheckEmail1.setError("Email does not registered");
                        }
                    }
                }
                else
                {
                    amt = edtAmt.getText().toString();
                    name = edtName1.getText().toString();
                    cardno = edtCardNo1.getText().toString();
                    bank_name = edtBank1.getText().toString();
                    emailid = edtCheckEmail1.getText().toString();
                    cvv1 = edtCVVNo1.getText().toString();

                    if ((amt.length() == 0) || (name.length() == 0) || (cardno.length() == 0)||(cardno.length()<16) || (bank_name.length() == 0) || (emailid.length() == 0)||(!isValidEmail(emailid)) || (cvv1.length() == 0)||(cvv1.length()<3))
                    {
                        if (amt.length() == 0) {
                            edtAmt.setError("Enter Amount");
                            edtAmt.requestFocus();
                        }
                        if (name.length() == 0) {
                            edtName1.setError("Enter Name");
                            edtName1.requestFocus();
                        }
                        if (cardno.length() == 0) {
                            edtCardNo1.setError("Enter Card No");
                            edtCardNo1.requestFocus();
                        } else if (cardno.length() < 16) {
                            edtCardNo1.setError("Enter Valid Card Number");
                            edtCardNo1.requestFocus();
                        }
                        if (bank_name.length() == 0) {
                            edtBank1.setError("Enter Bank Name");
                            edtBank1.requestFocus();
                        }
                        if (emailid.length() == 0) {
                            edtCheckEmail1.setError("Enter Email ID");
                            edtCheckEmail1.requestFocus();
                        }else
                        if (!isValidEmail(emailid)) {
                            edtCheckEmail1.setError("Invalid Email");
                            edtCheckEmail1.requestFocus();
                        }

                        if (cvv1.length() == 0) {
                            edtCVVNo1.setError("Enter CVV No");
                            edtCVVNo1.requestFocus();
                        }
                        else if (cvv1.length() < 3) {
                            edtCVVNo1.setError("Enter Valid CVV No");
                            edtCVVNo1.requestFocus();
                        }

                    }
                    else {

                        email = edtCheckEmail1.getText().toString();
                        amount = Integer.parseInt(edtAmt.getText().toString());

                        String s = dbh.CheckEmail(email);
                        Toast.makeText(getActivity(), "email = " + s, Toast.LENGTH_SHORT).show();

                        if (email.equals(s)) {

                            int amt = dbh.CheckAmount(email);
                            Toast.makeText(getActivity(), "Amount = " + amt, Toast.LENGTH_SHORT).show();

                            pay(amt, amount);

                        }
                        else
                        {
                            edtCheckEmail1.setError("Email does not registered");
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

    private void pay(int amt, int amount) {
        amt = amt - amount;
        int ii = dbh.UpdateAmount(email, amt);

        if (ii > 0)
        {
            Toast.makeText(getActivity(), "Payment has been Made", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Sorry Amount will not be Updated to your Wallet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String emailid)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailid);
        return matcher.matches();
    }


}



