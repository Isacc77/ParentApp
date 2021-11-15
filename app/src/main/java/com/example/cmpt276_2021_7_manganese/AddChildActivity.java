package com.example.cmpt276_2021_7_manganese;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        Toolbar toolbar = findViewById(R.id.add_child_toolbar);
        setSupportActionBar(toolbar);



        indexForSwitchActivity = getIntent().getIntExtra(EXTRA_MESSAGE, -1);

        // set up for UP bottom
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //read intent extra,if no extra , show new game screen,else show edit screen
        indexForSwitchActivity = getIntent().getIntExtra(EXTRA_MESSAGE, -1);

        inputName = findViewById(R.id.et_name);
        inputName.addTextChangedListener(tw);
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
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, AddChildActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    public static Intent makeLaunchIntent(Context c, String message, int position) {
        Intent intent = new Intent(c, AddChildActivity.class);
        intent.putExtra(EXTRA_MESSAGE, position);
//        indexForSwitchActivity = position;
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
        int childIndex = getIntent().getIntExtra(EXTRA_MESSAGE, -1);
//        indexForSwitchActivity = getIntent().getIntExtra(EXTRA_MESSAGE, -1);
        if (childIndex >= 0) {
            this.setTitle("Edit your child");
        } else {
            this.setTitle("Add your child");
        }
    }
}