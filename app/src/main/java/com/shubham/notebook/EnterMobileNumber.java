package com.shubham.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterMobileNumber extends AppCompatActivity {

    EditText enterNumber;
    Button getOtpButton, loginLinkBtn;
    ProgressBar enterMobileProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        enterNumber = findViewById(R.id.mobile_number);
        getOtpButton = findViewById(R.id.enter_mobile_btn);
        enterMobileProgressBar = findViewById(R.id.progress_bar_sending_otp);
        loginLinkBtn = findViewById(R.id.login_link_btn);


        loginLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enterNumber.getText().toString().trim().isEmpty()) {
                    if ((enterNumber.getText().toString().trim()).length() == 10) {

                        enterMobileProgressBar.setVisibility(View.VISIBLE);
                        getOtpButton.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enterNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                EnterMobileNumber.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        enterMobileProgressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        enterMobileProgressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);
                                        utility.showToast(EnterMobileNumber.this, e.getMessage());
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendotp, forceResendingToken);
                                        enterMobileProgressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifyOtp.class);
                                        intent.putExtra("mobile", enterNumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),VerifyOtp.class);
//                        intent.putExtra("mobile",enterNumber.getText().toString());
//                        startActivity(intent);
                    } else {
                        utility.showToast(EnterMobileNumber.this, "Enter correct mobile number");
                    }

                } else {
                    utility.showToast(EnterMobileNumber.this, "Enter mobile number");
                }
            }
        });
    }
}