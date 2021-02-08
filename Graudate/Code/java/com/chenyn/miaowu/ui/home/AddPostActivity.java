package com.chenyn.miaowu.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenyn.miaowu.MainActivity;
import com.chenyn.miaowu.R;
import com.chenyn.miaowu.Utils.Utils;
import com.chenyn.miaowu.data.post.Post;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
    private ImageView cancelPost, sendPost, cameraBtn, galleryBtn, emojiBtn;
    private EditText inputPost;

    private String postTime, postImageUrl, postText;

    private HomeViewModel mHomeViewModel;

    private Utils utils = Utils.getUtilsInstance();

    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int PICK_PHOTO_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        cancelPost = (ImageView) findViewById(R.id.cancelPostBtn);
        sendPost = (ImageView) findViewById(R.id.sendPostBtn);
        cameraBtn =  (ImageView) findViewById(R.id.cameraBtn);
        galleryBtn = (ImageView) findViewById(R.id.choosePostImage);
        inputPost = (EditText) findViewById(R.id.inputPostText);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        cancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                intent.putExtra("TAG", 1);
                startActivity(intent);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotos();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallay();
            }
        });

        sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // time format
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String postTime = df.format(calendar.getTime());
                String postText = inputPost.getText().toString();
                Post post = new Post(postTime, postText, postImageUrl);
                mHomeViewModel.insertPost(post);
            }
        });
    }

    // take camera
    protected void takePhotos() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
        if (requestCode == PICK_PHOTO_REQUEST || requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if(uri != null){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        postImageUrl = Utils.getUtilsInstance().saveImage(bitmap, this.getApplicationContext());
                        inputPost.setCompoundDrawables(null, null, null, new BitmapDrawable(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
