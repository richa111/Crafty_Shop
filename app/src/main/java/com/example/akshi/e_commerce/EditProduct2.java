package com.example.akshi.e_commerce;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by akshi on 02-08-2017.
 */

public class EditProduct2 extends AppCompatActivity {
    private static final int CAMERA = 100;

    private static final int MEDIA = 200;
    int request_code = 1;
    int PICK_IMAGE_REQUEST = 2;
    String imgDecodableString;
    private Uri fileUri;
    private static final int CROP = 1;
    int flag = 0;

    EditText edtPID, edtProductName, edtDescription, edtAmount;
    ImageView imgView;
    String Pid, Pname, Description, Price;
    Button btnSave;
    DBHandler dbh;
    String imagePath = "http://clothesaccessories.in/webservice/img/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product2);

        edtProductName = (EditText) findViewById(R.id.edtProductName);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        edtPID = (EditText) findViewById(R.id.edtPID);
        btnSave = (Button) findViewById(R.id.btnSave);
        imgView = (ImageView) findViewById(R.id.imgView);


        dbh = new DBHandler(this);
        //Intent intent = getIntent();

        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectimage();
                flag = 0;
            }
        });


        Bundle bundle = getIntent().getExtras();


        Pid = bundle.getString("Pid");
        Pname = bundle.getString("Pname");
        Description = bundle.getString("Description");
        Price = bundle.getString("Price");


        edtPID.setText(Pid);
        edtProductName.setText(Pname);
        edtDescription.setText(Description);
        edtAmount.setText(Price);

        Log.d("1234", "on create");

        new ReadSelectedImageBackgroundTask().execute(new String[]{});


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Pid = Integer.parseInt(edtPID.getText().toString());
                String Pname = edtProductName.getText().toString();
                String Description = edtDescription.getText().toString();
                String Price = edtAmount.getText().toString();

                int id = dbh.updateEntry(Pid, Pname, Description, Price);
                if (id > 0) {
                    Toast.makeText(EditProduct2.this, "Record updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProduct2.this, "No Record Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    // Class with extends AsyncTask class
    public class ReadSelectedImageBackgroundTask extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(EditProduct2.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            Dialog.setMessage("Loading image from Sdcard..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {

            Log.d("1234", "in execute");
            mBitmap = loadImageFromStorage(imagePath);

            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();


            if (mBitmap != null) {

                Log.d("1234", "setting image");

                imgView.setImageBitmap(mBitmap);
            }

        }

        private Bitmap loadImageFromStorage(String path) {

            Bitmap b = null;
            try {


                File f = new File(path, "profile" + Pid + ".jpg");


                Log.d("1234", f.getPath() + " " + f.getName());

                b = BitmapFactory.decodeStream(new FileInputStream(f));
                Dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return b;
        }

    }

    //code for select image from gallary and camera
    public void selectimage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProduct2.this);
        builder.setTitle("Product Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


                    startActivityForResult(cameraIntent, CAMERA);
                } else if (items[item].equals("Choose from gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == request_code) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_EDIT, Uri.parse(data.getData().toString()));
                startActivity(i);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "image is selected", Toast.LENGTH_LONG).show();
                // Get the Image from data

                Toast.makeText(EditProduct2.this, "data:" + data.getData(), Toast.LENGTH_LONG).show();

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();


                Log.d("1234", "imagepath= " + imgDecodableString);

                new SaveSelectedImageBachgroundTask().execute(new String[]{imgDecodableString});


                new LoadImagesFromSDCard().execute(new String[]{imgDecodableString});

            }
        } else if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(photo);
            // Bitmap resizedBitmap = Bitmap.createScaledBitmap(photo, 200, 200, false);
            //Bitmap conv_bm=getCircleBitmap(resizedBitmap, 100);
            // imgView.setImageBitmap(conv_bm);


        }

    }

    public class SaveSelectedImageBachgroundTask extends AsyncTask<String, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(EditProduct2.this);

        Bitmap mBitmap;

        protected void onPreExecute() {

            Log.d("1234", "image path= on pre ");
            Dialog.setMessage("Saving image to Sdcard..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {

            Log.d("1234", "image path= back");
            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            try {

                bitmap = BitmapFactory.decodeFile(urls[0]);

                if (bitmap != null) {
                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);
                    bitmap.recycle();

                    if (newBitmap != null) {
                        String s = saveToInternalStorage(newBitmap);
                        Log.d("1234", "image path= " + s);
                    }
                }
            } catch (Exception e) {
                cancel(true);
            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
        }

        private String saveToInternalStorage(Bitmap bitmapImage) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

            Log.d("1234", "image path pe length= " + directory.list().length);


            // Create imageDir
            File mypath = new File(directory, "profile" + edtPID.getText().toString() + ".jpg");

            Log.d("1234", "file path= " + mypath.getPath() + "   " + mypath.getName());


            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return directory.getAbsolutePath();
        }

    }
    // Class with extends AsyncTask class
    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(EditProduct2.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            Dialog.setMessage("Loading image from Sdcard..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            try {

                bitmap = BitmapFactory.decodeFile(urls[0]);

                if (bitmap != null) {

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);
                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;

                        // saveToInternalStorage(mBitmap);
                    }
                }
            } catch (Exception e) {
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (mBitmap != null)
                imgView.setImageBitmap(mBitmap);

        }

        private String saveToInternalStorage(Bitmap bitmapImage) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

            Log.d("1234", "image path= ");

            // Create imageDir
            File mypath = new File(directory, Pid);

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return directory.getAbsolutePath();
        }

    }
}

