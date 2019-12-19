package com.example.administrator.zahbzayxy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.fragments.ForgetPWFragment;
import com.example.administrator.zahbzayxy.fragments.RegisterFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RegisterActivity extends BaseActivity {

    public static final int REGISTER_FRAGMENT = 1; // 注册的fragement
    public static final int FORGET_PW_FRAGMENT = 2; // 忘记密码的fragemnt

    @IntDef({REGISTER_FRAGMENT, FORGET_PW_FRAGMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentType {
    }

    private static int MCurrentFragment; // 当前显示的fragment
    private RegisterFragment mRegisterFragment;
    private ForgetPWFragment mForgetPWFragment;

    /**
     * 通过此方法启动“注册”或“忘记密码”页面
     *
     * @param context
     * @param type
     */
    public static void startRegisActivity(Context context, @FragmentType int type) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("fragmentType", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getIntentFromLast();
        configFragment();
    }

    /**
     * 配置 fragment
     */
    private void configFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (MCurrentFragment == REGISTER_FRAGMENT) {
            mRegisterFragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag( RegisterFragment.class.getName());
            if (mRegisterFragment == null) {
                mRegisterFragment = new RegisterFragment();
                transaction.add(R.id.container_all_register,mRegisterFragment, mRegisterFragment.getClass().getName())
                        .commit();
            }
        } else {
            mForgetPWFragment = (ForgetPWFragment) getSupportFragmentManager().findFragmentByTag(ForgetPWFragment.class.getName());
            if (mForgetPWFragment == null) {
                mForgetPWFragment = new ForgetPWFragment();
                transaction.add(R.id.container_all_register,mForgetPWFragment, mForgetPWFragment.getClass().getName())
                        .commit();
            }
        }
    }

        /**
         * 获取intent
         */

    private void getIntentFromLast() {
        Intent intent = getIntent();
        if (intent != null) {
            MCurrentFragment = intent.getIntExtra("fragmentType", REGISTER_FRAGMENT);
        }

    }

}
