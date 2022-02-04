package com.example.groupizer.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.groupizer.R
import com.example.groupizer.pojo.model.LoginForm

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

       intent?.let {
           val login = it.getParcelableExtra<LoginForm>(getString(R.string.current_user))
           login?.let {
               Toast.makeText(this, "${it.email} ${it.password} dashboard", Toast.LENGTH_LONG).show()
           } ?: Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
       } ?: Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
    }
}