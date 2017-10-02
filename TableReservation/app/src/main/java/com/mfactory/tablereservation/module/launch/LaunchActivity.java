package com.mfactory.tablereservation.module.launch;

import android.os.Bundle;
import android.os.Handler;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.module.base.BaseActivity;
import com.mfactory.tablereservation.module.customer.list.CustomerListActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Handler handler = new Handler();
        handler.postDelayed(() -> CustomerListActivity.start(mContext), 3000);
    }
}
