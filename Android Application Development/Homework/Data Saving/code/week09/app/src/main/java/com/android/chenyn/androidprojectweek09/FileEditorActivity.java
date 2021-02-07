package com.android.chenyn.androidprojectweek09;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorActivity extends AppCompatActivity {

    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        inputText = findViewById(R.id.inputText);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button loadBtn = findViewById(R.id.loadBtn);
        Button clearBtn = findViewById(R.id.clearBtn);

        final String MYFILENAME = "MyInputFile";

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save file
                try (FileOutputStream fos = openFileOutput(MYFILENAME, Context.MODE_PRIVATE)) {
                    fos.write(inputText.getText().toString().getBytes());
                    fos.close();
                    Toast.makeText(getApplicationContext(), "Save successfully.", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully saved file.");
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to save file.");
                }

            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load file
                try (FileInputStream fis = openFileInput(MYFILENAME)) {
                    byte[] contents = new byte[fis.available()];
                    fis.read(contents);
                    inputText.setText(new String(contents));
                    Toast.makeText(getApplicationContext(), "Load successfully.", Toast.LENGTH_SHORT).show();
                    fis.close();
                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(), "Fail to load file.", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to read file.");
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
            }
        });
    }
}
