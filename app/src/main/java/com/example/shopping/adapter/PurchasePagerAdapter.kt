package com.example.shopping.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.shopping.PurchaseListFragment
import com.example.shopping.data.Purchase

class PurchasePagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val ARCHIVE_FRAGMENT_INDEX = 1
        const val NEW_PURCHASE_INDEX = 0
    }
    private val fragments : MutableList<Fragment> = mutableListOf()
    private val titles : MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun addViewPagerFragment(fragment : Fragment, title : String) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun addPurchase(purchase : List<Purchase>) {
        (fragments[NEW_PURCHASE_INDEX] as PurchaseListFragment).addItem(purchase)
    }

    fun archiveAll() {
        (fragments[NEW_PURCHASE_INDEX] as PurchaseListFragment).archiveAll()

    }

    fun addAllPurchaseToArchive(purchase : List<Purchase>) {
        (fragments[ARCHIVE_FRAGMENT_INDEX] as PurchaseListFragment).setItem(purchase)

    }
}