package com.hyd.frameworkproject.utils.permission.base;

/**
 * 自定义提示用户到设置中授权接口
 *
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public interface ISettingDialogListener {

    /**
     * 自定义SettingDialog
     *
     * @param requestCode
     */
    void showSettingDialog(int requestCode);
}
