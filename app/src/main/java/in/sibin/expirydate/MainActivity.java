package in.sibin.expirydate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Context context;
    private static int RESULT_ADD_ENTRY = 11;
    private static int RESULT_CHECK_ENTRY = 12;

    private static String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
    }

    public void addEntry(View v){

        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, RESULT_ADD_ENTRY);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_ADD_ENTRY){

            if (resultCode == Activity.RESULT_OK){

                Log.d(LOG_TAG, data.getStringExtra("result"));

            }

        }
    }


}
