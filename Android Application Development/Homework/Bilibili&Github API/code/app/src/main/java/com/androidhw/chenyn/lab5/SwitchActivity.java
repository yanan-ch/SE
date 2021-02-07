package com.androidhw.chenyn.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidhw.chenyn.lab5.Bilibili.MainActivity;
import com.androidhw.chenyn.lab5.Github.RepoActivity;

public class SwitchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        Button bili = findViewById(R.id.bilibili);
        Button github = findViewById(R.id.github);

        bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SwitchActivity.this, MainActivity.class));
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SwitchActivity.this, RepoActivity.class));
            }
        });
    }
}
