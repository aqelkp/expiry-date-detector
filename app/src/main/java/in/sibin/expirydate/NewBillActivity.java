package in.sibin.expirydate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class NewBillActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE =  "extraImage";
    public static final String EXTRA_NAME =  "extraName";
    public static final String EXTRA_DESC =  "extraDesc";
    ImageView ivPic;
    EditText etName, etDesc;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        ivPic = (ImageView) findViewById(R.id.ivPic);
        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDesc);

        image = getIntent().getStringExtra(EXTRA_IMAGE);
        Glide.with(this)
                .load(new File( image )) // Uri of the picture
                .into(ivPic);
    }

    public void save(View v){

        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_IMAGE, image);
        returnIntent.putExtra(EXTRA_NAME, name);
        returnIntent.putExtra(EXTRA_DESC, desc);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();



    }
}
