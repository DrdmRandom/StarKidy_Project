package com.project.starkidyapps.Load_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SignUp extends AppCompatActivity {

    EditText AccountInput, PhoneInput, PasswordInput, confirm_password;
    CheckBox app_term, school_term;
    Button btnNext;
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
        setContentView(R.layout.sign_up_one); // Memanggil layout XML nya

        AccountInput = findViewById(R.id.AccountInput); //Input Email
        PhoneInput = findViewById(R.id.PhoneInput); // Input Phone Number
        PasswordInput = findViewById(R.id.PasswordInputt); // input password
        confirm_password = findViewById(R.id.confirm_password); // input confirm password

        app_term = findViewById(R.id.app_term); //check box app term
        school_term = findViewById(R.id.school_term); //check box app term

        btnNext = findViewById(R.id.btnNext); //button next

        progressBar =findViewById(R.id.progressBar);//progressbar for loading

        mAuth = FirebaseAuth.getInstance();//Firebase

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(AccountInput.getText());
                password = String.valueOf(PasswordInput.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
                    return;
                }//if else empty Email

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this,"Please fill your Password",Toast.LENGTH_SHORT).show();
                    return;
                }//if else empty Password

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUp.this, "Account Created",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, SignIn_Parent.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Account Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    } // onCreaete
} //public class