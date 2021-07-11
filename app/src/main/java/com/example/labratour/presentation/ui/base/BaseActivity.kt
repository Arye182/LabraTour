package com.example.labratour.presentation.ui.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.labratour.R
import kotlinx.android.synthetic.main.dialog_progress.*

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = Dialog(this)
    }

    fun showProgressBar(text: String) {
        mProgressDialog.setContentView((R.layout.dialog_progress))
        mProgressDialog.progress_text.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressBar() {
        mProgressDialog.dismiss()
    }
}
