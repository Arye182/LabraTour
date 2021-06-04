package com.example.labratour.utils


import android.app.Activity
import android.app.Dialog
import android.view.View
import com.example.labratour.R
import kotlinx.android.synthetic.main.dialog_progress.*

class ProgressBar {

    private lateinit var mProgressDialog : Dialog



    fun showProgressBar(text: String, context: Activity, view: View){
        mProgressDialog = Dialog(context)
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