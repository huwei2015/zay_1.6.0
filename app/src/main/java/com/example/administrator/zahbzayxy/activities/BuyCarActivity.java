package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.fragments.BuyCarBuyFragment;
import com.example.administrator.zahbzayxy.fragments.BuyCarDeleteFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import butterknife.BindView;

/**
 * 购物车
 */
public class BuyCarActivity extends BaseActivity {
 private TextView bianJi;
  @BindView(R.id.buyCar_container)
  FrameLayout  buyCar_container;
    //设置标记  用哪个fragment
    int what=0;
    private ImageView finish_iv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_car);
        intitView();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        BuyCarBuyFragment buyFragment=new BuyCarBuyFragment();
        fragmentTransaction.add(R.id.buyCar_container,buyFragment).commit();
    }

    private void intitView() {
        bianJi= (TextView) findViewById(R.id.bianji);
        finish_iv= (ImageView) findViewById(R.id.buyCar_toolBar);
        finish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editOnClick(View view) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (what==0){

            BuyCarDeleteFragment deleteFragment=new BuyCarDeleteFragment();
            fragmentTransaction.replace(R.id.buyCar_container,deleteFragment);
            what=1;
            bianJi.setText("完成");


        }else {
            BuyCarBuyFragment buyFragment=new BuyCarBuyFragment();
            fragmentTransaction.replace(R.id.buyCar_container,buyFragment);
            what=0;
            bianJi.setText("编辑");

        }
              fragmentTransaction.commit();


    }
    //点击左上角home键返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //<span style="white-space:pre">    </span>//点击back键finish当前activity
        switch (item.getItemId()) {

            case android.R.id.home:
              //  <span style="white-space:pre">    </span>//点击back键finish当前activity
                finish();
                break;

            default:
                break;
        }
        return true;
    }



    //返回键 销毁当前页面
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
