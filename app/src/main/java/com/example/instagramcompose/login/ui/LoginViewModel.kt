package com.example.instagramcompose.login.ui

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramcompose.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase

) : ViewModel() {

//    val loginUseCase = LoginUseCase()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {

        _email.value = email
        _password.value = password
        _isLoginEnabled.value = enabledLogin(email, password)

    }


    fun enabledLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

    fun onLoginSelected() {
        viewModelScope.launch {  //lanza corrutina
            _isLoading.value = true
            val result = loginUseCase(email.value!!, password.value!!)

            if (result) {
                //NAVEGAR A LA SIG PANTALLA
                Log.d("Hola", "exitoso")
            }
            _isLoading.value = false

        }

    }

}