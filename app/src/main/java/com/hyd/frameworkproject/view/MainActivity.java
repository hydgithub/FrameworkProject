package com.hyd.frameworkproject.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hyd.frameworkproject.R;
import com.hyd.frameworkproject.base.FwBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FwBaseActivity {
     @Bind(R.id.toolbar)
     Toolbar toolbar;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(toolbar,true);
    }

    @Override
    protected void onCreate() {
        setImmersion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
