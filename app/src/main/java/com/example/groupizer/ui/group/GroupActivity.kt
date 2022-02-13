package com.example.groupizer.ui.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.groupizer.R
import com.example.groupizer.databinding.ActivityGroupBinding
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.dashboard.viewmodel.GroupsViewModelFactory
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import com.example.groupizer.ui.group.viewmodel.MembersGroupFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GroupActivity : FragmentActivity() {
    private lateinit var binding: ActivityGroupBinding
    private lateinit var navController: NavController
    private val viewModel: GroupViewModel by viewModels {
        MembersGroupFactory(DashboardRepository.getInstance())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.group_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        retrieveGroup()

        NavigationUI.setupWithNavController(binding.botNav, navController)
        bottomNavDestListener()


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.groupHome -> {
                    binding.groupToolbarLayout.visibility = View.VISIBLE
                    window?.statusBarColor = getColor(R.color.white)
                    binding.groupActivityName.visibility = View.VISIBLE
                }

                R.id.groupChat -> {
                    window?.statusBarColor = getColor(R.color.white)
                    binding.groupToolbarLayout.visibility = View.VISIBLE
                    binding.groupActivityName.visibility = View.VISIBLE
                    window?.statusBarColor = getColor(R.color.white)
                }
                R.id.members -> {
                    window?.statusBarColor = getColor(R.color.bright_blue)
                    binding.groupToolbarLayout.visibility = View.GONE
                    binding.groupActivityName.visibility = View.GONE
                }
                R.id.requests -> {
                    window?.statusBarColor = getColor(R.color.white)
                    binding.groupToolbarLayout.visibility = View.VISIBLE
                    binding.groupActivityName.visibility = View.VISIBLE

                }



            }
        }
    }

    private fun retrieveGroup() {
        intent?.let {

            val string = it.getStringExtra(getString(R.string.group_id))!!
            val group = Gson().fromJson(string, GroupResponse::class.java)
            viewModel.setGroup(group)
            binding.groupActivityName.text = group.title
        }
    }

    private fun bottomNavDestListener() {
        binding.botNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.group_chat -> {
                    navController.navigate(R.id.groupChat)
                    true
                }
                R.id.group_members -> {
                    navController.navigate(R.id.members)
                    true
                }
                R.id.group_home -> {
                    navController.navigate(R.id.groupHome)
                    true
                }
                R.id.group_requests  -> {
                    navController.navigate(R.id.requests)
                    true
                }



                else -> false
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}