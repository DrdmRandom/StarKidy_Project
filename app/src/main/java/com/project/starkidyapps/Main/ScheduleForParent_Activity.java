package com.project.starkidyapps.Main;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.starkidyapps.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleForParent_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private ScheduleAdapter adapter;
    private List<ActivityInfo> scheduleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_for_parent);

        //Initializasi
        ImageView backButton = findViewById(R.id.backButton);

        //Back Button Function
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //END OOF Back Button Fucntion

        // WeekDaysGroup Button Fucntion
        RadioGroup weekDaysGroup = findViewById(R.id.weekDaysGroup);

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int defaultCheckedId = R.id.mondayButton; // Default to Monday

        if (dayOfWeek == Calendar.MONDAY) {
            defaultCheckedId = R.id.mondayButton;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            defaultCheckedId = R.id.tuesdayButton;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            defaultCheckedId = R.id.wednesdayButton;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            defaultCheckedId = R.id.thursdayButton;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            defaultCheckedId = R.id.fridayButton;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            defaultCheckedId = R.id.saturdayButton;
        }

        weekDaysGroup.check(defaultCheckedId);
        weekDaysGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String daySelected = "Unknown";

                if (checkedId == R.id.mondayButton) {
                    daySelected = "Monday";
                } else if (checkedId == R.id.tuesdayButton) {
                    daySelected = "Tuesday";
                } else if (checkedId == R.id.wednesdayButton) {
                    daySelected = "Wednesday";
                } else if (checkedId == R.id.thursdayButton) {
                    daySelected = "Thursday";
                } else if (checkedId == R.id.fridayButton) {
                    daySelected = "Friday";
                } else if (checkedId == R.id.saturdayButton) {
                    daySelected = "Saturday";
                }

                Log.i(TAG, "Selected day: " + daySelected);
                showScheduleForDay(daySelected);
            }
        });//END OF WeekdaysGroup

        //Schedule Recycle View
        recyclerView = findViewById(R.id.scheduleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        scheduleList = new ArrayList<>();
        // Contoh menambahkan data
        scheduleList.add(new ActivityInfo()); // Taruh pesan nya di dalan kurung nanti

//        adapter = new ScheduleAdapter(scheduleList);
//        recyclerView.setAdapter(adapter);
        //END OF Schedule Recyccle view
    }//END OF onCreate

    private void showScheduleForDay(String day) {
        Log.i(TAG, "Schedule shown for: " + day);
        // Update the UI based on the day selected
    }
}
