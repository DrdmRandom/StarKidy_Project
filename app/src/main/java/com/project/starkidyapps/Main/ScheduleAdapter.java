//package com.project.starkidyapps.Main;
//
//import android.content.pm.ActivityInfo;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.project.starkidyapps.R;
//
//import java.util.List;
//
//public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
//
//    private List<ActivityInfo> scheduleList;
//
//    public ScheduleAdapter(List<ActivityInfo> scheduleList) {
//        this.scheduleList = scheduleList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        ActivityInfo activity = scheduleList.get(position);
//        holder.title.setText(activity.getTitle());
//        holder.time.setText(activity.getTime());
//        holder.time.setText(activity.getUniform());
//    }
//
//    @Override
//    public int getItemCount() {
//        return scheduleList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView title;
//        public TextView time;
//        public TextView uniform;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.activity_Title);
//            time = itemView.findViewById(R.id.activity_Time);
//            uniform = itemView.findViewById(R.id.uniform_Activity);
//        }
//    }
//}
