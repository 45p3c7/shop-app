package com.example.shopping.extension

import androidx.viewpager.widget.ViewPager

fun ViewPager.addOnPageSelectedListener(listener: (Int) -> Unit) {
     this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            listener(position)

        }
    })
}