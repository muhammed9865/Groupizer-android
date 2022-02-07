package com.example.groupizer.ui.dashboard

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.viewpager2.widget.ViewPager2
import com.example.groupizer.R
import com.example.groupizer.databinding.ActivityDashboardBinding
import com.example.groupizer.databinding.SignOutDialogBinding
import com.example.groupizer.ui.auth.AuthActivity
import com.example.groupizer.ui.dashboard.utils.FragmentsAdapter
import com.example.groupizer.ui.saveToken
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.log

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var pager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.dashboardToolbar)
        pager = binding.pager
        pager.adapter = FragmentsAdapter(this)
        buildUI()


        // Will save the token received from Auth Activity
        saveToken()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        /*(menu.findItem(R.id.logout)).setOnMenuItemClickListener {
            logOut()
            true
        }*/

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as android.widget.SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Log.d(TAG, "onOptionsItemSelected: log out")
                logOut()

            }
        }
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        logOut()
    }

    private fun buildUI() {
        val texts = listOf("Home", "Explore")
        val icons = listOf(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_explore_24)
        TabLayoutMediator(binding.tabsLayout, pager) { tab, position ->
            tab.text = texts[position]
            tab.icon = getDrawable(icons[position])
        }.attach()
    }

    fun goToExplore() {
        Log.d(TAG, "goToExplore: clicked")
        binding.tabsLayout.getTabAt(1)!!.select()
    }

    fun logOut() {
        val dialog = Dialog(this)
        val binding = SignOutDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(binding.root)
        dialog.show()
        dialog.apply {
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            binding.proceedSignout.setOnClickListener {
                dismiss()
                cancel()
                val intent = Intent(this@DashboardActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.cancelSignOut.setOnClickListener {
                dismiss()
                cancel()
            }

            binding.closeDialog.setOnClickListener {
                dismiss()
                cancel()
            }


        }

    }



    companion object {
        private const val TAG = "DashboardActivity"
    }
}