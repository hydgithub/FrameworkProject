package com.hyd.frameworkproject.utils.display;

import android.content.Context;

/**
 * 处理ios图片和参数，转化为android的
 * Created by zhengjb on 2016/9/6.
 */
public class IOSDisplayTransfer {
    /**
     * 将设计在苹果的高度转换成android的高度
     */
    public static int caculateHeightFromApple(Context context, int appleHeight)
    {
        int rw = DimensUtil.getDisplayWidth(context);
        int height = appleHeight * rw / 750;
        return height;
    }

    /**
     * 将设计在苹果的高度转换成android的高度
     *
     * @param androidWidth android中的屏幕宽度
     * @param appleHeight  苹果中的设计高度
     */
    public static int caculateHeightFromApple(int androidWidth, int appleHeight)
    {
        int height = appleHeight * androidWidth / 750;
        return height;
    }

    /**
     * 换算宽度
     * @param context
     * @param width
     * @return
     */
    public static int caculateWidthFromApple(Context context, int width)
    {
        int rw = DimensUtil.getDisplayWidth(context);
        return rw * width / 750;
    }
}
