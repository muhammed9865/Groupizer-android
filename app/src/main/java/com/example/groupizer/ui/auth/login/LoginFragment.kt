package com.example.groupizer.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentLoginBinding
import com.example.groupizer.pojo.model.LoginForm
import com.example.groupizer.pojo.repository.GroupizerRepository
import com.example.groupizer.ui.hideKeyboard
import com.example.groupizer.ui.auth.login.viewmodel.LoginViewModel
import com.example.groupizer.ui.auth.login.viewmodel.LoginViewModelFactory
import com.example.groupizer.ui.dashboard.DashboardActivity
import com.example.groupizer.ui.hideProgress
import com.example.groupizer.ui.showProgress
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(GroupizerRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        onEmailTextChanged()
        onPasswordTextChanged()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginFragment = this
    }

    fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    fun goToDashboard() {
        showProgress(binding.loggingProgressbar, binding.loginLoginBtn)

        hideKeyboard(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.login()?.let { user ->
                    val intent = Intent(requireActivity(), DashboardActivity::class.java)
                    intent.putExtra(getString(R.string.current_user), LoginForm(user.email, user.password))
                    startActivity(intent)
                }
                hideProgress(binding.loggingProgressbar, binding.loginLoginBtn)
            }catch (e: HttpException) {
                Log.d(TAG, "goToDashboard: ${e.response()}")
                 Snackbar.make(binding.root, "Email or password is incorrect", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(requireContext().getColor(R.color.red)).show()
                hideProgress(binding.loggingProgressbar, binding.loginLoginBtn)
            }



        }

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

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }

    companion object {
        private const val TAG = "LoginFragment"
    }


}