package com.shubham.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {

    TextView showNumber, resendOtp;
    EditText input_otp1,input_otp2,input_otp3,input_otp4,input_otp5,input_otp6;
    Button verifyOtp;
    String getOtpBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        showNumber = findViewById(R.id.text_view_mobile);
        resendOtp = findViewById(R.id.text_resend_opt);
        verifyOtp = findViewById(R.id.verify_otp_btn);

        input_otp1 = findViewById(R.id.input_otp1);
        input_otp2 = findViewById(R.id.input_otp2);
        input_otp3 = findViewById(R.id.input_otp3);
        input_otp4 = findViewById(R.id.input_otp4);
        input_otp5 = findViewById(R.id.input_otp5);
        input_otp6 = findViewById(R.id.input_otp6);

        showNumber.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")
        ));

        getOtpBackend = getIntent().getStringExtra("backendotp");

        ProgressBar progressBarVerifyotp = findViewById(R.id.progress_bar_verify_otp);

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!input_otp1.getText().toString().trim().isEmpty()&&!input_otp2.getText().toString().trim().isEmpty()&&!input_otp3.getText().toString().trim().isEmpty()&&!input_otp4.getText().toString().trim().isEmpty()&&!input_otp5.getText().toString().trim().isEmpty()&&!input_otp6.getText().toString().trim().isEmpty()){
                    String enterCodeOtp = input_otp1.getText().toString() +
                            input_otp2.getText().toString()+
                            input_otp3.getText().toString()+
                            input_otp4.getText().toString()+
                            input_otp5.getText().toString()+
                            input_otp6.getText().toString();

                    if (getOtpBackend!=null){
                        progressBarVerifyotp.setVisibility(View.VISIBLE);
                        verifyOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getOtpBackend,enterCodeOtp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBarVerifyotp.setVisibility(View.GONE);
                                verifyOtp.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    // finish activity like finish()
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    utility.showToast(VerifyOtp.this,"Enter Correct OTP");
                                }
                            }
                        });
                    }else {
                        utility.showToast(VerifyOtp.this,"please check internet connection");
                    }

//                    utility.showToast(VerifyOtp.this,"otp verify");
                }else {
                    utility.showToast(VerifyOtp.this,"enter all number");
                }
            }
        });

        numberotpmove();

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                utility.showToast(VerifyOtp.this, e.getMessage());
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(newbackendotp, forceResendingToken);
                                getOtpBackend = newbackendotp;
                                utility.showToast(VerifyOtp.this,"OTP send successfully");
                            }
                        }
                );
            }
        });

    }

    private void numberotpmove(){
        input_otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input_otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input_otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input_otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input_otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input_otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}