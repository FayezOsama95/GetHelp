package com.am.gethelp

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_case.submitCaseBtn
import kotlinx.android.synthetic.main.activity_case.successTextView

class EmrgencyBtnActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case)

        submitCaseBtn.setOnClickListener {
            createCaseInFireStore()
        }
    }





    private fun createCaseInFireStore() {
        showCustomDialog("التبليغ عن حادثة" , "حدث خطأ أثناء محاولة إرسال الشكوى، يرجى المحاولة لاحقاً" , R.drawable.ic_verified_user , false)
    }


    private fun showCustomDialog(title: String, content: String, icon: Int, isSuccessful: Boolean) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before

        dialog.setContentView(R.layout.dialog_info)
        dialog.setCancelable(true)
        val lp = LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = LayoutParams.WRAP_CONTENT
        lp.height = LayoutParams.WRAP_CONTENT

        (dialog.findViewById<View>(R.id.title) as TextView).text = title
        (dialog.findViewById<View>(R.id.content) as TextView).text = content
        (dialog.findViewById<View>(R.id.icon) as ImageView).setImageResource(icon)

        (dialog.findViewById<View>(R.id.bt_close) as AppCompatButton).setOnClickListener { v ->
            if (isSuccessful)
                this.finish()
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }

    companion object {
        //image pick code
        const val IMAGE_PICK_CODE = 1000
        //Permission code
        const val PERMISSION_CODE = 1001
    }

}