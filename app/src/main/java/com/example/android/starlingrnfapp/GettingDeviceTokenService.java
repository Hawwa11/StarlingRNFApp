package com.example.android.starlingrnfapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import androidx.annotation.NonNull;

public class GettingDeviceTokenService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        String DeviceToken = String.valueOf(FirebaseMessaging.getInstance().getToken());
        Log.d("DEVICE TOKEN:" , DeviceToken);
    }
}