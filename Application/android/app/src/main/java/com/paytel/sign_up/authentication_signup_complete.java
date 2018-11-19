package com.paytel.sign_up;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.userDataObject;

public class authentication_signup_complete extends AppCompatActivity {
    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_complete);

        new_user = ((global_objects) getApplication()).getNew_user();

        //user data

        //fingerprint
        //useFingerprint();

        final Button btn_COMPLETE = findViewById(R.id.btn_complete);

        btn_COMPLETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent k = new Intent(authentication_signup_complete.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    void useFingerprint() {
        BiometricPrompt prompt = new BiometricPrompt.Builder(this)
                .setTitle("Verify")
                .setSubtitle("...")
                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build();
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
        prompt.authenticate(new CancellationSignal(), getMainExecutor(), callback);

        }
    }

