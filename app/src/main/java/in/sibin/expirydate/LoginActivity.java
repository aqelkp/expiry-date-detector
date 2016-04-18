package in.sibin.expirydate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity" ;
    Context context;
    EditText etEmail, etPassword;
    Firebase ref;
    Firebase.AuthResultHandler authResultHandler;
    ProgressDialog progress;
    private static String SERVER_URL = "https://expiry-date-detector.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        context = LoginActivity.this;
        Firebase.setAndroidContext(context);
        ref = new Firebase(SERVER_URL);


    }

    public void signIn(View v){

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        login(email, password);
    }

    public void signUp(View v){

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        createNewUser(email, password);


    }

    private void login(final String email, final String password){

        progress = ProgressDialog.show(this, "Loading", "Logging in", true);
        progress.setCancelable(false);
        // Create a handler to handle the result of the authentication
        authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                Log.d(LOG_TAG, "Authenticated");
                progress.dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();

            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                Log.d(LOG_TAG, firebaseError.getMessage());
                progress.dismiss();
                Snackbar
                        .make(etEmail, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        };

        ref.authWithPassword( email, password, authResultHandler);
    }

    private void createNewUser(final String email, final String password) {


        progress = ProgressDialog.show(this, "Loading", "Creating new account", true);
        progress.setCancelable(false);
        /*
            Create a new user - Includes checking existing user
         */
        ref.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {

                progress.setMessage("Registering account");

                // Create a handler to handle the result of the authentication
                authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData
                        Log.d(LOG_TAG, "Authenticated");
                        progress.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        Log.d(LOG_TAG, firebaseError.getMessage());
                        Snackbar
                                .make(etEmail, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                        progress.dismiss();
                    }
                };

                ref.authWithPassword( email, password, authResultHandler);

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.d(LOG_TAG, firebaseError.getMessage());
                showSnackBar(firebaseError.getMessage());
                progress.dismiss();
            }
        });
    }

    private void showSnackBar(String message) {
        Snackbar
                .make(etEmail, message, Snackbar.LENGTH_SHORT)
                .show();
    }

}
