package com.lyz.lyzwanandroid.widget.customxpopup

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnInputConfirmListener
import com.lyz.lyzwanandroid.R

/**
 * @author liyanze
 * @create 2021/01/08
 * @Describe
 */

class LoginPopup(context: Context) : CenterPopupView(context) {

    private lateinit var etUsername: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
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
    }

    var cancelListener: OnCancelListener? = null
    var inputConfirmListener: OnConfirmListener? = null
    fun setListener(inputConfirmListener: OnInputConfirmListener?, cancelListener: OnCancelListener?) {
        this.cancelListener = cancelListener
        this.inputConfirmListener = inputConfirmListener
    }

    fun onClick(v: View) {
        if (v === tv_cancel) {
            if (cancelListener != null) {
                cancelListener!!.onCancel()
            }
            dismiss()
        } else if (v === tv_confirm) {
            if (inputConfirmListener != null) {
                inputConfirmListener!!.onConfirm(et_input.getText().toString().trim({ it <= ' ' }))
            }
            if (popupInfo.autoDismiss) {
                dismiss()
            }
        }
    }

}