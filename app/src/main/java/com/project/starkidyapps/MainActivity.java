package com.project.starkidyapps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//MAIN ACTIVITY INI BUAT DUMMY CLASS

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Memanggil layout splash_screen.xml
        setContentView(R.layout.sign_in_employee);
    }}
