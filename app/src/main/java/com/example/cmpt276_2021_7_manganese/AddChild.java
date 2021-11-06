package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


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

import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;


public class AddChild extends AppCompatActivity {

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
        this.setTitle("Add your child");

        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);


        // set up for UP bottom
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        indexForSwitchActivity = getIntent().getIntExtra(EXTRA_MESSAGE, -1);


        setTextWatcher();


    }

    private void setTextWatcher() {

        inputName = findViewById(R.id.et_name);
        inputName.addTextChangedListener(getInputFromUser());


    }


    private TextWatcher getInputFromUser() {

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String strName = inputName.getText().toString().trim();


                if (strName.length() > 0) {
                    isSaved = true;
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        return tw;
    }


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

                } else {

                    Toast.makeText(this, "Cannot save with invalid inputs!", Toast.LENGTH_SHORT).show();

                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, AddChild.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }


    public static Intent makeLaunchIntent(Context c, String message, int position) {
        Intent intent = new Intent(c, AddChild.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        indexForSwitchActivity = position;
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

        getMenuInflater().inflate(R.menu.add_child, menu);

        return true;

    }


}