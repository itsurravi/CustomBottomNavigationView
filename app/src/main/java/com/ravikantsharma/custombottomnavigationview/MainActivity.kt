package com.ravikantsharma.custombottomnavigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.ravikantsharma.custombottomnavigationview.databinding.ActivityMainBinding
import com.ravikantsharma.custombottomnavigationview.fragment.BlogFragment
import com.ravikantsharma.custombottomnavigationview.fragment.ChapterFragment
import com.ravikantsharma.custombottomnavigationview.fragment.StoreFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)

        if(savedInstanceState == null) {
            changeFragment(BlogFragment.getInstance())
        }
    }

    private val mOnNavigationItemSelectedListener = OnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_blog -> {
                changeFragment(BlogFragment.getInstance())
                return@OnItemSelectedListener true
            }
            R.id.navigation_chapter -> {
                changeFragment(ChapterFragment.getInstance())
                return@OnItemSelectedListener true
            }
            R.id.navigation_store -> {
                changeFragment(StoreFragment.getInstance())
                return@OnItemSelectedListener true
            }
        }
        false
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}