package com.example.diydialog_kotlin

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View

/**
 * Created by RAYn on 2021/6/26.
 */
class MyDialog(
    /**
     * 上下文
     */
    private val mContext: Context,
    /**
     * 布局文件id
     */
    private val layoutResId: Int,
    /**
     * 监听的控件id
     */
    private val listenedItem: IntArray
) : Dialog(mContext, R.style.MyDialog), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //提前设置Dialog样式
        val dialogWindow = window
        //设置dialog居中显示
        dialogWindow!!.setGravity(Gravity.CENTER)
        setContentView(layoutResId)
        val windowManager = (mContext as Activity).windowManager
        val dialogDisplay = windowManager.defaultDisplay
        val layoutParams = dialogWindow.attributes
        //设置dialog宽度为屏幕的4/5
        layoutParams?.height = dialogDisplay.height * 4 / 5
        dialogWindow.attributes = layoutParams
        //点击外部dialog消失
        setCanceledOnTouchOutside(true)
        for (id in listenedItem) {
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        //只要按任何一个控件的id,弹窗都会消失,不管是确定还是取消
        dismiss()
        mListener!!.onCenterItemClick(this, v)
    }

    private var mListener: OnCenterItemClickListener? = null

    interface OnCenterItemClickListener {
        /**
         * 点击事件
         *
         * @param myDialog MyDialog
         * @param view View
         */
        fun onCenterItemClick(myDialog: MyDialog, view: View)
    }

    fun setOnCenterItemClickListener(listener: OnCenterItemClickListener) {
        mListener = listener
    }
}