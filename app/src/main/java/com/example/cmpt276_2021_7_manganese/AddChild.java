package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    private boolean isSaved = false;

    private EditText inputName;
    private String name;
    ChildManager Manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        Manager = ChildManager.getInstance();
        Toolbar toolbar = findViewById(R.id.add_child_toolbar);

        setSupportActionBar(toolbar);

        // set up for UP bottom
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


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

                    Manager.add(new Child(name));


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

        return intent;
    }


    // connecting menu_add_lens.xml for tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_child, menu);

        return true;

    }












}