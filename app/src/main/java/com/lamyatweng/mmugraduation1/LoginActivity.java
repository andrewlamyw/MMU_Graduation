package com.lamyatweng.mmugraduation1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://mmugraduation.firebaseio.com/");
        setContentView(R.layout.activity_login);
        final Activity activity = this;

        final TextInputLayout wrapperId = (TextInputLayout) findViewById(R.id.wrapper_email);
        final TextInputLayout wrapperPassword = (TextInputLayout) findViewById(R.id.wrapper_password);

        Button buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = wrapperId.getEditText().getText().toString();
                String password = wrapperPassword.getEditText().getText().toString();

                // Hide keyboard
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                ref.authWithPassword(id, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(activity, "Wrong email or password.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
