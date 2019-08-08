package com.example.alc_challenge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class TheMainActivity extends AppCompatActivity {

    private static final int GOOGLE_SIGN = 123;
    Button googleSignIN_Btn;
    Button mButton;
    FirebaseAuth fAuth;
    GoogleSignInClient mGoogleSign_IN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_activity_main);

        mButton = (Button) findViewById(R.id.sign_up_with_email);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TheMainActivity.this, SignUp_Activity.class);
                startActivity(i);
            }
        });


        fAuth =FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSign_IN = GoogleSignIn.getClient(this, googleSignInOptions);



        googleSignIN_Btn = (Button) findViewById(R.id.sign_up_with_google);
        googleSignIN_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInGoogle();
            }
        });
    }
    void SignInGoogle(){
        Intent signInIntent = mGoogleSign_IN.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    if (account != null) firebaseAuthWithGoogle(account);
                } catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Log.d("TAG", "Signed in Successfully");
                        FirebaseUser user = fAuth.getCurrentUser();

                        Intent i = new Intent(TheMainActivity.this, Input_Information_Activity.class);
                        startActivity(i);

                        Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Log.w("TAG", "Sign in Failed, Try Again", task.getException());
                        Toast.makeText(this, "SignIn Failed, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
