package com.yowayowa.santa_emp.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class kokudoFrangment:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("サンタのタイプ")
            .setMessage("あなたは飛ぶサンタですか？")
            .setPositiveButton("はい") { dialog, id ->
                Toast.makeText(context, "Pressed はい", Toast.LENGTH_SHORT).show()
             
            }
            .setNegativeButton("いいえ") { dialog, id ->
                Toast.makeText(context, "あなたは軽車両に分類されます", Toast.LENGTH_SHORT).show()
            }

        return builder.create()
    }
}