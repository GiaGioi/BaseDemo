package com.gioidev.basedemo.in_app_purchase.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;

public class BillingClientClientSetup {

    String TAG = BillingClientClientSetup.this.getClass().getSimpleName();

    private static BillingClient instance;

    public static BillingClient getInstance(Context context, PurchasesUpdatedListener listener) {
        return instance == null ? setUpBillingClient(context, listener) : instance;
    }

    private static BillingClient setUpBillingClient(Context context, PurchasesUpdatedListener listener) {
        BillingClient billingClient = BillingClient.newBuilder(context)
                .setListener(listener)
                .enablePendingPurchases()
                .build();

        return billingClient;
    }



}
