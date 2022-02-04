package com.example.groupizer.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentInterestBinding
import com.example.groupizer.pojo.model.LoginForm
import com.example.groupizer.pojo.repository.GroupizerRepository
import com.example.groupizer.ui.auth.register.adapter.InterestCheck
import com.example.groupizer.ui.auth.register.adapter.InterestsAdapter
import com.example.groupizer.ui.auth.register.viewmodel.RegisterViewModel
import com.example.groupizer.ui.auth.register.viewmodel.RegisterViewModelFactory
import com.example.groupizer.ui.dashboard.DashboardActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class InterestFragment : Fragment() {

    private lateinit var binding: FragmentInterestBinding
    private val viewModel: RegisterViewModel by activityViewModels {
        RegisterViewModelFactory(GroupizerRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestBinding.inflate(inflater, container, false)

        prepareRvAdapter()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.interestFragment = this
    }

    private fun prepareRvAdapter() {
        val adapter = InterestsAdapter(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            try {
                viewModel.getAllInterests()?.let {
                    Log.d(TAG, "prepareRvAdapter: ${it[0].title}")
                    adapter.submitList(it)
                    adapter.setInterestCheck(object : InterestCheck {
                        override fun onInterestChecked(id: Int, isChecked: Boolean) {
                            Log.d(TAG, "interest onInterestChecked: $id")
                            if (isChecked) {
                                viewModel.addInterest(id)
                            } else {
                                viewModel.removeInterest(id)
                            }
                        }
                    })
                }
            }catch (e: HttpException) {
                Log.d(TAG, "prepareRvAdapter: ${e.response()}")
            }
            

            setupRecycleView(adapter)
        }
    }

    private fun setupRecycleView(iAdapter: InterestsAdapter) {
        binding.interestsRv.apply {
            adapter = iAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    fun goToDashboard() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.sendSelectedInterests()?.let { interests ->

                repeat(interests.size) { i ->
                    Log.d(TAG, "goToDashboard: ${interests[i].title}")
                }

                val intent = Intent(requireActivity(), DashboardActivity::class.java)
                intent.putExtra(
                    getString(R.string.current_user),
                    LoginForm(viewModel.getEmail(), viewModel.getPassword())
                )
                startActivity(intent)
            }
        }

    }

    companion object {
        private const val TAG = "InterestFragment"
    }

}