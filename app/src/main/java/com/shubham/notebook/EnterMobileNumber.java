package com.shubham.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class EnterMobileNumber extends AppCompatActivity {

    EditText enterNumber;
    CountryCodePicker countryCode;
    Button getOtpButton;
//    Button loginLinkBtn;
    ProgressBar enterMobileProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        countryCode = findViewById(R.id.country_code);
        enterNumber = findViewById(R.id.mobile_number);
        getOtpButton = findViewById(R.id.enter_mobile_btn);
        enterMobileProgressBar = findViewById(R.id.progress_bar_sending_otp);

        countryCode.registerCarrierNumberEditText(enterNumber);
//        loginLinkBtn = findViewById(R.id.login_link_btn);


//        loginLinkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!enterNumber.getText().toString().trim().isEmpty()) {
//                        (enterNumber.getText().toString().trim()).length() == 10

                        countryCode.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                            @Override
                            public void onValidityChanged(boolean isValidNumber) {
                                // your code
                                enterMobileProgressBar.setVisibility(View.VISIBLE);
                                getOtpButton.setVisibility(View.INVISIBLE);


                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        countryCode.getFullNumberWithPlus().toString(),
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
//                                                utility.showToast(EnterMobileNumber.this, e.getLocalizedMessage());
                                                Toast.makeText(EnterMobileNumber.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                super.onCodeSent(backendotp, forceResendingToken);
                                                enterMobileProgressBar.setVisibility(View.GONE);
                                                getOtpButton.setVisibility(View.VISIBLE);
                                                Intent intent = new Intent(getApplicationContext(), VerifyOtp.class);
                                                intent.putExtra("mobile", countryCode.getFullNumberWithPlus().toString());
                                                intent.putExtra("backendotp", backendotp);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                );
                            }
                        });
//                        if (!countryCode.isValidFullNumber()) {



//                        Intent intent = new Intent(getApplicationContext(),VerifyOtp.class);
//                        intent.putExtra("mobile",enterNumber.getText().toString());
//                        startActivity(intent);
//                        } else {
//                            utility.showToast(EnterMobileNumber.this, "Enter Valid mobile number");
//                        }

                    } else {
                        utility.showToast(EnterMobileNumber.this, "Enter mobile number");
                    }

            }
        });
    }
}