package com.emptydev.verba.delete

import android.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DeleteDialog(val context: Context, val endAction: (delete:Boolean) -> Unit) {
    var dialog: AlertDialog
    init {
        dialog= MaterialAlertDialogBuilder(context)
                .setTitle("Delete a set of words")
                .setMessage("Are you sure you want to delete a set of words?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes") { dialog, which ->
                    // Continue with delete operation
                    endAction.invoke(true)
                } // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_dialog_alert)
                .create()
    }
    fun show(){
        dialog.show()
    }
}
