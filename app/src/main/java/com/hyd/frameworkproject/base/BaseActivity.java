package com.hyd.frameworkproject.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.hyd.frameworkproject.utils.immersion.ImmersionBarUtil;
import com.hyd.frameworkproject.utils.immersion.base.OnImmersionListener;
import com.hyd.frameworkproject.utils.loadingdialog.DefaultRequestLoading;
import com.hyd.frameworkproject.utils.loadingdialog.IRequestLoad;
import com.hyd.frameworkproject.utils.permission.PermissionUtil;
import com.hyd.frameworkproject.utils.permission.base.OnPermissionListener;
import com.hyd.frameworkproject.utils.toast.ToastUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

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

public abstract class BaseActivity extends RxAppCompatActivity  implements OnImmersionListener ,BaseView{

    protected Context mContext;
    protected Bundle mSavedInstanceState;
    protected PermissionUtil mPermission;
    private ImmersionBarUtil mImmersionBar = null;
    private IRequestLoad mRequestLoading;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mOtherBroadcastReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        mSavedInstanceState=savedInstanceState;
        setActionBar(true);
        setScreenOrientation(false);
        setSoftInputMode(false);
        isLauncherTaskRoot(false);
        initContentView();
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

    /**
     * 解决安装界面直接点击打开应用再按home键返回桌面，重新进入app重复实例化launcher activity的问题
     * https://www.cnblogs.com/sunsh/articles/4846320.html
     */
    protected void isLauncherTaskRoot(boolean isLauncher){
        if (isLauncher && !this.isTaskRoot()) {
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }

    private void initContentView() {
        //把设置布局文件的操作交给继承的子类
        int layoutRes = setLayoutResId();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
        butterKnifebind();
        onCreate();
    }

    /**
     * 绑定黄油刀,项目上自行封装,便于后期更新版本用
     */
    protected void butterKnifebind() {
    }

    @LayoutRes
    protected abstract int setLayoutResId();

    /**
     * 如果不是直接继承BaseActivity的话,请勿使用该方法
     */
    protected abstract void onCreate();



    @Override
    protected void onPause() {
        super.onPause();
        //关闭软键盘
        hideSoftKeyBoard();
    }

    /**
     * 关闭软键盘,调用三方Utils工具
     */
    protected void hideSoftKeyBoard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
            mContext = this;
            newIntentDoSomething();
        }
    }

    protected void newIntentDoSomething() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * 动态权限申请
     *
     * @param permissionListener
     * @param permissionArray
     */
    public void requestPermission(@NonNull OnPermissionListener permissionListener,
                                  @NonNull String[]... permissionArray) {
        onDestroyPermission();
        mPermission = new PermissionUtil.Builder(mContext)
                .setListener(permissionListener)
                .build();
        mPermission.request(permissionArray);
    }

    /**
     * 动态权限申请
     *
     * @param permissionListener
     * @param permissionsList
     */
    public void requestPermission(@NonNull OnPermissionListener permissionListener,
                                  @NonNull ArrayList<String> permissionsList) {
        onDestroyPermission();
        mPermission = new PermissionUtil.Builder(mContext)
                .setListener(permissionListener)
                .build();
        mPermission.request(permissionsList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPermission != null) {
            mPermission.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onDestroyPermission() {
        if (mPermission != null) {
            mPermission.onDestroy();
            mPermission = null;
        }
    }

    @Override
    public ImmersionBarUtil getImmersion() {
        if (mImmersionBar == null) {
            mImmersionBar = new ImmersionBarUtil(this);
        }
        return mImmersionBar;
    }

    @Override
    @Deprecated
    public void initImmersion() {
        getImmersion();
    }

    @Override
    public void setImmersion() {
    }

    @Override
    public void onDestroyImmersion() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public IRequestLoad getRequestLoading() {
        if (mRequestLoading == null) {
            mRequestLoading = new DefaultRequestLoading(this);
        }
        return mRequestLoading;
    }

    public void setRequestLoading(IRequestLoad requestLoading) {
        mRequestLoading = requestLoading;
    }

    @Override
    public void showRequestLoading() {
        getRequestLoading().show();
    }

    @Override
    public void dismissRequestLoading() {
        if (mRequestLoading != null && mRequestLoading.isShowing()) {
            mRequestLoading.dismiss();
        }
    }

    protected void onDestroyRequestLoading() {
        if (mRequestLoading != null) {
            mRequestLoading.onDestroy();
            mRequestLoading = null;
        }
    }


    /**
     * 吐司
     * @param msg
     */
    @Override
    public void showToast(@NonNull String msg) {
        ToastUtil.showToast(this, msg);
    }

    /**
     * 吐司
     * @param resId
     */
    @Override
    public void showToast(@StringRes final int resId) {
        ToastUtil.showToast(this, getResources().getString(resId));
    }

    /**
     * 弹出吐司
     * @param msg
     */
    protected void showToastLong(@NonNull String msg) {
        ToastUtil.showToastLong(this, msg);
    }

    /**
     * 默认不开启注册广播，设置广播的过滤同时开启广播
     *
     * @param intentFilter 此activity中的广播过滤器
     */
    protected void setIntentFilter(IntentFilter intentFilter) {
        this.mIntentFilter = intentFilter;
        registerBroadCast();
    }

    /**.
     * 为activity准备的广播接收器，主要为了实现activity在需要刷新的时候刷新，也可以有其他用途
     */
    private void registerBroadCast() {
        if (mOtherBroadcastReceiver == null) {
            mOtherBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveBroadCast(context, intent);
                }
            };
            registerReceiver(mOtherBroadcastReceiver, mIntentFilter);
        }
    }

    /**
     * 在activity被销毁时，注销广播
     */
    private void unRegisterBroadCast() {
        if (mOtherBroadcastReceiver != null) {
            unregisterReceiver(mOtherBroadcastReceiver);
            mOtherBroadcastReceiver = null;
        }
    }

    /**
     * 当接受到广播后的处理
     *
     * @param context 接受广播的上下文
     * @param intent  该广播的意图
     */
    protected void onReceiveBroadCast(Context context, Intent intent) {

    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyImmersion();
        onDestroyPermission();
        unRegisterBroadCast();
        onDestroyRequestLoading();
    }



}
