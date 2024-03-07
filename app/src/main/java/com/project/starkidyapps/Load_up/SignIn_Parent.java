package com.project.starkidyapps.Load_up;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.project.starkidyapps.R;

public class SignIn_Parent extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Memanggil layout XML nya
        setContentView(R.layout.login_parent);
    }
}
