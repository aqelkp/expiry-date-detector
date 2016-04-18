package in.sibin.expirydate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NihalActivity extends AppCompatActivity {

    Context context;
    private int RESULT_ADD_ENTRY = 1;
    EditText etRollNumber;
    String barcode;
    private boolean mReturningWithResult = false;

    String[] rollNumbers = {"CH13B006", "CH13B010", "NA13B001", "NA13B007", "CS13B062", "ME13B154"};
    String[] departments = {"Web and mobile operations", "Security", "Marketing and Sales", "Finance", "Cultural Affairs Secretary", "Cultural Affairs Secretary"};
    String[] subDepartments = {"Mobile Operations", "OAT", "Ticket Sales", "Catering" , "Cultural Affairs Secretary", "Cultural Affairs Secretary"};
    String[] names = {"Aqel Ahammad KP", "Arun P", "Nihal Abdussamad", "Arun Ramakrishnan",  "Shreyas Harish", "Gokulesh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nihal);

        context = this;

        etRollNumber = (EditText) findViewById(R.id.etRollNumber);
    }


    public void scanRoll(View v){

        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, RESULT_ADD_ENTRY);

    }

    public void searchRoll(View v){

        barcode = etRollNumber.getText().toString();
        searchRollNumber(barcode);
    }

    private void searchRollNumber(String barcode) {

        int position = -1;

        for (int i = 0; i< rollNumbers.length; i++){
            if (barcode.equals(rollNumbers[i])) position = i;
        }

        if (position == -1){
            FragmentManager fm = getSupportFragmentManager();
            NihalEntryDenied fragment = new NihalEntryDenied();
            fragment.show(fm, "Dialog");
        }
        else {

            FragmentManager fm = getSupportFragmentManager();
            NihalDetailFragment fragment = new NihalDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(NihalDetailFragment.EXTRA_NAME, names[position]);
            bundle.putString(NihalDetailFragment.EXTRA_ROLLNUMBER, rollNumbers[position]);
            bundle.putString(NihalDetailFragment.EXTRA_DEPT, departments[position]);
            bundle.putString(NihalDetailFragment.EXTRA_SUB, subDepartments[position]);
            fragment.setArguments(bundle);
            fragment.show(fm, "Dialog");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_ADD_ENTRY){

            if (resultCode == Activity.RESULT_OK){

                Log.d("barcode", data.getStringExtra("result"));
                barcode = data.getStringExtra("result");
//                etRollNumber.setText(barcode);
                mReturningWithResult = true;

            }

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mReturningWithResult) {
            // Commit your transactions here.
            searchRollNumber(barcode);
        }
        // Reset the boolean flag back to false for next time.
        mReturningWithResult = false;
    }
}
