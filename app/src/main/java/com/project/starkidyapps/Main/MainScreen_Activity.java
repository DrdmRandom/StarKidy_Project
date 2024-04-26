package com.project.starkidyapps.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.starkidyapps.Load_up.SplashScreen;
import com.project.starkidyapps.R;

public class MainScreen_Activity extends AppCompatActivity {
   //for firebase
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_parent);

        // Initialize components
        LinearLayout containerProfile = findViewById(R.id.container_profile);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView profileName = findViewById(R.id.profile_name);
        TextView profileSubtitle = findViewById(R.id.profile_subtitle);

        TextView titleAgenda = findViewById(R.id.Title_Agenda);
        LinearLayout todayAgendaContainer = findViewById(R.id.Today_Agenda_container);

        ConstraintLayout scheduler = findViewById(R.id.Scheduler);
        CardView cardSchedule = findViewById(R.id.card_schedule);
        ImageView imageScheduleCard = findViewById(R.id.image_schedule_card);
        TextView textSchedule = findViewById(R.id.text_schedule);

        ConstraintLayout reportBookContainer = findViewById(R.id.report_book_container);
        CardView cardReportBook = findViewById(R.id.card_report_book);
        ImageView imageReportCard = findViewById(R.id.image_report_card);
        TextView textReportBook = findViewById(R.id.text_report_book);

        ConstraintLayout attendanceContainer = findViewById(R.id.attendance_container);
        CardView cardAttendance = findViewById(R.id.card_attendance);
        ImageView imageAttendanceCard = findViewById(R.id.image_attendace_card);
        TextView textAttendance = findViewById(R.id.text_attendance);

        TextView titleAnnouncement = findViewById(R.id.Title_Announcement);
        CardView announcementsBoard = findViewById(R.id.announcements_board);
        RelativeLayout emptyAnnouncementLayout = findViewById(R.id.empty_announcement_layout);
        ImageView imageEmptyAnnouncements = findViewById(R.id.image_empty_announcements);
        TextView textEmptyAnnouncements = findViewById(R.id.text_empty_announcements);
        LinearLayout announcementsContainer = findViewById(R.id.announcements_container);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Initialize the CardView and set an OnClickListener
        initCardSchedule();

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(intent);
            finish();
        } else {
            profileName.setText(user.getDisplayName());
            profileSubtitle.setText(user.getEmail());

            // If the user's name is null or empty, you might want to set a placeholder
            if (profileName.getText().toString().isEmpty()) {
                profileName.setText(getString(R.string.default_user_name)); // default_user_name is a string resource
            }

        }

    }

    private void initCardSchedule() {
        CardView cardSchedule = findViewById(R.id.card_schedule);
        if (cardSchedule != null) {
            cardSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainScreen_Activity.this, ScheduleForParent_Activity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("MainScreen_Activity", "cardSchedule is null");
        }
    }
}
