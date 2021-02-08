package com.chenyn.miaowu.ui.archive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenyn.miaowu.MainActivity;
import com.chenyn.miaowu.R;
import com.chenyn.miaowu.Utils.Utils;
import com.chenyn.miaowu.data.pet.Pet;

import java.io.IOException;

public class AddPetActivity extends AppCompatActivity {
    private EditText addPetName, addPetGender, addPetBirth, addPetWeight;
    private ImageView addPetProfile;
    private Button cancelPetBtn, doneBtn;

    private String petProfileUrl;

    static final int PICK_PHOTO_REQUEST = 5;

    private ArchiveViewModel mArchiveViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        addPetProfile = (ImageView) findViewById(R.id.addPetProfile);
        addPetName = (EditText) findViewById(R.id.addPetName);
        addPetGender = (EditText) findViewById(R.id.addPetGender);
        addPetBirth = (EditText) findViewById(R.id.addPetBirth);
        addPetWeight = (EditText) findViewById(R.id.addPetWeight);
        cancelPetBtn = (Button) findViewById(R.id.cancelPetBtn);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        mArchiveViewModel = new ViewModelProvider(this).get(ArchiveViewModel.class);

        addPetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallay();
            }
        });

        cancelPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petName = addPetName.getText().toString();
                String petGender = addPetGender.getText().toString();
                String petBirth = addPetBirth.getText().toString();
                String petWeight = addPetWeight.getText().toString();

                Pet pet = new Pet(petProfileUrl, petName, petGender, petBirth, petWeight);
                mArchiveViewModel.insertPet(pet);
            }
        });
    }

    // choose picture from gllary
    protected void choosePhotoFromGallay() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_REQUEST);
    }

    //gallary callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if(uri != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        petProfileUrl = Utils.getUtilsInstance().saveImage(bitmap, this.getApplicationContext());
                        addPetProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
