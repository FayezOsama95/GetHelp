package com.am.gethelp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.am.gethelp.R.string
import com.am.gethelp.databinding.ActivityDrawerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_drawer.logout
import kotlinx.android.synthetic.main.app_bar_drawer.toolbar

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_drawer)

        setSupportActionBar(toolbar)
        setupBottomNavigation()
        setupDrawerNavigation()
    }

    private fun setupDrawerNavigation() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            string.navigation_drawer_open,
            string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        mBinding.drawerNavigationView.setNavigationItemSelectedListener(this@DrawerActivity)
        val drawerHeader = mBinding.drawerNavigationView.getHeaderView(0)
        drawerHeader.findViewById<TextView>(R.id.userNameTextView).text = "سعاد (إسم مستعار)"
        Picasso.get().load(R.drawable.ic_female_3).placeholder(resources.getDrawable(R.drawable.ic_female_3))
            .error(resources.getDrawable(R.drawable.ic_female_3))
            .into(drawerHeader.findViewById<ImageView>(R.id.avatarImageView))

        logout.setOnClickListener {
            this@DrawerActivity.finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }



    }

    private fun setupBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.bottomNavHomeFragment,
                R.id.bottomNavLibraryFragment,
                R.id.bottomFragmentNotificationFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> startActivity(Intent(this@DrawerActivity, CaseActivity::class.java))
            R.id.nav_council_leader -> startActivity(Intent(this@DrawerActivity, ContactActivity::class.java))
        }
        return false
    }

}
