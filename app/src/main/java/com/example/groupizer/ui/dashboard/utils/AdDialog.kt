package com.example.groupizer.ui.dashboard.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.groupizer.databinding.NewAddDialogBinding

class AdDialog(context: Context): Dialog(context) {
    private var binding: NewAddDialogBinding = NewAddDialogBinding.inflate(LayoutInflater.from(context))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        closeDialog()

        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

    fun createGroup(onCreate: (title: String, desc: String) -> Unit) {
        setContentView(binding.root)
        show()
        binding.apply {
            newAdCreate.setOnClickListener {
            if (validateTitle(newAdTitle.text) && validateDesc(newAdDesc.text)) {
                    onCreate(newAdTitle.text.toString(), newAdDesc.text.toString())
                    dismiss()
                    cancel()
                }
            }
        }
    }
    fun closeDialog() {
        binding.newAdClose.setOnClickListener {
            dismiss()
        }
    }

    private fun validateTitle(text: Editable?): Boolean {
        return text!!.isNotEmpty()
    }

    private fun validateDesc(text: Editable?): Boolean {
        return text!!.isNotEmpty()
    }
}