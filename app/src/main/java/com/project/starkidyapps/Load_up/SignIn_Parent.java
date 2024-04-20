package com.project.starkidyapps.Load_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.starkidyapps.Main.MainScrean;
import com.project.starkidyapps.R;

public class SignIn_Parent extends AppCompatActivity{
    EditText ParentSingInAccount,ParentPassword;
    Button ParentSignIn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainScrean.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_parent);// Memanggil layout XML nya

        ParentSingInAccount = findViewById(R.id.ParentSingInAccount); //Input Email
        ParentPassword = findViewById(R.id.ParentPassword); // input password
        ParentSignIn = findViewById(R.id.ParentSignIn); //button next
        progressBar =findViewById(R.id.progressBar);//progressbar for loading
        mAuth = FirebaseAuth.getInstance();//Firebase

        ParentSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(ParentSingInAccount.getText());
                password = String.valueOf(ParentPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignIn_Parent.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }//if else empty Email

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignIn_Parent.this, "Please fill your Password", Toast.LENGTH_SHORT).show();
                    return;
                }//if else empty Password

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignIn_Parent.this,"Sign In Succesfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainScrean.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignIn_Parent.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }//oncomplite
                        });
            }//onclick
        });//click listener
    }//On Create
}//public class