package com.example.diydialogdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Created by RAYn on 2021/6/21.
 */
public class MyDialog extends Dialog implements View.OnClickListener {
    //在构造方法里提前加载了样式
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 布局文件id
     */
    private int layoutResID;
    /**
     * 监听的控件id
     */
    private int[] listenedItem;

    public MyDialog(@NonNull Context context, int themeResId, int[] listenedItem) {
        //加载dialog的样式
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.layoutResID = themeResId;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window window = getWindow();
        //设置dialog显示居中
        window.setGravity(Gravity.CENTER);
        setContentView(layoutResID);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        // 设置dialog宽度为屏幕的4/5
        layoutParams.width = display.getWidth() * 4 / 5;
        getWindow().setAttributes(layoutParams);
        //点击外部Dialog消失
        setCanceledOnTouchOutside(true);
        //遍历控件id添加点击注册
        for (int id : listenedItem) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private OnCenterItemClickListener mListener;

    public interface OnCenterItemClickListener {
        /**
         * 点击事件
         *
         * @param myDialog
         * @param view
         */
        void onCenterItemClick(MyDialog myDialog, View view);
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        //只要按任何一个控件的id,弹窗都会消失,不管是确定还是取消
        dismiss();
        mListener.onCenterItemClick(this, v);
    }
}
