package com.example.wmsx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mLoginBtn;
    private FirebaseAuth mAuth;
    private Toolbar mToolBar;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolBar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mEmail = (TextInputLayout) findViewById(R.id.loginEmail);
        mPassword = (TextInputLayout) findViewById(R.id.login_Pass);
        mLoginBtn = (Button) findViewById(R.id.Login_Complete_Btn);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = mEmail.getEditText().getText().toString();
                String Password = mPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Password)) {} else {
                    mRegProgress.setTitle("Login Users");
                    mRegProgress.setMessage("Please wait checking Email and Password");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    loginUser(Email,Password);

                }


            }
        });


    }

    private void loginUser(String email, String password) {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                     mRegProgress.dismiss();
                     Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                     mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(mainIntent);
                     finish();
                    }


                    else {

                        mRegProgress.hide();
                        Toast.makeText(LoginActivity.this,"Authentication Failure", Toast.LENGTH_LONG).show();
                    }


                }
            });

    }
}
