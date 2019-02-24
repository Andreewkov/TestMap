package kov.ru.testmap.ui.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kov.ru.testmap.R
import kov.ru.testmap.ui.view.activity.DialogCallback

open class DialogFragment: DialogFragment() {

    lateinit var dialogCallback: DialogCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dialogCallback = context as DialogCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var view = activity?.layoutInflater?.inflate(R.layout.dialog_view, null)
        val editText = view?.findViewById<EditText>(R.id.editText)
        val button = view?.findViewById<Button>(R.id.button)
        button?.isEnabled = false
        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button?.isEnabled = count != 0
            }
        })
        button?.setOnClickListener {
            dialogCallback.load(editText?.text.toString())
            dialog.dismiss()
        }

        var alert = AlertDialog.Builder(activity)
        alert.setView(view)

        return alert.create()

    }
}