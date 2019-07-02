package com.hyd.frameworkproject.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hyd.frameworkproject.R;
import com.hyd.frameworkproject.base.BaseActivity;

import butterknife.ButterKnife;


/**
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public abstract class FwBaseActivity extends BaseActivity {

    @Override
    protected void butterKnifebind() {
        super.butterKnifebind();
        ButterKnife.bind(this);
    }

    @Override
    public void setImmersion() {
        super.setImmersion();
    }

    /**
     * 设置框架自带的ToolBar{@link com.hyd.frameworkproject.R.layout#layout_base_toolbar}
     *
     * @param toolBar
     * @param title
     */
    protected void setU1cityBaseToolBar(@NonNull Toolbar toolBar, String title) {
        TextView titleTv = (TextView) toolBar.findViewById(R.id.toolbar_title);
        if (titleTv != null){
            titleTv.setText(title);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
       // RequestApi.getInstance().cancleAll(this);
    }


}
