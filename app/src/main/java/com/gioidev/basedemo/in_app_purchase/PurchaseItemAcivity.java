package com.gioidev.basedemo.in_app_purchase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.gioidev.basedemo.R;
import com.gioidev.basedemo.in_app_purchase.utils.BillingClientClientSetup;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItemAcivity extends AppCompatActivity implements PurchasesUpdatedListener {

    public BillingClient billingClient;
    ConsumeResponseListener listener;
    List<SkuDetails> skuDetails;

    Button loadProduct;
    RecyclerView recyclerView;
    TextView tvPremium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_item_acivity);

        skuDetails = new ArrayList<>();
        findViewById(R.id.btn_load_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBillingClient();
            }
        });
    }
    private void setBillingClient() {

        listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull @org.jetbrains.annotations.NotNull BillingResult billingResult, @NonNull @org.jetbrains.annotations.NotNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(PurchaseItemAcivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            }
        };
        billingClient = BillingClientClientSetup.getInstance(this, this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(PurchaseItemAcivity.this, "Billing service disconnected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){

                    //QueryResult
                    List<String> skuList = new ArrayList<> ();
                    skuList.add("com.twouyu.coin001");
                    skuList.add("com.twouyu.coin005");
                    skuList.add("com.twouyu.coin010");
                    skuList.add("com.twouyu.coin025");
                    skuList.add("com.twouyu.coin050");
                    skuList.add("com.twouyu.coin100");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(@NotNull BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                    // Process the result.
                                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){

                                        if (skuDetailsList != null && skuDetailsList.size() > 0) {
                                            skuDetails = skuDetailsList;
                                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetails.get(skuDetails.size() - 1))
                                                    .build();

                                            int responseCode = billingClient.launchBillingFlow(PurchaseItemAcivity.this, billingFlowParams).getResponseCode();
                                            Log.d("TAG", "onSkuDetailsResponse: " + responseCode);
                                        } else
                                            Toast.makeText(PurchaseItemAcivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                } else Toast.makeText(PurchaseItemAcivity.this, "Error code: " + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlePurchase(Purchase purchase) {

        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, @NotNull String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    Toast.makeText(PurchaseItemAcivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult,  @org.jetbrains.annotations.Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }
}