package com.project.starkidyapps.Load_up;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.project.starkidyapps.R;

public class SplashScreen extends AppCompatActivity {

    // Variable
    Animation fadeIn, fadeOut;
    ImageView bg_splass, logo, star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Loading Animation
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        // Find Views
        bg_splass = findViewById(R.id.Backgournd_Teacher);
        logo = findViewById(R.id.Logo);
        star = findViewById(R.id.Backgorund_Star);
        Button buttonParentSignIn = findViewById(R.id.btnParentSignIn);
        Button buttonTeacherSignIn = findViewById(R.id.btnTeacherSignIn);
        Button buttonSignUp = findViewById(R.id.btnSignUp);
        TextView startTitle = findViewById(R.id.start_title);
        TextView content = findViewById(R.id.conten);

        // Apply Animation
        bg_splass.setAnimation(fadeIn);
        logo.setAnimation(fadeIn);
        star.setAnimation(fadeIn);

        // Set views menjadi invisible di awal
        buttonParentSignIn.setVisibility(View.INVISIBLE);
        buttonTeacherSignIn.setVisibility(View.INVISIBLE);
        buttonSignUp.setVisibility(View.INVISIBLE);
        startTitle.setVisibility(View.INVISIBLE);
        content.setVisibility(View.INVISIBLE);

        // Add onClick listeners to buttons
        buttonParentSignIn.setOnClickListener(new View.OnClickListener() {
            //buton sign in parent
            @Override
            public void onClick(View v) {
                // Intent untuk pindah ke SignIn_Parent class
                Intent intent = new Intent(SplashScreen.this, SignIn_Parent.class);
                startActivity(intent);
            }
        });

        buttonTeacherSignIn.setOnClickListener(new View.OnClickListener() {
            //button sing in employee
            @Override
            public void onClick(View v) {
                // Intent untuk pindah ke SignIn_employee class
                Intent intent = new Intent(SplashScreen.this, SignIn_Employee.class);
                startActivity(intent);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            //button sigup
            @Override
            public void onClick(View v) {
                // Intent untuk pindah ke SignUp class
                Intent intent = new Intent(SplashScreen.this, SignUp.class);
                startActivity(intent);
            }
        });

        // Menunggu sampai animasi fade out selesai untuk menampilkan views lain dengan fade in
        bg_splass.postDelayed(new Runnable() {
            @Override
            public void run(){
                bg_splass.startAnimation(fadeOut);
                bg_splass.setVisibility(View.GONE); // Menyembunyikan background setelah fade out

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Setiap view menjadi visible dan mulai animasi fade in
                        buttonParentSignIn.setVisibility(View.VISIBLE);
                        buttonTeacherSignIn.setVisibility(View.VISIBLE);
                        buttonSignUp.setVisibility(View.VISIBLE);
                        startTitle.setVisibility(View.VISIBLE);
                        content.setVisibility(View.VISIBLE);

                        buttonParentSignIn.startAnimation(fadeIn);
                        buttonTeacherSignIn.startAnimation(fadeIn);
                        buttonSignUp.startAnimation(fadeIn);
                        startTitle.startAnimation(fadeIn);
                        content.startAnimation(fadeIn);
                    }
                }, fadeOut.getDuration()); // Tambahkan delay sesuai dengan durasi fadeOut
            }
        }, fadeOut.getDuration()); // Delay ini menunggu sampai fade out selesai tanpa penundaan awal
    }

}
