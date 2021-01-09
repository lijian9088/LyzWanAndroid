package com.lyz.lyzwanandroid.widget.customxpopup

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.blankj.utilcode.util.ScreenUtils
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnInputConfirmListener
import com.lyz.lyzwanandroid.R

/**
 * @author liyanze
 * @create 2021/01/08
 * @Describe
 */

class LoginPopup(context: Context) : CenterPopupView(context), View.OnClickListener {

    lateinit var etUsername: AppCompatEditText
    lateinit var etPassword: AppCompatEditText
    private lateinit var tv_confirm: TextView
    private lateinit var tv_cancel: TextView

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_login
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun initPopupContent() {
        super.initPopupContent()
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tv_confirm = findViewById(R.id.tv_confirm)
        tv_cancel = findViewById(R.id.tv_cancel)

        tv_confirm.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }

    interface OnClickListener{
        fun onCancel()
        fun onConfirm()
    }

    var onClickListener : OnClickListener? = null
    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onClick(v: View) {
        if (v === tv_cancel) {
            if (onClickListener != null) {
                onClickListener!!.onCancel()
            }
            dismiss()
        } else if (v === tv_confirm) {
            if (onClickListener != null) {
                onClickListener!!.onConfirm()
            }
            if (popupInfo.autoDismiss) {
                dismiss()
            }
        }
    }

    override fun getPopupWidth(): Int {
        return (ScreenUtils.getScreenWidth() * 0.7f).toInt()
    }
}