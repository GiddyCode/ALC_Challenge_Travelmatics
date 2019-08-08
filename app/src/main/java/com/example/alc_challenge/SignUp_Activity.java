package com.example.alc_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "SignUp_Activity";
    private EditText mEmail, mPassword;
    private Button CreateAccountBtn;
    private Button AltBtn;

    private FirebaseAuth OAuth;

    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        OAuth = FirebaseAuth.getInstance();

        InitializeFields();



        CreateAccountBtn = findViewById(R.id.Save_for_SignUp);
        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

        }

    private void CreateAccount() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password...", Toast.LENGTH_SHORT).show();
        }
        else {

            LoadingBar.setTitle("Creating New Account");
            LoadingBar.setMessage("Please Wait, While WE Create An Account For You");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();


            OAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                SendUsToActivity();
                                Toast.makeText(SignUp_Activity.this, "Created Account Sucessfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(SignUp_Activity.this, "Error :" +message, Toast.LENGTH_SHORT).show();

                            }
                            }
                    });

        }


    }

    private void SendUsToActivity() {
        Intent i = new Intent(SignUp_Activity.this, Input_Information_Activity.class);
        startActivity(i);
    }

    private void InitializeFields() {

        mEmail = (EditText) findViewById(R.id.Email_text);
        mPassword = (EditText) findViewById(R.id.Password_text);
        CreateAccountBtn = (Button) findViewById(R.id.Save_for_SignUp);

        LoadingBar = new ProgressDialog(this);
    }

}

