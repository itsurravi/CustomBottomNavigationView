package com.ravikantsharma.custombottomnavigationview

import android.animation.ValueAnimator.INFINITE
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
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

        createBottomNavigationMenu()

        binding.bottomNavigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            changeFragment(BlogFragment.getInstance())
        }
    }

    private fun createBottomNavigationMenu() {
        binding.apply {
            bottomNavigationView.menu.apply {
                add(Menu.NONE, 0, Menu.NONE, "Dashboard").icon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_home_2022)
                add(Menu.NONE, 1, Menu.NONE, "Video").icon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_video_2022)
                add(Menu.NONE, 2, Menu.NONE, "Qbank").icon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_qbank_2022)
                add(Menu.NONE, 3, Menu.NONE, "Test").icon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_mcq_2022)
//                add(Menu.NONE, 4, Menu.NONE, "Live").icon =
//                    getLottieDrawable("anim_live_video_inactive_red.json", bottomNavigationView)
                add(Menu.NONE, 5, Menu.NONE, "ME").icon =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.default_image)
            }
            bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

            addBadge(
                navigationView = bottomNavigationView,
                itemIndex = 0,
                layoutResId = R.layout.notification_badge
            )
            addBadge(
                navigationView = bottomNavigationView,
                itemIndex = 1,
                layoutResId = R.layout.notification_bade_new
            )
            addBadge(
                navigationView = bottomNavigationView,
                itemIndex = 2,
                layoutResId = R.layout.notification_badge
            )

            setProfile(
                navigationView = bottomNavigationView,
                itemIndex = 5
            )
        }
    }

    private val imageUrl = "https://cdn-icons-png.flaticon.com/512/124/124010.png"

    private fun setProfile(
        navigationView: BottomNavigationView,
        itemIndex: Int
    ) {
        val menu = navigationView.menu
        val menuItem = menu.findItem(itemIndex)

        navigationView.itemIconTintList = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            menuItem?.iconTintList = null
            menuItem?.iconTintMode = null
        }

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .apply(
                RequestOptions
                    .circleCropTransform()
                    .placeholder(R.drawable.default_image)
            )
            .into(object : CustomTarget<Bitmap>(24, 24) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    menuItem?.icon = BitmapDrawable(resources, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun addBadge(
        navigationView: BottomNavigationView,
        itemIndex: Int,
        @LayoutRes layoutResId: Int
    ) {
        val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(itemIndex) as BottomNavigationItemView

        val badge = LayoutInflater.from(this@MainActivity)
            .inflate(layoutResId, itemView, true)
    }

    private fun removeBadge(navigationView: BottomNavigationView, index: Int) {
        val bottomNavigationMenuView = navigationView.getChildAt(0) as BottomNavigationMenuView
        val itemView = bottomNavigationMenuView.getChildAt(index) as BottomNavigationItemView

        val badge: View? = itemView.findViewById(R.id.notifications_badge)
        if (badge != null) {
            (badge.parent as ViewGroup).removeView(badge)
        }
    }

    private fun getLottieDrawable(
        animation: String,
        view: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromAssetSync(
                view.context.applicationContext, animation
            )
            callback = view
            composition = result.value
            repeatCount = INFINITE
        }.also {
            it.playAnimation()
        }
    }

    private val mOnNavigationItemSelectedListener = OnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            0 -> {
                changeFragment(BlogFragment.getInstance())
                return@OnItemSelectedListener true
            }
            1 -> {
                changeFragment(ChapterFragment.getInstance())
                removeBadge(binding.bottomNavigationView, 1)
                return@OnItemSelectedListener true
            }
            else -> {
                changeFragment(StoreFragment.getInstance())
                return@OnItemSelectedListener true
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}