package com.android.chenyn.androidprojectweek09;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText newPwText;
    private EditText confirmPwText;
    private Button okBtn;
    private Button clearBtn;
    private Boolean isFirstStart;
    private final String PW_PREFS_NAME = "MyPwPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPwText = findViewById(R.id.newPw);
        confirmPwText = findViewById(R.id.confirmPw);
        okBtn = findViewById(R.id.okBtn);
        clearBtn = findViewById(R.id.clearBtn);

        ifFirstStart();

        if (isFirstStart == true) {
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newPwText.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else if (!confirmPwText.getText().toString().equals(newPwText.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //save password
                        SharedPreferences pwSharedPref = getSharedPreferences(PW_PREFS_NAME, 0);
                        SharedPreferences.Editor pwEditor = pwSharedPref.edit();
                        pwEditor.putString("password", confirmPwText.getText().toString());
                        pwEditor.commit();
                        Intent intent = new Intent(getApplicationContext(), FileEditorActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            newPwText.setVisibility(View.GONE);
            confirmPwText.setHint("Password");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get sharedPreferences
                    SharedPreferences pwSharedPref = getSharedPreferences(PW_PREFS_NAME,0);
                    String password = pwSharedPref.getString("password", "defValue");
                    if (!confirmPwText.getText().toString().equals(password)) {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), FileEditorActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPwText.setText("");
                confirmPwText.setText("");
            }
        });
    }

    //if fisrt start
    protected void ifFirstStart() {
        SharedPreferences pwSharedPref = getSharedPreferences(PW_PREFS_NAME,0);
        String password = pwSharedPref.getString("password", "defValue");
        if (password.equals("defValue")) {
            isFirstStart = true;
        } else {
            isFirstStart = false;
        }
    }
}
