package com.example.cmpt276_2021_7_manganese;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.example.cmpt276_2021_7_manganese.model.ChoiceAdapter;
import com.example.cmpt276_2021_7_manganese.model.CoinResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FlipCoinActivity extends AppCompatActivity {

    private Button start;
    private CoinImageView mCoinImageView;
    private RecyclerView recyclerView;
    private int currentItem = -1;
    private int result;
    private List<CoinResult> coinResults = new ArrayList<>();
    private ChoiceAdapter adapter;
    private TextView record_btn;
    ImageView child_photo;
    private String select;
    private AppDatabase db;
    private MediaPlayer player;
    private String[] childrenData;
    private ChildManager manager;
    private Spinner show_name;
    private Spinner show_icon;
    private  String currentPhoto = "default";
    private static final String[] coinChooseList={"——","Head","Tail"};
    private static final HashMap<String,String> childChooseMap = new HashMap<>();
    private ArrayAdapter<String> arrayAdapter;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (manager.getManager().size() == 0) {

                } else {
                    childrenData = new String[manager.getManager().size() + 1];
                    for (int i = 0; i < manager.getManager().size(); i++) {
                        childrenData[i] = manager.getManager().get(i).getName();
                    }
                    childrenData[manager.getManager().size()] = "nobody";
                    childChooseMap.put("nobody", "default");
                    showMain();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        manager = ChildManager.getInstance();
        start = findViewById(R.id.start);
        mCoinImageView = findViewById(R.id.tiv);
        recyclerView = findViewById(R.id.recycle);
        show_name = findViewById(R.id.show_time);
        start.setOnClickListener(view -> showAnimotion());
        ActionBar actionBar = getSupportActionBar();

       if (actionBar != null){
           actionBar.hide();
       }
        adapter = new ChoiceAdapter(this,coinResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db = Room.databaseBuilder(this,AppDatabase.class,"database-name").build();

        record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(view -> {
            Intent intent = new Intent(FlipCoinActivity.this,RecordListActivity.class);
            startActivity(intent);
        });

        childrenData = manager.StringChildData();

        if (childrenData.length == 0){
        }else{
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, childrenData);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            show_name.setAdapter(arrayAdapter);
            show_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    select =  childrenData[i];
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            select =  childrenData[0];
        }
    }

    private void showMain(){
        /*
        show_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                select = childrenData[index];
                String photo = childChooseMap.get(select);
                if ("default".equals(photo)) {
                    child_photo.setImageResource(R.mipmap.msg);
                } else {
                    child_photo.setImageBitmap(BitmapFactory.decodeFile(childChooseMap.get(select)));
                }
                currentPhoto = photo;

                chooseChildInOrder(index);
            }


        }

         */

    }

    private void chooseChildInOrder(int position){
        if (position == 0){
            return;
        }
        List<String> str  = new ArrayList<>();
        for (int i = 0;i < childrenData.length;i++){
            str.add(childrenData[i]);
        }
        str.remove(childrenData[position]);
        childrenData[0] = childrenData[position];
        for (int i = 0;i < str.size();i++){
            childrenData[i+1] = str.get(i);
        }
        arrayAdapter.notifyDataSetChanged();
        show_name.setSelection(0);
    }

    private void showAnimotion(){
        result = new Random().nextInt(2);
        mCoinImageView.clearOtherAnimation();
        if(player == null){
            player = MediaPlayer.create(this,R.raw.coin_flip);
        }
        player.start();
        mCoinImageView.setInterpolator(new DecelerateInterpolator())
                .setDuration(5200)
                .setCircleCount(35)
                .setXAxisDirection(CoinAnimation.DIRECTION_CLOCKWISE)
                .setYAxisDirection(CoinAnimation.DIRECTION_NONE)
                .setResult( result == 0 ? CoinImageView.RESULT_HEAD : CoinImageView.RESULT_TAIL);
        mCoinImageView.setCoinAnimationListener(new CoinAnimation.CoinAnimationListener() {
            @Override
            public void onDrawableChange(int result, CoinAnimation animation) {
            }
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                String msg;
                if(currentItem == 0)
                    msg = "The result is Head!";
                else
                    msg = "the result is Tail!";
                showNormalDialog(msg);
                CoinResult coinresult = new CoinResult(getUUID(),getTime(),currentItem==0?"Head":"Tail",result==0?"Head":"Tail",select,currentPhoto);
                new Thread(() -> db.coinDao().insert(coinresult)).start();
                coinResults.add(coinresult);
                adapter.notifyDataSetChanged();
             }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mCoinImageView.startFlipCoin();
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