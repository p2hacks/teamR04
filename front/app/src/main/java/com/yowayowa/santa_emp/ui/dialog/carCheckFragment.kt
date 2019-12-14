package com.yowayowa.santa_emp.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class carCheckFragment:DialogFragment (){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("国土交通省の許可")
            .setMessage("国土交通省の許可は取りましたか？")
            .setPositiveButton("はい") { dialog, id ->
                Toast.makeText(context, "Accept.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("いいえ") { dialog, id ->
                Toast.makeText(context, "許可を取ってください。", Toast.LENGTH_SHORT).show()
            }

        return builder.create()
    }
}