package com.hyd.frameworkproject.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Activity基类
 * 继承于RxAppCompatActivity,并使用RxLifecycle防止RxJava导致的内存泄漏
 * 实现功能:
 * 1.ActionBar开关,默认关闭
 * 2.横竖屏开关,默认竖屏(禁止分屏)
 * 3.输入法弹出是否改变布局,默认改变
 * 4.动态权限
 * 5.沉浸式
 * 6.智能刷新数据
 * 7.网络请求Loading
 * 8.吐司
 * 9.BaseQuickAdapter分页判断
 * Created by huangyd on 2019/7/1.
 */

public class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(true);
        setScreenOrientation(false);
        setSoftInputMode(false);

    }


    /**
     * 选择是否展示ActionBar,默认是不展示,现在基本是用toolbar代替
     */
    private void setActionBar(boolean isNoActionBar) {
        if (isNoActionBar) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 选择屏幕方向 默认竖屏
     */
    private void setScreenOrientation(boolean isLandscape) {
        if (!isLandscape) {
            //强制竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //强制横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * 输入法弹出后改变布局
     * 什么都不设置的话,默认SOFT_INPUT_ADJUST_PAN
     * SOFT_INPUT_ADJUST_PAN: 布局被顶起
     * SOFT_INPUT_ADJUST_RESIZE: 布局不被顶起
     * 当界面中有WebView的时候,建议用SOFT_INPUT_ADJUST_RESIZE,其他时候都用SOFT_INPUT_ADJUST_PAN
     * 当布局文件中有控件设置成始终在底部或权重的时候,SOFT_INPUT_ADJUST_RESIZE方法会使得相关布局始终保持在输入法之上
     */
    private void setSoftInputMode(boolean isRegisterAdjustPAN) {
        if (isRegisterAdjustPAN) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

}
