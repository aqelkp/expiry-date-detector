package in.sibin.expirydate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    Context context;
    private static int RESULT_ADD_ENTRY = 11;
    private static int RESULT_CHECK_ENTRY = 12;



    private static String LOG_TAG = "MainActivity";
    public static String EXTRA_BAR_CODE = "barcode";

    Firebase ref;
    private static String SERVER_URL = "https://expiry-date-detector.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        Firebase.setAndroidContext(context);
        ref = new Firebase(SERVER_URL);

        if (ref.getAuth() == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void addEntry(View v){

        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, RESULT_ADD_ENTRY);

    }

    public void getEntry(View v){

        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, RESULT_CHECK_ENTRY);

    }

    public void signOut(View v){

        ref.unauth();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);

    }

    public void saveBill(View v){

        Intent intent = new Intent(context, BillsActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_ADD_ENTRY){

            if (resultCode == Activity.RESULT_OK){

                Intent intent = new Intent(context, AddEntryActivity.class);
                intent.putExtra(EXTRA_BAR_CODE, data.getStringExtra("result"));
                startActivity(intent);

            }

        } else if (requestCode == RESULT_CHECK_ENTRY){

            if (resultCode == Activity.RESULT_OK){

                Intent intent = new Intent(context, GetEntryActivity.class);
                intent.putExtra(EXTRA_BAR_CODE, data.getStringExtra("result"));
                startActivity(intent);

            }

        }
    }


}
