package com.hyd.frameworkproject.utils.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyd.frameworkproject.R;
import com.hyd.frameworkproject.utils.display.DimensUtil;


/**
 * @author huangyd
 * @date 2019/7/2
 * 维护者
 */

public class NormalToast {
    private int toastType = TOAST_TYPE_TEXT;

    public static final int TOAST_TYPE_TEXT = 0;
    public static final int TOAST_TYPE_IMAGE_AND_TEXT = 1;

    public NormalToast(int toastType) {
        this.toastType = toastType;
    }

    public NormalToast() {
    }

    public void show(final Context context, final String text, int duration) {
        show(context, text, -1, duration);
    }

        public void show(final Context context, final String text, final int imageRes, int duration){
            //加载Toast布局
            View toastRoot = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null);
            //初始化布局控件
            TextView  mTextView = (TextView) toastRoot.findViewById(R.id.custom_toast_tv);
            //为控件设置属性
            mTextView.setText(text);

            LinearLayout customLl = (LinearLayout) toastRoot.findViewById(R.id.custom_toast_ll);
            if (toastType == TOAST_TYPE_IMAGE_AND_TEXT) {
                if (imageRes != -1) {
                    ImageView imageView = (ImageView) toastRoot.findViewById(R.id.custom_toast_iv);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageResource(imageRes);

                    customLl.setPadding(0, DimensUtil.dpToPixels(context, 25), 0, DimensUtil.dpToPixels(context, 45));
                    customLl.getLayoutParams().width = DimensUtil.dpToPixels(context, 128);
                }
            }

            //Toast的初始化
            Toast toastStart = new Toast(context);
            //获取屏幕高度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            toastStart.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            toastStart.setDuration(duration);
            toastStart.setView(toastRoot);
            toastStart.show();
        }
}
