package com.example.administrator.zahbzayxy.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.PaySuccessActivity;
import com.example.administrator.zahbzayxy.utils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String orderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("weChatPayPay",baseResp.errCode+"");
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if (baseResp.errCode==0){
                Toast.makeText(this,"恭喜您支付成功",Toast.LENGTH_SHORT).show();
                SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
                orderPrice = tokenDb.getString("orderPrice","");
                Intent intent=new Intent(WXPayEntryActivity.this,PaySuccessActivity.class);
                if (!TextUtils.isEmpty(orderPrice)){
                    intent.putExtra("orderPrice",orderPrice);
                    startActivity(intent);
                }
            }else {
                Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}
