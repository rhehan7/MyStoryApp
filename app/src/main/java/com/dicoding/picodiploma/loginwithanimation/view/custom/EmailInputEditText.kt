package com.dicoding.picodiploma.loginwithanimation.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.picodiploma.loginwithanimation.R

class EmailInputEditText : AppCompatEditText {

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
                count: Int
            ) {

            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                // validation input email
                if (!char.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(char).matches()) {
                    setError(context.getString(R.string.email_address_warning_txt), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(char: Editable?) {

            }
        })
    }
}