package com.game.samplecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btn_get_verification_code,btn_sign_in;
    EditText editTextPhone, editTextCode;
    FirebaseAuth mAuth;
    String codeSent;
    String phn="test";
    String[] perm = {Manifest.permission.SEND_SMS};
//    String[] perm1 = {Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);
        editTextPhone = findViewById(R.id.editTextPhone);
        btn_get_verification_code=findViewById(R.id.buttonGetVerificationCode);
        btn_sign_in=findViewById(R.id.buttonSignIn);
        phn=editTextPhone.getText().toString();

        btn_get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int per = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
                if(per != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,perm,123);
                }
                else {
                    sendVerificationCode();
                }
//                int per = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
//                int per1 = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET);
//                if(per != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,perm,123);
//                }
//                else if(per1 != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,perm1,123);
//                }
//                else if(per1 == PackageManager.PERMISSION_GRANTED){
//                    sendVerificationCode();
//                }
//                else {
//                    Random random = new Random();
//                    int randNo=random.nextInt(8000-6500)+6500;
//                    SmsManager manager = SmsManager.getDefault();
//                    manager.sendTextMessage(phn, null, String.valueOf(randNo), null, null);
//                    codeSent= String.valueOf(randNo);
//                    Toast.makeText(MainActivity.this, codeSent, Toast.LENGTH_SHORT).show();
//                }
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
//                int per1 = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET);
//                if(per1 != PackageManager.PERMISSION_GRANTED){
//                    if(editTextCode.getText().toString() == codeSent){
//                        Toast.makeText(getApplicationContext(),"Login Successfull", Toast.LENGTH_LONG).show();
//                        Log.d("Checking","Login successfully");
//                        Intent intent=new Intent(MainActivity.this,OperationActivity.class);
//                        intent.putExtra("Phone",editTextPhone.getText().toString());
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(MainActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else if(per1 == PackageManager.PERMISSION_GRANTED){
//                    verifySignInCode();
//                }
            }
        });

    }
    private void verifySignInCode(){
        String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successfull", Toast.LENGTH_LONG).show();
                            Log.d("Checking","Login successfully");
                            Intent intent=new Intent(MainActivity.this,OperationActivity.class);
                            intent.putExtra("Phone",editTextPhone.getText().toString());
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d("Checking","Incorrect Verification Code");
                                Toast.makeText(getApplicationContext(),"Incorrect Verification Code", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(){
        String phone = "+91"+editTextPhone.getText().toString();
        if(phone.equals("+91")){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }
        if(phone.length() != 13){
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,                 // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,      // Unit of timeout
                this,           // Activity (for callback binding)
                mCallbacks);           // OnVerificationStateChangedCallbacks
        mAuth.useAppLanguage();
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("Checking", "onVerificationCompleted:" + phoneAuthCredential);
            Toast.makeText(MainActivity.this, "Verification completed", Toast.LENGTH_SHORT).show();
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
            Log.w("Checking", "onVerificationFailed", e);
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("Checking", "onCodeSent: " + s);
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
            codeSent = s;
        }
    };
}
