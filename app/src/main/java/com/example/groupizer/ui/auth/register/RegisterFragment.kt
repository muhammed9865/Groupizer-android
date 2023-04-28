package com.example.groupizer.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentRegisterBinding
import com.example.groupizer.pojo.di.DataModule
import com.example.groupizer.ui.*
import com.example.groupizer.ui.auth.register.viewmodel.RegisterViewModel
import com.example.groupizer.ui.auth.register.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by activityViewModels {
        RegisterViewModelFactory(DataModule.provideAuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        startTextListeners()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerFragment = this
    }

    fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    fun goToInterests() {
        hideKeyboard(binding.root)
        showProgress(binding.registeringProgressbar, binding.registerNextBtn)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.register()?.let { user ->
                    viewModel.setToken(user.token)
                    saveId(user.id!!)
                    Log.d(TAG, "goToInterests: ${user.email} ${user.name} ${user.password}")
                    findNavController().navigate(R.id.action_registerFragment_to_interestFragment)
                } ?: showError(binding.root, "Email or password are incorrect")
                hideProgress(binding.registeringProgressbar, binding.registerNextBtn)
            }catch (e: HttpException) {
                showError(binding.root, "Email is already in use")
                hideProgress(binding.registeringProgressbar, binding.registerNextBtn)
                e.printStackTrace()
            }

    }


}

private fun startTextListeners() {
    onDisplayNameChanged()
    onEmailTextChanged()
    onPasswordTextChanged()
}

private fun onDisplayNameChanged() {
    binding.registerNameField.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setName(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}

private fun onEmailTextChanged() {
    binding.loginEmailField.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setEmail(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}

private fun onPasswordTextChanged() {
    binding.loginPasswordField.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setPassword(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}

companion object {
    private const val TAG = "RegisterFragment"
}


}