package com.hyd.frameworkproject.utils.permission.base;

import android.annotation.SuppressLint;

/**
 * 权限申请回调
 *
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public interface OnPermissionListener {

    /**
     * 权限申请成功
     */
    @SuppressLint("MissingPermission")
    void onPermissionSuccessful();

    /**
     * 权限申请失败
     */
    void onPermissionFailure();
}
