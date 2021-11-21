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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.example.cmpt276_2021_7_manganese.model.CoinResult;

import java.lang.reflect.Array;
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
    private int currentItem = -1;
    private int result;
    private int currentUserPosition = 0;

    private TextView record_btn;
    ImageView child_photo;
    private String select="";
    private MediaPlayer player;
    private ChildManager manager;
    private String[] childrenData;
    private Spinner show_name;
    private Spinner show_icon;
    private String currentPhoto = "default";
    private AppDatabase db;
    private static final String[] childrenChooseList = {"nobody","child_1","child_2"};
    private static final String[] coinChooseList = {"——","Head","Tail"};
    private static final HashMap<String,String> childChooseMap = new HashMap<>();
    private ArrayAdapter<String> arrayAdapter, coin_adapter;
    private List<Child> childrenList = new ArrayList<>();

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if(childrenList.size() == 0) {
                    childrenData = new String[1];
                    childrenData[0] = "nobody";
                    childChooseMap.put("nobody","default");
                    showMain();
                } else {
                    childrenData = new String[childrenList.size() + 1];
                    for (int i = 0; i < childrenList.size(); i++) {
                        childrenData[i] = childrenList.get(i).getName();
                        childChooseMap.put(childrenList.get(i).getName(),childrenList.get(i).getPhotoUrl());
                    }
                    childrenData[childrenList.size()] = "nobody";
                    childChooseMap.put("nobody","default");
                    showMain();
                }
            }
        }
    };
    //clear the whole children list and put the whole data to the children manager
    private void initChildrenList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                childrenList.clear();
//                childrenList = manager.ChildData();
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initChildrenList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        db = Room.databaseBuilder(this,AppDatabase.class,"database-name").build();
        initChildrenList();
        manager = ChildManager.getInstance();
        start = findViewById(R.id.start);
        mCoinImageView = findViewById(R.id.tiv);
        show_name = findViewById(R.id.show_name);
        show_icon = findViewById(R.id.show_coin);
        start.setOnClickListener(view -> showAnimotion());

        record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(view -> {
            Intent intent = new Intent(FlipCoinActivity.this,RecordListActivity.class);
            startActivity(intent);
        });
        childrenData = manager.StringChildData();

        coin_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coinChooseList);
        coin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        show_icon.setAdapter(coin_adapter);
        show_icon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentItem = i - 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        currentItem = -1;

        //childrenData = manager.StringChildData();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,childrenData);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        show_name.setAdapter(arrayAdapter);
        show_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
//                select =  childrenData[index];
//                String photo = childChooseMap.get(select);
//                if ("default".equals(photo)){
//                    child_photo.setImageResource(R.mipmap.default_head);
//                }else {
//                    child_photo.setImageBitmap(BitmapFactory.decodeFile(childChooseMap.get(select)));
//                }
//                currentPhoto = photo;
//                chooseChildInOrder(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showMain(){
        //childrenData = new String[1];
        childrenData[0] = "nobody";
        //Connect the optional content to the ArrayAdapter,Set the style of the drop-down list,Add the Adapter to the spinner,Add event Spinner event listener
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,childrenChooseList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        show_name.setAdapter(arrayAdapter);
        show_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
//                select =  childrenData[index];
//                String photo = childChooseMap.get(select);
//                if ("default".equals(photo)){
//                    child_photo.setImageResource(R.mipmap.default_head);
//                }else {
//                    child_photo.setImageBitmap(BitmapFactory.decodeFile(childChooseMap.get(select)));
//                }
//                currentPhoto = photo;
//                chooseChildInOrder(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        select =  childrenData[0];
        String photo = childChooseMap.get(childrenData[0]);
        if ("default".equals(photo)){
            child_photo.setImageResource(R.mipmap.default_head);
        }else {
            child_photo.setImageBitmap(BitmapFactory.decodeFile(childChooseMap.get(select)));
        }
    }

    private void chooseChildInOrder(int position){
        if (position == 0)
            return;
        List<String> str  = new ArrayList<>();
        for (int i = 0;i < childrenData.length;i++){
            str.add(childrenData[i]);
        }
        str.remove(childrenData[position]);
        childrenData[0] = childrenData[position];
        for (int i = 0;i < str.size();i++){
            childrenData[i + 1] = str.get(i);
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
                if(result == 0)
                    msg = "The result is Head!";
                else
                    msg = "The result is Tail!";
                showNormalDialog(msg);
                if (!select.equals("nobody")){
                    String coinResultChoose = "--";
                    if (currentItem == 0){
                        coinResultChoose = "Head";
                    }else if (currentItem ==  1){
                        coinResultChoose = "Tail";
                    }
                    CoinResult coinresult = new CoinResult(getUUID(),getTime(),coinResultChoose,result == 0?"Head":"Tail",select,currentPhoto);
                    show_icon.setSelection(0);
                    if (childrenList.size() == 0){
                        currentUserPosition = 0;
                    }else {
                        if (currentUserPosition < childrenData.length-1){
                            currentUserPosition++;
                        }else if (currentUserPosition >= childrenData.length-1){
                            currentUserPosition = 0;
                        }
                    }
                    show_name.setSelection(currentUserPosition);
                    new Thread(() -> db.coinDao().insert(coinresult)).start();
                }
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
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
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
