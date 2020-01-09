package com.example.administrator.zahbzayxy.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.google.zxing.Result;
import com.zhangke.qrcodeview.QRCodeView;

import java.util.List;


public class ScanQRCodeActivity extends AppCompatActivity{

    private QRCodeView qrCodeView;
    private TextView scodeBackTV;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        Utils.setFullScreen(ScanQRCodeActivity.this,getWindow());
        qrCodeView = (QRCodeView) findViewById(R.id.qr_code_view);
        qrCodeView.setOnQRCodeListener(new QRCodeView.OnQRCodeRecognitionListener() {
            @Override
            public void onQRCodeRecognition(Result result) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("result", result.getText());
                startActivity(intent);
            }
        });
        scodeBackTV=(TextView)findViewById(R.id.scodeBack);
        scodeBackTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeView.startPreview();
    }

    @Override
    protected void onPause() {
        if(qrCodeView.getCamera()!=null) {
            qrCodeView.stopPreview();
        }else{
            Toast.makeText(getApplicationContext(), "请检查此应用的相机权限是否打开！", Toast.LENGTH_SHORT).show();
        }
        super.onPause();
    }


}
