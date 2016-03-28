package in.sibin.expirydate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetEntryActivity extends AppCompatActivity {

    Context context;
    Firebase ref;
    Product product;
    ProgressDialog progressDialog;
    String barcode;
    private static String SERVER_URL = "https://expiry-date-detector.firebaseio.com/";
    TextView tvName, tvBarcode, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_entry);

        barcode = getIntent().getStringExtra(MainActivity.EXTRA_BAR_CODE);

        context = GetEntryActivity.this;
        Firebase.setAndroidContext(context);
        ref = new Firebase(SERVER_URL);

        tvName = (TextView) findViewById(R.id.tvProduct);
        tvBarcode = (TextView) findViewById(R.id.tvBarCode);
        tvDate = (TextView) findViewById(R.id.tvDate);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait while we load your product info");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ref.child("products").child(barcode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Log.d("Product", dataSnapshot.getValue().toString());
                product = dataSnapshot.getValue(Product.class);
                tvName.setText(product.getName());
                tvBarcode.setText(product.getBarcode());

                Date date = new Date(product.getDate());
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                tvDate.setText(formatter.format(date));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addReminder(View v){


        new AlertDialog.Builder(context)
                .setTitle("Set reminder")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        Log.d("GetEntry", product.getName());

        Intent intent = new Intent(this, ShowNotification.class);
        intent.putExtra(ShowNotification.PRODUCT_NAME, product.getName());
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

        long oneDay = 1000 * 60 * 60 * 24;

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, product.getDate() - oneDay , pintent);

    }

}
