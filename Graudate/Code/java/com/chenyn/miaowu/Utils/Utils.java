package com.chenyn.miaowu.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static Utils utils;

    public static Utils getUtilsInstance() {
        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }

    //load image from memory according path
    public Bitmap loadImage(String path) {
        try {
            File file = new File(path);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //save image to memory, return path
    public String saveImage(Bitmap bitmap, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        //img dir name
        File imgDir = cw.getDir("MiaoWuCacheImg", Context.MODE_PRIVATE);
        //name the img
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgName = "Img" + timeStamp + ".jpg";
        File img = new File(imgDir, imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return img.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Notice: null path";
    }
}
