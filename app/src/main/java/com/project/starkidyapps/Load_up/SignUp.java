    package com.project.starkidyapps.Load_up;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextUtils;
    import android.text.TextWatcher;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.view.Gravity;
    import android.view.Window;
    import android.widget.LinearLayout;
    import android.graphics.Color;
    import android.graphics.PorterDuff;
    import androidx.core.content.ContextCompat;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.google.firebase.firestore.DocumentSnapshot;






    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.project.starkidyapps.Main.MainScrean;
    import com.project.starkidyapps.R;

    public class SignUp extends AppCompatActivity {

        EditText AccountInput, PhoneInput,SKidInput, PasswordInput, confirm_password;
        CheckBox app_term, school_term;
        Button btnNext;
        ProgressBar progressBar;
        ProgressBar passwordStrengthBar;

        FirebaseAuth mAuth;
        FirebaseFirestore firestore;

        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        private TextView passwordStrengthView;
        private int[] passwordStrengthColors = {
                Color.RED, // Very Weak
                Color.rgb(255, 165, 0), // Weak
                Color.GREEN, // Good
                Color.rgb(0, 100, 0) // Strong
        };

        private String[] passwordStrengthLevels;

        @Override
        protected void onStart() {
            super.onStart();
            mAuth = FirebaseAuth.getInstance();
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
            setContentView(R.layout.sign_up);

            // Initialize UI components
            initializeUI();

            // Now you can initialize passwordStrengthLevels
            passwordStrengthLevels = new String[]{
                    getString(R.string.password_strength_very_weak),
                    getString(R.string.password_strength_weak),
                    getString(R.string.password_strength_good),
                    getString(R.string.password_strength_strong)
            };

            PasswordInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }//beforeTextChanged

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        int passwordStrength = getPasswordStrength(s.toString());
                        passwordStrengthBar.setProgress(passwordStrength);
                        // Update color and text
                        updatePasswordStrengthView(passwordStrength);
                    } else {
                        passwordStrengthBar.setProgress(0);
                        passwordStrengthView.setText(""); // Assuming you have a TextView for the strength text
                    }
                } // onTextChanged

                @Override
                public void afterTextChanged(Editable s) {
                } // afterTextChanged
            }); //password input listerner

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performSignUp();
                }
            });// onclick listener
        }

        private boolean isPhoneNumberValid(String phoneNumber) {
            return phoneNumber.startsWith("08") || phoneNumber.startsWith("+62");
        } //isPhoneNumberValid

        // Call this method after the user has signed up successfully
        private void linkParentToChild(String parentEmail, String skId, String parentPhoneNumber) {
            // Query the Firestore database to find the student document with the matching SK number
            db.collection("students")
                    .whereEqualTo("SK", skId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                // Found matching documents, process them
                                for (DocumentSnapshot studentDoc : querySnapshot.getDocuments()) {
                                    // Check if the phone number matches
                                    if (parentPhoneNumber.equals(studentDoc.getString("parentPhoneNumber"))) {
                                        // Update the student document with the parent's email
                                        studentDoc.getReference().update("parentEmail", parentEmail)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("UpdateSuccess", "Parent email updated for student ID: " + skId);
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("UpdateFailure", "Failed to update parent email", e);
                                                });
                                    }
                                }
                            } else {
                                // No matching document, handle the case where the student ID does not exist
                                Log.e("QueryEmpty", "No matching student document found for ID: " + skId);
                            }
                        } else {
                            // The query failed, handle the error
                            Log.e("QueryFailure", "Failed to perform query for student ID", task.getException());
                        }
                    });
        } // linkParentToChild

        private void performSignUp() {
            progressBar.setVisibility(View.VISIBLE);
            String email = AccountInput.getText().toString().trim();
            String password = PasswordInput.getText().toString().trim();
            String confirmPassword = confirm_password.getText().toString().trim();
            String skId = SKidInput.getText().toString().trim();
            String phoneNumber = PhoneInput.getText().toString().trim();

            if (!validateForm(email, password, confirmPassword, skId, phoneNumber)) {
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (!isPhoneNumberValid(phoneNumber)) {
                Toast.makeText(SignUp.this, "Phone number must start with 08 or +62", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // User is signed up, now link the SK ID and phone number
                                linkParentToChild(email, skId, phoneNumber);
                                sendEmailVerification();
                            } else {
                                // If sign up fails, display a message to the user.
                                Toast.makeText(SignUp.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } // performSignUp

        private boolean validateForm(String email, String password, String confirmPassword, String skId, String phoneNumber) {
            boolean isValid = true;

            if (TextUtils.isEmpty(email)) {
                AccountInput.setError("Please enter your email.");
                isValid = false;
            }

            if (TextUtils.isEmpty(password)) {
                PasswordInput.setError("Password is empty.");
                isValid = false;
            } else if (!isPasswordValid(password)) {
                PasswordInput.setError("Password is weak.");
                isValid = false;
            } else if (!password.equals(confirmPassword)) {
                confirm_password.setError("Password and confirm password do not match.");
                isValid = false;
            }

            if (!app_term.isChecked() || !school_term.isChecked()) {
                Toast.makeText(SignUp.this, "Please agree to the terms and conditions.", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (TextUtils.isEmpty(skId) || !skId.toUpperCase().startsWith("SK")) {
                SKidInput.setError("Student ID must start with 'SK'.");
                isValid = false;
            }

            if (!isPhoneNumberValid(phoneNumber)) {
                PhoneInput.setError("Phone number must start with 08 or +62.");
                isValid = false;
            }

            return isValid;
        } //validateForm

        private void sendEmailVerification() {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                Task<Void> voidTask = user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this,
                                            "Verification email sent to " + user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                    showVerificationPopup(user.getEmail()); // Show verification popup after email is sent
                                } else {
                                    Toast.makeText(SignUp.this,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } // sendEmailVerification

        private void initializeUI() {
            AccountInput = findViewById(R.id.AccountInput); // Input Email
            PhoneInput = findViewById(R.id.PhoneInput); // Input Phone Number
            SKidInput = findViewById(R.id.SKidInput); // Input student ID
            PasswordInput = findViewById(R.id.PasswordInputt); // Input Password
            confirm_password = findViewById(R.id.confirm_password); // Input Confirm Password

            app_term = findViewById(R.id.app_term); // Checkbox App Term
            school_term = findViewById(R.id.school_term); // Checkbox School Term

            btnNext = findViewById(R.id.signUpButton); // Button Sign Up

            progressBar = findViewById(R.id.progressBar); // Progress Bar for loading
            passwordStrengthBar = findViewById(R.id.passwordStrengthBar); // Initialize the ProgressBar for Password Strength

            // Initialize Firebase
            mAuth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();

            passwordStrengthView = findViewById(R.id.passwordStrengthText);

            // Initialize password strength colors
            passwordStrengthColors = new int[]{
                    ContextCompat.getColor(this, R.color.password_strength_very_weak),
                    ContextCompat.getColor(this, R.color.password_strength_weak),
                    ContextCompat.getColor(this, R.color.password_strength_good),
                    ContextCompat.getColor(this, R.color.password_strength_strong)
            };

            // Initialize password strength levels (after super.onCreate(savedInstanceState))
            passwordStrengthLevels = new String[]{
                    getString(R.string.password_strength_very_weak),
                    getString(R.string.password_strength_weak),
                    getString(R.string.password_strength_good),
                    getString(R.string.password_strength_strong)
            };

        } //initializeUI


        private boolean isPasswordValid(String password) {
            // Minimum password length
            int MIN_LENGTH = 6;
            // Requirement flags
            boolean hasUppercase = !password.equals(password.toLowerCase());
            boolean hasLowercase = !password.equals(password.toUpperCase());
            boolean hasDigit = password.matches(".*\\d.*");
            //boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?].*");
            // Check the password against each requirement
            return password.length() >= MIN_LENGTH && hasUppercase && hasLowercase && hasDigit; //&& hasSpecialChar;
        } //isPasswordValid

        private int getPasswordStrength(String password) {
            int strengthPoints = 0;
            if (password.length() >= 8) strengthPoints += 2; // Points for length
            if (password.matches(".*\\d.*")) strengthPoints += 2; // Points for including digits
            if (password.matches(".*[a-z].*")) strengthPoints += 2; // Points for including lowercase
            if (password.matches(".*[A-Z].*")) strengthPoints += 2; // Points for including uppercase
            if (password.matches(".*[\\p{Punct}].*")) strengthPoints += 2; // Points for including symbols
            // Convert points to a percentage, assuming 10 is the max strength points
            return (strengthPoints * 100) / 10;
        }// getPasswordStrength

        private void updatePasswordStrengthView(int strength) {
            int color = getColorForStrength(strength);
            passwordStrengthBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            passwordStrengthView.setTextColor(color);

            if (strength > 75) {
                passwordStrengthView.setText("Strong");
            } else if (strength > 50) {
                passwordStrengthView.setText("Good");
            } else if (strength > 25) {
                passwordStrengthView.setText("Weak");
            } else {
                passwordStrengthView.setText("Very Weak");
            }
        } //updatePasswordStrengthView

        private int getColorForStrength(int strength) {
            if (strength > 75) {
                return passwordStrengthColors[3];
            } else if (strength > 50) {
                return passwordStrengthColors[2];
            } else if (strength > 25) {
                return passwordStrengthColors[1];
            } else {
                return passwordStrengthColors[0];
            }
        } // getColorForStrength

        private void showVerificationPopup(String userEmail) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_email_verification, null);
            dialogBuilder.setView(dialogView);

            TextView tvEmailVerificationMessage = dialogView.findViewById(R.id.tvEmailVerificationMessage);
            tvEmailVerificationMessage.setText("Please verify your Account in your Inbox Email [" + userEmail + "]");

            // Set up the OK button to dismiss the dialog and navigate to the splash screen
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Navigate to the splash screen
                    Intent intent = new Intent(SignUp.this, SplashScreen.class); // Replace with your actual splash screen class
                    startActivity(intent);
                    finish(); // Finish this current activity
                }
            });

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.show();

            // Center the OK button
            Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of button
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height of button
            );
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL; // This will center the button horizontally
            okButton.setLayoutParams(layoutParams);
        }// showVerificationPopup

    }// end of SignUp