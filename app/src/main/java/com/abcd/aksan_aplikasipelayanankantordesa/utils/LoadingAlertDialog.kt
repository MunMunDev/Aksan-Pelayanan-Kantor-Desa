package com.abcd.aksan_aplikasipelayanankantordesa.utils

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.R

class LoadingAlertDialog() {
    private lateinit var dialog: AlertDialog

    fun alertDialogLoading(context: Context){
        val view = View.inflate(context, R.layout.alert_dialog_loading, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(view)

        dialog = alertDialogBuilder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    fun alertDialogCancel(){
        dialog.dismiss()
    }
}