package com.hyd.frameworkproject.utils.immersion.base;

import com.hyd.frameworkproject.utils.immersion.ImmersionBarUtil;

/**
 * 沉浸式效果UI接口
 *
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public interface OnImmersionListener {

    /**
     * 初始化沉浸式效果
     */
    void initImmersion();

    /**
     * 获取沉浸式效果实例
     *
     * @return
     */
    ImmersionBarUtil getImmersion();

    /**
     * 设置沉浸式效果
     */
    void setImmersion();

    /**
     * 销毁沉浸式效果
     */
    void onDestroyImmersion();
}
