package in.sibin.expirydate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillsActivity extends AppCompatActivity {

    private static final String LOG_TAG =  "BillsActivity";
    Context context;
    RecyclerView recycler;
    RecyclerView.Adapter adapter;
    ArrayList<Bill>  bills = new ArrayList<>();
    Gson gson = new Gson();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_NEW_BILL = 2;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        context = BillsActivity.this;
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        String strBlls = Bill.getPrefString(context, Bill.spBills);
        if (strBlls != null)
        bills = gson.fromJson(strBlls, new TypeToken<List<Bill>>(){}.getType());
        adapter = new BillRVAdapter(context, bills);
        recycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_billing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:

                dispatchTakePictureIntent();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getPath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                Log.d(LOG_TAG, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Intent intent = new Intent(context, NewBillActivity.class);
            intent.putExtra(NewBillActivity.EXTRA_IMAGE, mCurrentPhotoPath);
            startActivityForResult(intent, REQUEST_NEW_BILL);

        } else if (requestCode == REQUEST_NEW_BILL && resultCode == RESULT_OK) {

            Bill bill = new Bill();
            bill.setImage(data.getStringExtra(NewBillActivity.EXTRA_IMAGE));
            bill.setName(data.getStringExtra(NewBillActivity.EXTRA_NAME));
            bill.setDescription(data.getStringExtra(NewBillActivity.EXTRA_DESC));
            bills.add(bill);
            adapter.notifyDataSetChanged();

            Bill.saveBills(context, Bill.spBills,  gson.toJson(bills));

        }
    }
}
