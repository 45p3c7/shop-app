package com.example.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping.adapter.PurchaseListAdapter
import com.example.shopping.data.Purchase
import com.example.shopping.data.PurchaseType
import com.example.shopping.presenter.PurchasePresenter
import com.example.shopping.presenter.PurchaseView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_purchase_list.*
import javax.inject.Inject

class PurchaseListFragment : DaggerFragment(), PurchaseView {

    @Inject
    lateinit var presenter : PurchasePresenter

    private val adapter = PurchaseListAdapter()

    companion object {

        const val PURCHASE = "purchase_type"

        fun newPurchaseInstance(): PurchaseListFragment {
            return PurchaseListFragment().apply {
                val args = Bundle()
                args.putParcelable(PURCHASE, PurchaseType.NEW)
                this.arguments = args
            }
        }

        fun newArchivePurchaseInstance(): PurchaseListFragment {
            return PurchaseListFragment().apply {
                val args = Bundle()
                args.putParcelable(PURCHASE, PurchaseType.ARCHIVE)
                this.arguments = args
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        presenter.load(arguments?.getParcelable(PURCHASE))

        purchase_list.layoutManager = LinearLayoutManager(context)
        purchase_list.adapter = adapter


    }

    override fun showPurchase(list: List<Purchase>) {
        adapter.setData(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDettach()
    }

    fun setItem(purchase: List<Purchase>) {
        adapter.setData(purchase)
    }

    fun addItem(purchase : List<Purchase>) {
        adapter.addData(purchase)
    }

    fun archiveAll() {
        adapter.deleteAll()
    }
}