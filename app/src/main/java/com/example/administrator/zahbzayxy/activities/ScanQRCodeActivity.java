package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.google.zxing.Result;
import com.zhangke.qrcodeview.QRCodeView;


public class ScanQRCodeActivity extends AppCompatActivity{

    private QRCodeView qrCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        qrCodeView = (QRCodeView) findViewById(R.id.qr_code_view);
        qrCodeView.setOnQRCodeListener(new QRCodeView.OnQRCodeRecognitionListener() {
            @Override
            public void onQRCodeRecognition(Result result) {
                Intent intent=new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("result", result.getText());
                startActivity(intent);
                Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
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
        qrCodeView.stopPreview();
        super.onPause();
    }

}
