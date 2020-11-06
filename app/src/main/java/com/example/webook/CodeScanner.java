package com.example.webook;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * This is an activity that let user to scan a book code to get isbn
 * @param x isbn code results from scanning
 */

public class CodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public String x;
    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scan();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scan(){
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            zXingScannerView =new ZXingScannerView(getApplicationContext());
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }
        else {
            // permission has been already granted, you can use camera straight away
        }
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        x = result.getText();
        zXingScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("code", x);
        setResult(RESULT_OK, intent);
        finish();
    }
}
