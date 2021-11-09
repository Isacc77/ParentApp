package com.example.cmpt276_2021_7_manganese;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cmpt276_2021_7_manganese.model.ChoiceAdapter;
import com.example.cmpt276_2021_7_manganese.model.CoinResult;

import java.util.ArrayList;
import java.util.List;


public class RecordListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<CoinResult> coinResults = new ArrayList<>();
    private ChoiceAdapter adapter;
    private AppDatabase db;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        recyclerView = findViewById(R.id.recycle);

        adapter = new ChoiceAdapter(this,coinResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db = Room.databaseBuilder(this,AppDatabase.class,"database-name").build();
        checkAll();
    }


    private void checkAll(){

        new Thread(() -> {
            List<CoinResult> datas =  db.coinDao().getAll();
            coinResults.clear();
            coinResults.addAll(datas);
            handler.sendEmptyMessage(0);

        }).start();

    }
}
