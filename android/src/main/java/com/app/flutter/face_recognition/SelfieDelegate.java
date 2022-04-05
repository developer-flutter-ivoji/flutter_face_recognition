package com.app.flutter.face_recognition;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import io.flutter.app.FlutterActivity ;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import android.content.SharedPreferences;
/**
 * Created by Jaimin Sarvaiya on 11-11-2019.
 * Copyright (c) 2019  All rights reserved.
 */
public class SelfieDelegate implements PluginRegistry.ActivityResultListener {
    private static final String TAG = "Selfie Delegate";
    Activity activity;
    private MethodChannel.Result pendingResult;
    private MethodCall methodCall;
    public SelfieDelegate(Activity activity) {
        this.activity = activity;
    }
    private void finishWithAlreadyActiveError(MethodChannel.Result result) {
        result.error("already_active", "Image picker is already active", null);
        clearMethodCallAndResult();
    }
    private void clearMethodCallAndResult() {
        methodCall = null;
        pendingResult = null;
    }
    public void detectLivelinesss(MethodCall methodCall, MethodChannel.Result result) {
        if (!setPendingMethodCallAndResult(methodCall, result)) {
            finishWithAlreadyActiveError(result);
            return;
        }
        this.pendingResult = result;
        String msgselfieCapture = methodCall.argument("msgselfieCapture");
        String msgBlinkEye = methodCall.argument("msgBlinkEye");
        Intent intent = new Intent(activity, FaceTrackerActivity.class);
        intent.putExtra("msgselfieCapture", msgselfieCapture);
        intent.putExtra("msgBlinkEye", msgBlinkEye);
        activity.startActivityForResult(intent, 2899);
    }
    private boolean setPendingMethodCallAndResult(
            MethodCall methodCall, MethodChannel.Result result) {
        if (this.pendingResult != null) {
            return false;
        }
        this.methodCall = methodCall;
        pendingResult = result;
        return true;
    }
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2899) {
            if (resultCode == Activity.RESULT_OK) {
                if (null != data && null != data.getExtras()) {
                    String message = data.getStringExtra("bitmapImage");
                    String imageResult = FaceTrackerActivity.getImgBase64();
                    finishWithSuccess(imageResult);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finishWithSuccess(null);
            }
        }
        return false;
    }
    private void finishWithSuccess(String imagePath) {
        if(imagePath == null){
            imagePath = "";
        }
        else{
        }
        Log.e("ImagePath",imagePath);
        pendingResult.success(imagePath);
        clearMethodCallAndResult();
    }
}
