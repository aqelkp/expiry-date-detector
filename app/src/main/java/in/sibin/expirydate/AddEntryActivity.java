package in.sibin.expirydate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    Context context;
    EditText etName, etDate;
    Firebase ref;
    private static String SERVER_URL = "https://expiry-date-detector.firebaseio.com/";
    String barcode;
    TextView tvBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        etName = (EditText) findViewById(R.id.etName);
        etDate = (EditText) findViewById(R.id.etDate);
        tvBarCode = (TextView) findViewById(R.id.tvBarCode);
        barcode = getIntent().getStringExtra(MainActivity.EXTRA_BAR_CODE);
        tvBarCode.setText(barcode);

        context = AddEntryActivity.this;
        Firebase.setAndroidContext(context);
        ref = new Firebase(SERVER_URL);

    }

    public void saveEntry(View v)  {

        /*
            Take inputs
            Convert date to long
            save to firebase
            Show success message
         */

        String name = etName.getText().toString();
        String dateString = etDate.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Enter date in the specified format", Toast.LENGTH_SHORT).show();
            return;
        }
        long milliseconds = date.getTime();

        Product product = new Product();
        product.setBarcode(barcode);
        product.setName(name);
        product.setDate(milliseconds);

        ref.child("products").child(barcode).setValue(product, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Toast.makeText(context, "Product info successfully added", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
