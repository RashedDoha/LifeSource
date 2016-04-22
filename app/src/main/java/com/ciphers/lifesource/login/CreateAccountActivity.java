package com.ciphers.lifesource.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ciphers.lifesource.BaseActivity;
import com.ciphers.lifesource.R;
import com.ciphers.lifesource.utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;

import java.util.Map;

public class CreateAccountActivity extends BaseActivity {
    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        /**
         * Link layout elements from XML and setup the progress dialog
         */
        initializeScreen();
    }

    /**
     * Override onCreateOptionsMenu to inflate nothing
     *
     * @param menu The menu with which nothing will happen
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    /**
     * Link layout elements from XML and setup the progress dialog
     */
    public void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);
        LinearLayout linearLayoutCreateAccountActivity = (LinearLayout) findViewById(R.id.linear_layout_create_account_activity);


        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    /**
     * Open LoginActivity when user taps on "Sign in" textView
     */
    public void onSignInPressed(View view) {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Create new account using Firebase email/password provider
     */
    public void onCreateAccountPressed(View view) {
        String userName = mEditTextUsernameCreate.getText().toString();
        String email = mEditTextEmailCreate.getText().toString();
        String password = mEditTextPasswordCreate.getText().toString();
        if(isEmailValid(email) && isPasswordValid(password) && isUserNameValid(userName))
        {
            mAuthProgressDialog.show();
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Firebase ref = new Firebase(Constants.FIREBASE_URL);
            ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    mAuthProgressDialog.dismiss();
                    Log.d(LOG_TAG, "Successfully created user with uid: " + result.get("uid"));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    mAuthProgressDialog.dismiss();
                    switch (firebaseError.getCode()) {
                        case FirebaseError.EMAIL_TAKEN:
                            mEditTextEmailCreate.setError("Email Address already taken!");
                            break;
                        case FirebaseError.INVALID_PASSWORD:
                            mEditTextPasswordCreate.setError("Invalid Password!");
                            break;
                        default:
                            break;
                    }
                }
            });

        }

    }


    /**
     * Creates a new user in Firebase from the Java POJO
     */
    private void createUserInFirebaseHelper(final String encodedEmail) {
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (!email.equals("") && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail)
        {
            mEditTextEmailCreate.setError(String.format(getString(R.string.error_invalid_email_not_valid), email));
            return false;
        }
        return true;
    }

    private boolean isUserNameValid(String userName) {
        if(userName.equals("")){
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if(password.length() < 6)
        {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }

    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
