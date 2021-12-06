package com.example.cmpt276_2021_7_manganese;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.TaskHistory;

import java.time.LocalDateTime;
import java.util.List;

public class TaskHistoryAdapter extends BaseAdapter {
    private final Context context;
    private List<Child> childList;
    private List<TaskHistory> taskHistories;

    public TaskHistoryAdapter(List<TaskHistory> taskHistories, Context context) {
        this.childList = childList;
        this.context=context;
        this.taskHistories = taskHistories;
    }

    @Override
    public int getCount() {
//        return childList.size();
        return taskHistories.size();
    }

    @Override
    public Object getItem(int position) {
//        return childList.get(position);
        return taskHistories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.list_view, null);
//        Child child = childList.get(position);
        TaskHistory history = taskHistories.get(position);
        TextView textView=view.findViewById(R.id.tv_name);
        ImageView img=view.findViewById(R.id.user_header);
        String name = history.getName();
            LocalDateTime date =  history.getDate();
            String fullInfo = name + "    (" + date.getMonthValue() + "/" +
                    date.getDayOfMonth() + "/" + date.getYear() + ")    ";
//        textView.setText(history.getName());
        textView.setText(fullInfo);

        Glide.with(this.context).load(history.getUrl()).placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head).into(img);
//        view.findViewById(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context,AddChildActivity.class);
////                intent.putExtra("child",child);
//                intent.putExtra("task",history);
//                intent.putExtra("Child",position);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }
}
