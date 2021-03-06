package com.example.administrator.zahbzayxy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import javax.annotation.Nullable;


/**
 * HYY
 */
public class PermissionActivity extends BaseActivity {

        /**
         * 申请相机权限 code
         */
        private final int REQUEST_CAMERA_PERMISSION = 101;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                openMainActivity();
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_CAMERA_PERMISSION
                    && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMainActivity();
            } else {
                finish();
            }
        }

        private void openMainActivity() {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
}
