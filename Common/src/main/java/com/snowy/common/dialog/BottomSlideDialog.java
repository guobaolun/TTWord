package com.snowy.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.snowy.common.R;


public class BottomSlideDialog extends Dialog {

    private OnDispatchListener listener;

    public BottomSlideDialog(Context context) {
        super(context);
        initWindow();
    }

    public BottomSlideDialog(Context context, int themeResId) {
        super(context, themeResId);
        initWindow();
    }

    protected BottomSlideDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initWindow();
    }

    private void initWindow() {


        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);  //添加动画
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }


    public void setDispatchListener(OnDispatchListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (listener !=null){
            listener.onDispatch();
        }
        return super.dispatchKeyEvent(event);
    }


    public interface OnDispatchListener {

        void onDispatch();
    }


}
