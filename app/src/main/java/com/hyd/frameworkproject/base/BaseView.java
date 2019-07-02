package com.hyd.frameworkproject.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * BaseActivity与BaseFragment中的网络请求loading与吐司接口
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public interface BaseView {

    /**
     * 展示网络请求Loading
     */
    void showRequestLoading();

    /**
     * 关闭网络请求Loading
     */
    void dismissRequestLoading();

    /**
     * 网络请求异常
     *
     * @param error
     */
    void onRequestError(String error);

    /**
     * 弹出吐司
     *
     * @param msg
     */
    void showToast(@NonNull String msg);

    /**
     * 弹出吐司
     *
     * @param resId
     */
    void showToast(@StringRes final int resId);
}
