package com.gioidev.basedemo.screenshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.gioidev.basedemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ScreenshotActivity extends AppCompatActivity {

    String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);


        close();

    }
    public void close(){
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout constraintLayout = findViewById(R.id.constraint_screen_shot);
                View v = getWindow().getDecorView().getRootView();
                getScreenShot();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] imageBytes = baos.toByteArray();
                String s = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.d(TAG, "getScreenShot: + 1: " + s);

            }
        });
    }
    public void delete(){
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fdelete = new File(Environment
                        .getExternalStorageDirectory().toString(), "Download/"
                        + System.currentTimeMillis() + ".png");
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + fdelete);
                    } else {
                        System.out.println("file not Deleted :" + fdelete);
                    }
                }
            }
        });
    }
    private void getScreenShot() {
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            FileOutputStream fos = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().toString(), "Download/"
                    + System.currentTimeMillis() + ".png"));
            Log.d(TAG, "getScreenShot: " + fos);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static Bitmap getScreenShot(View v) {
//        Bitmap b = Bitmap.createBitmap(v.getWidth() , v.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.draw(c);
//        FileOutputStream out = null;
//        File f = new File("storage/emulated/0/Download/1.PNG");
//        f.mkdirs();
//
//        try {
//            out = new FileOutputStream(f);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (null != out) {
//                b.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//            }
//        } catch (IOException e) {
//            // TODO: handle exception
//        }
//        return b;
//    }
}