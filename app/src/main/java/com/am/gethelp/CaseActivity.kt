package com.am.gethelp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_case.addImageBtn
import kotlinx.android.synthetic.main.activity_case.submitCaseBtn
import kotlinx.android.synthetic.main.activity_case.successTextView

class CaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case)
        addImageBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, Companion.PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }

        }

        submitCaseBtn.setOnClickListener {
            createCaseInFireStore()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Companion.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImageFromGallery()
                else
                    Toast.makeText(
                        this,
                        "يرجى السماح للتطبيق بالوصول للمعرض الصور لإختيار الصورة",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode ==IMAGE_PICK_CODE) {
            if (data?.data != null) {
                successTextView.visibility = View.VISIBLE
            }
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