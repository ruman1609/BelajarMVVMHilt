package com.rudyrachman16.belajarmvvmhilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.rudyrachman16.belajarmvvmhilt.core.utils.CategorySetter
import com.rudyrachman16.belajarmvvmhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.toolbarMain.title = CategorySetter.getCategory()
        setSupportActionBar(bind.toolbarMain)

        navController =
            (supportFragmentManager.findFragmentById(bind.mainNavFragmentHolder.id) as NavHostFragment).navController

        appBarConfiguration = AppBarConfiguration.Builder().build()


        NavigationUI.setupActionBarWithNavController(
            this, navController, appBarConfiguration
        )
    }

    fun setToolbarTitle(title: String) {
        bind.toolbarMain.title = title
    }

    fun showOrHideToolbar() {
        val count =
            (supportFragmentManager.findFragmentById(bind.mainNavFragmentHolder.id) as NavHostFragment).childFragmentManager.backStackEntryCount
        supportActionBar?.setDisplayHomeAsUpEnabled(count >= 1)
        supportActionBar?.setHomeButtonEnabled(count >= 1)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
}