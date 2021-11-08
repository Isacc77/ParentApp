package com.example.cmpt276_2021_7_manganese;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cmpt276_2021_7_manganese.model.ChoiceAdapter;
import com.example.cmpt276_2021_7_manganese.model.CoinResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FlipCoinActivity extends AppCompatActivity {

    private RadioButton head,tail;
    private Button start;

    private TossImageView mTossImageView;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private int currentItem = 0;
    private int result;
    private List<CoinResult> coinResults = new ArrayList<>();
    private ChoiceAdapter adapter;
    private TextView record_btn;
    private String select;

    private AppDatabase db;


    private static final String[] m={"Tom","Jerry","Any"};
    private Spinner show_name;
    private ArrayAdapter<String> arrayAdapter;

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
        setContentView(R.layout.activity_flip_coin);
        head = findViewById(R.id.head);
        tail = findViewById(R.id.tail);
        start = findViewById(R.id.start);
        relativeLayout = findViewById(R.id.relativeLayout);
        mTossImageView = findViewById(R.id.tiv);
        recyclerView = findViewById(R.id.recycle);
        show_name = findViewById(R.id.show_time);
        start.setOnClickListener(view -> showAnimotion());
        head.setOnCheckedChangeListener((compoundButton, b) -> {
          if (b){
              currentItem = 0;
          }
        });

       ActionBar actionBar = getSupportActionBar();
       if (actionBar!=null){
           actionBar.hide();
       }

        tail.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                currentItem = 1;
            }
        });
        adapter = new ChoiceAdapter(this,coinResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db = Room.databaseBuilder(this,AppDatabase.class,"database-name").build();

        checkAll();

        record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlipCoinActivity.this,RecordListActivity.class);
                startActivity(intent);
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        show_name.setAdapter(arrayAdapter);

        show_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select =  m[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        select =  m[0];




    }

    private void checkAll(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CoinResult> datas =  db.coinDao().getAll();
                coinResults.clear();
                coinResults.addAll(datas);
                handler.sendEmptyMessage(0);

            }
        }).start();

    }



    private void showAnimotion(){

        result = new Random().nextInt(2);
        mTossImageView.cleareOtherAnimation();

        TranslateAnimation translateAnimation0 = new TranslateAnimation(0, 0, 0, 0);
        translateAnimation0.setDuration(3000);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, 0, 0);
        translateAnimation1.setDuration(3000);
        translateAnimation1.setStartOffset(3000);

        mTossImageView.setInterpolator(new DecelerateInterpolator())
                .setDuration(6000)
                .setCircleCount(40)
                .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                .setZAxisDirection(TossAnimation.DIRECTION_NONE)
                .setResult( result == 0 ? TossImageView.RESULT_FRONT : TossImageView.RESULT_REVERSE);

        mTossImageView.addOtherAnimation(translateAnimation0);
        mTossImageView.addOtherAnimation(translateAnimation1);

        mTossImageView.setTossAnimationListener(new TossAnimation.TossAnimationListener() {
            @Override
            public void onDrawableChange(int result, TossAnimation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {//
                String msg = result == currentItem?"Congratulations, you won!!！":"I'm sorry you lost";
                showNormalDialog(msg);
                CoinResult coinresult = new CoinResult(getUUID(),getTime(),currentItem==0?"Head":"Tail",result==0?"Head":"Tail",select);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.coinDao().insert(coinresult);
                    }
                }).start();
                coinResults.add(coinresult);
                adapter.notifyDataSetChanged();
             }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mTossImageView.startToss();
    }



    public  String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public String getTime(){
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
          Date date=new Date();
          return sdf1.format(date);
    }



    private void showNormalDialog(String msg){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(FlipCoinActivity.this);
        normalDialog.setIcon(R.mipmap.msg);
        normalDialog.setTitle("");
        normalDialog.setMessage(msg);
        normalDialog.setPositiveButton("confirm",
                (dialog, which) -> dialog.dismiss());
        normalDialog.setNegativeButton("cancel",
                (dialog, which) -> dialog.dismiss());
        normalDialog.show();
    }
}