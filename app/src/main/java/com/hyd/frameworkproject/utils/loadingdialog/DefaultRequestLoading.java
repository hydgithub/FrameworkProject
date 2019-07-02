package com.hyd.frameworkproject.utils.loadingdialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hyd.frameworkproject.R;

/**
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */
public class DefaultRequestLoading implements IRequestLoad {

    private Context mContext;
    /**
     * 用于网络请求的Dialog
     */
    private MaterialDialog mRequestLoading;
    private boolean cancelable = true;

    public DefaultRequestLoading(Context context) {
        mContext = context;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public MaterialDialog getRequestLoading() {
        if (mRequestLoading == null) {
            mRequestLoading = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.layout_request_loading, false)
                    .cancelable(cancelable)
                    .build();

            Window window = mRequestLoading.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable());
                WindowManager.LayoutParams params = window.getAttributes();
                params.dimAmount = 0f;
                window.setAttributes(params);
            }
        }
        return mRequestLoading;
    }

    @Override
    public boolean isShowing() {
        return mRequestLoading != null && mRequestLoading.isShowing();
    }

    @Override
    public void show() {
        getRequestLoading().show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            mRequestLoading.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismiss();
        mContext = null;
        mRequestLoading = null;
    }
}
