package com.gioidev.basedemo.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowInsets;

import com.gioidev.basedemo.R;

public class SystemUIActivity extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_u_i);

        activity = SystemUIActivity.this.activity;
        Window window = activity.getWindow();
        clipToScreenShape(window);
    }

    public static boolean clipToScreenShape(final Window window) {
        if (window == null || window.getDecorView() == null) {
            return false;
        }

        // Record original drawable & set window to transparent to avoid window has solid color.
        final Drawable original = window.getDecorView().getBackground();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        window.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                return insets;
            }
        });
        window.getDecorView().requestApplyInsets();

        return true;
    }
    public static void clipToRound(@NonNull View view) {
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(-1, -1, view.getWidth() + 1, view.getHeight() + 1);
            }
        });
    }
}