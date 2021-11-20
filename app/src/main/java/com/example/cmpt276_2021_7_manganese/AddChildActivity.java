package com.example.cmpt276_2021_7_manganese;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;

import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.util.Date;

/**
 * This class is for add child
 * user can use this class to add child, by clicking floating button
 * @author  Shuai Li & Yam
 */
public class AddChildActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Child";
    private static int indexForSwitchActivity = -1;
    private boolean isSaved = false;
    private EditText inputName;
    private String name;
    private String photourl;
    private ChildManager childManager;

    private ImageView photo;
    private Button skip;

    //whether user change their photo done.
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Toast.makeText(AddChildActivity.this,"Change Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(AddChildActivity.this,"Change Fail",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        Toolbar toolbar = findViewById(R.id.add_child_toolbar);
        setSupportActionBar(toolbar);

        childManager = ChildManager.getInstance();

        indexForSwitchActivity = getIntent().getIntExtra(EXTRA_MESSAGE, -1);

        // set up for UP bottom
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.et_name);
        inputName.addTextChangedListener(tw);

        photo = findViewById(R.id.iv_photo);
        photo.setOnClickListener(view -> PictureSelector
                .create(AddChildActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(true));

        //set photo url default if user skip.
        skip = findViewById(R.id.bt_skip_photo);
        skip.setOnClickListener(view ->{
            if (inputName.getText() == null){
                Toast.makeText(AddChildActivity.this,"Name cannot be null",Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(photourl)){
                name = inputName.getText().toString();
                photourl = "default";
                Child ch = new Child(name, photourl);
                if (indexForSwitchActivity < 0) {
                    addChildToManager();
                }
                else {
                    editChildInManager();
                }
                Toast.makeText(AddChildActivity.this,"Saved With Skip Choosing Photo!",Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    //reload the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    photourl = pictureBean.getPath();
                    photo.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                } else {
                    photo.setImageURI(pictureBean.getUri());
                }
            }
        }
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String strName = inputName.getText().toString().trim();
            if (strName.length() <= 0) {
                isSaved = false;
                return;
            }
            isSaved = true;
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_backup:
                if (isSaved) {
                    name = inputName.getText().toString();
                    if (indexForSwitchActivity < 0) {
                        addChildToManager();
                    } else {
                        editChildInManager();
                    }
                    finish();
                } else {
                    Toast.makeText(this, "Cannot save with invalid inputs!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete:
                childManager.removeChild(indexForSwitchActivity);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, AddChildActivity.class);
        intent.putExtra(EXTRA_MESSAGE, -1);
        return intent;
    }

    public static Intent makeLaunchIntentWithPosition(Context c, int position) {
        Intent intent = new Intent(c, AddChildActivity.class);
        intent.putExtra(EXTRA_MESSAGE, position);
        return intent;
    }

    // add a child to the exist manager object, when indexForSwitchActivity < 0
    private void addChildToManager() {
        ChildManager manager = ChildManager.getInstance();
        manager.add(new Child(name));
    }

    // edit a child, when indexForSwitchActivity >= 0
    private void editChildInManager() {
        ChildManager manager = ChildManager.getInstance();
        manager.getByIndex(indexForSwitchActivity).setName(name);
    }

    // a static function to set indexForSwitchActivity in onDestroy()
    private static void setIndex(int position) {
        indexForSwitchActivity = position;
    }

    @Override
    protected void onDestroy() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onDestroy();
        jsonSave();
        setIndex(-1);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (indexForSwitchActivity >= 0) {
            getMenuInflater().inflate(R.menu.backup_and_delete_on_action_bar, menu);
        }else {
            getMenuInflater().inflate(R.menu.backup_on_action_bar, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (indexForSwitchActivity >= 0) {
            this.setTitle("Edit your child");
        } else {
            this.setTitle("Add your child");
        }
    }

    private void jsonSave() {
        String jsonString = childManager.getGsonString();
        SharedPreferences prefs = this.getSharedPreferences("tag", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("save", jsonString);
        editor.apply();
    }


}