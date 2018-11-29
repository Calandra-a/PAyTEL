package com.paytel.transaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.home;

public class transaction_facial extends AppCompatActivity {
    boolean fingerprintTest = false;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = 0;
        setContentView(R.layout.transaction_facial);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, transaction_facial_fragment.newInstance())
                    .commit();
        }

    }
    public void pictureComplete() {
        try {
            if(fingerprintTest) {
                Intent k = new Intent(transaction_facial.this, complete_transaction.class);
                startActivity(k);
            }
            else{
                Intent k = new Intent(transaction_facial.this, home.class);
                startActivity(k);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pictureIncomplete() {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, transaction_facial_fragment.newInstance())
                    .commit();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void biometricCheck() {
        BiometricPrompt prompt = null;
        if (Build.VERSION.SDK_INT >= 28) {
            prompt = new BiometricPrompt.Builder(this)
                    .setTitle("Verify Biometrics")
                    .setSubtitle("...")
                    .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .build();
        }
        BiometricPrompt.AuthenticationCallback callback = null;
        if (Build.VERSION.SDK_INT >= 28) {
            callback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    pictureComplete();
                }
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    fingerprintTest = true;
                    pictureComplete();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    counter++;
                    Toast.makeText(transaction_facial.this, "Fingerprint not recognized", Toast.LENGTH_SHORT).show();

                    if(counter == 2){
                        Toast.makeText(transaction_facial.this, "Fingerprint failed! Transaction Cancelled", Toast.LENGTH_SHORT).show();
                        onAuthenticationError(1, "Failed");
                        pictureComplete();
                    }
                }
            };
        }
        if (Build.VERSION.SDK_INT >= 28) {
            prompt.authenticate(new CancellationSignal(), getMainExecutor(), callback);
        }
    }
}

