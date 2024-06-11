package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository

class SignupViewModel(private val repostitory: UserRepository) : ViewModel() {
    fun registerUser(name: String, email: String, password: String) =
        repostitory.registerUser(name, email, password)
}