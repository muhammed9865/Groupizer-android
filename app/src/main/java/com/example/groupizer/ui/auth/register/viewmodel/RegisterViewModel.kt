package com.example.groupizer.ui.auth.register.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.repository.AuthRepository
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.interest.InterestsResponse
import com.example.groupizer.ui.isValidEmail
import com.example.groupizer.ui.isValidName
import com.example.groupizer.ui.isValidPassword

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _name = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _interests = MutableLiveData<HashSet<Interest>>(hashSetOf())
    private val _token = MutableLiveData<String>()

    fun setEmail(email: String) {

        this._email.value = email
        Log.d(TAG, "register setEmail: ${_email.value}")

    }

    fun getEmail(): String {
        return _email.value!!
    }

    fun setPassword(password: String) {
        this._password.value = password
        Log.d(TAG, "register setPassword: ${_password.value}")
    }

    fun getPassword(): String {
        return _password.value!!
    }

    fun setName(name: String) {
        this._name.value = name
        Log.d(TAG, "register setName: ${_name.value}")
    }

    fun getName(): String {
        return _name.value!!
    }

    fun setToken(token: String) {
        Log.d(TAG, "setToken: $token")
        _token.value = token
    }

    fun getToken(): String? {
        return _token.value
    }


    fun addInterest(id: Int) {
        _interests.value!!.add(Interest(id))
        Log.d(TAG, "addInterest: ${_interests.value}")
    }

    fun removeInterest(id: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _interests.value!!.removeIf {
                it.id == id
            }
        } else {
            for (i in _interests.value!!) {
                if (i.id == id) {
                    _interests.value!!.remove(i)
                    break
                }
            }
        }
    }


    suspend fun register(): AuthResponse? {
        //val registerForm = RegisterForm(_name.value!!, _email.value!!, _password.value!!)
        if (_name.value.isValidName() && _email.value.isValidEmail() && _password.value.isValidPassword()) {
            return repository.registerUser(
                RegisterForm(
                    _name.value!!,
                    _email.value!!,
                    _password.value!!
                )
            )
        }
        return null
    }

    suspend fun getAllInterests(): List<InterestsResponse>{
        return repository.getAllInterests()
    }

    suspend fun sendSelectedInterests() =
        repository.sendSelectedInterests(_token.value!!, _interests.value!!.toList())

    fun dummyInterests(): List<InterestsResponse> {
        return listOf(
            InterestsResponse(1, "CS"), InterestsResponse(2, "CS"), InterestsResponse(3, "CS"),
            InterestsResponse(4, "CS"), InterestsResponse(5, "CS"), InterestsResponse(6, "CS"),
            InterestsResponse(7, "CS"), InterestsResponse(8, "CS"), InterestsResponse(9, "CS")
        )
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}