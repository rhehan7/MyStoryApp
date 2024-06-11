package com.dicoding.picodiploma.loginwithanimation.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.picodiploma.loginwithanimation.R

class PasswordInputEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                char: CharSequence?,
                start: Int,
                before: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, after: Int) {
                // validation input password
                if (!char.isNullOrEmpty() && char.toString().length < 8) {
                    setError(context.getString(R.string.password_warning_txt), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(char: Editable?) {

            }
        })
    }

}