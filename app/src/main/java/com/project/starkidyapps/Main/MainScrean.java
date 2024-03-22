package com.project.starkidyapps.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.starkidyapps.Load_up.SplashScreen;
import com.project.starkidyapps.R;

public class MainScrean extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView;
    ImageView imageView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen); // Set your layout here

        imageView = findViewById(R.id.imageLogo);
        textView = findViewById(R.id.textView2);
        button = findViewById(R.id.buttonLogOut);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail()); // Now it's safe to use textView
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
