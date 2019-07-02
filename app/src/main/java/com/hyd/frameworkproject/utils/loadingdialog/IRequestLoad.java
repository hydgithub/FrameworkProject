package com.hyd.frameworkproject.utils.loadingdialog;

/**
 * 网络请求Loading接口
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public interface IRequestLoad {

    void show();

    void dismiss();

    boolean isShowing();

    void onDestroy();
}
