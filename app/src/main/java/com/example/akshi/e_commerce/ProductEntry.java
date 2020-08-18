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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
/**
 * Created by akshi on 02-08-2017.
 */

public class ProductEntry extends AppCompatActivity {
    private static final int CAMERA = 100;

    private static final int MEDIA = 200;
    int request_code = 1;
    int PICK_IMAGE_REQUEST = 2;
    String imgDecodableString;
    private Uri fileUri;
    private static final int CROP = 1;
    int flag = 0;

    EditText edtPID, edtProductName, edtDescription, edtPrice;
    ImageView imgView;
    TextView txtBack;
    Button btnSubmit;
    Spinner spinner;
    public String pid, pname, pimage, description, price, list;
    DBHandler dbh;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_entry);


        edtPID = (EditText) findViewById(R.id.edtPID);
        edtProductName = (EditText) findViewById(R.id.edtProductName);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        imgView = (ImageView) findViewById(R.id.imgView);
        txtBack = (TextView) findViewById(R.id.txtBack);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        spinner = (Spinner) findViewById(R.id.spinner);


        dbh = new DBHandler(this);

        //spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list = spinner.getAdapter().getItem(position).toString();
                // Toast.makeText(getActivity(), "You Selected : "+list, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectimage();
                flag = 0;
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid = edtPID.getText().toString();
                pname = edtProductName.getText().toString();
                // pimage=imgView.getResources().toString();

                description = edtDescription.getText().toString();
                price = edtPrice.getText().toString();

                Toast.makeText(ProductEntry.this, "You Selected \n" + pid+"\n" + pname+"\n" + description+"\n" + price+"\n" + list, Toast.LENGTH_SHORT).show();

                long id = dbh.prodEntry(pid, pname, pimage, description, price, list);

                if (id > 0) {
                    Toast.makeText(ProductEntry.this, "Product Entry Entered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductEntry.this,AdminFragment.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ProductEntry.this, "No Product Entry Inserted", Toast.LENGTH_SHORT).show();
                }

                new ReadSelectedImageBackgroundTask().execute(new String[]{""});

            }
        });

    }

    //code for select image from gallary and camera
    public void selectimage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductEntry.this);
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
   /* private Bitmap getCircleBitmap(Bitmap bitmap, int pixel)
    {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(100, 100, 90, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MEDIA && resultCode == RESULT_OK && data != null)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(photo);


            Bitmap resizedBitmap = Bitmap.createScaledBitmap(photo, 200, 200, false);
            Bitmap conv_bm=getCircleBitmap(resizedBitmap, 100);
            imgView.setImageBitmap(conv_bm);


        }

        else if(requestCode == CAMERA && resultCode == RESULT_OK && data != null)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(photo);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(photo, 200, 200, false);
            Bitmap conv_bm=getCircleBitmap(resizedBitmap, 100);
            imgView.setImageBitmap(conv_bm);


        }


    }
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixel)
    {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(100, 100, 90, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;

    }*/

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

                Toast.makeText(ProductEntry.this, "data:" + data.getData(), Toast.LENGTH_LONG).show();

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();


                Log.d("1234", "imagepath= "+imgDecodableString);

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

    public class SaveSelectedImageBachgroundTask extends AsyncTask<String, Void, Void>
    {
        private ProgressDialog Dialog = new ProgressDialog(ProductEntry.this);

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

            Log.d("1234", "image path pe length= "+directory.list().length );


            // Create imageDir
            File mypath = new File(directory, "profile"+edtPID.getText().toString()+".jpg");

            Log.d("1234", "file path= "+mypath.getPath()+"   "+mypath.getName());


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
    public class ReadSelectedImageBackgroundTask extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(ProductEntry.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            Dialog.setMessage("Loading image from Sdcard..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {

            mBitmap = loadImageFromStorage("/data/data/com.example.a.groceryapp/app_imageDir");

            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();


            if (mBitmap != null)
                imgView.setImageBitmap(mBitmap);

        }

        private Bitmap loadImageFromStorage(String path) {

            Bitmap b = null;
            try {
                File f = new File(path, "profile"+edtPID.getText().toString()+".jpg");
                b = BitmapFactory.decodeStream(new FileInputStream(f));
                Dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return b;
        }

    }


    // Class with extends AsyncTask class
    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(ProductEntry.this);

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

            Log.d("1234", "image path= " );

            // Create imageDir
            File mypath = new File(directory, pid);

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