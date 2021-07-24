package com.gioidev.basedemo.in_app_purchase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.ConsumeResponseListener;
import com.gioidev.basedemo.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}