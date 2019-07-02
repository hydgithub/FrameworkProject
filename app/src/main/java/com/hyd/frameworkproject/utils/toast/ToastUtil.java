package com.hyd.frameworkproject.utils.toast;

import android.content.Context;
import android.widget.Toast;

import com.hyd.frameworkproject.R;


/**
 * 简化toast的调用
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
// TODO: 2018/10/18 未做单例处理,同时这里会有个Toast不在MainLooper下调用崩溃的问题
public class ToastUtil {
    public static final String TOAST_ERROR = "服务器开了会小差，稍后再试吧~";
    public static final String TOAST_NO_NET = "亲，您的网络不太给力哟，稍后再试吧~";

    /**
     * toast 提示消息
     *
     * @param context
     * @param msg
     * @return
     */
    public static void showToast(Context context, String msg) {
        NormalToast normalToast = new NormalToast();
        normalToast.show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * @param msg
     */
    public static void showToastLong(Context context, String msg) {
        NormalToast normalToast = new NormalToast();
        normalToast.show(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * toast 提示消息
     *
     * @param context
     * @return
     */
    public static void showNotNetToast(Context context) {
        NormalToast normalToast = new NormalToast();
        normalToast.show(context, TOAST_NO_NET, Toast.LENGTH_SHORT);
    }

    /**
     * toast 提示消息
     *
     * @param context
     * @return
     */
    public static void showErrorToast(Context context) {
        NormalToast normalToast = new NormalToast();
        normalToast.show(context, TOAST_ERROR, Toast.LENGTH_SHORT);
    }

    public static void showCtrlSuccessful(Context context) {
        NormalToast toast = new NormalToast();
        toast.show(context, "操作成功", Toast.LENGTH_SHORT);
    }

    public static void showCtrlFailed(Context context) {
        NormalToast toast = new NormalToast();
        toast.show(context, "操作失败", Toast.LENGTH_SHORT);
    }

    /**
     * 保存图片成功
     *
     * @param context
     * @param text    toast的文字
     */
    public static void showSaveImageComplete(Context context, String text) {
        NormalToast toast = new NormalToast(NormalToast.TOAST_TYPE_IMAGE_AND_TEXT);
        toast.show(context, text, R.drawable.ic_saved, Toast.LENGTH_LONG);
    }

    /**
     * 包含image的toast
     *
     * @param context
     * @param text     toast的文字
     * @param imageRes 文字上面的图片的id
     */
    public static void showWithImage(Context context, int imageRes, String text) {
        NormalToast toast = new NormalToast(NormalToast.TOAST_TYPE_IMAGE_AND_TEXT);
        toast.show(context, text, imageRes, Toast.LENGTH_LONG);
    }
}
