package com.example.shopping

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.example.shopping.adapter.PurchasePagerAdapter
import com.example.shopping.data.Purchase
import com.example.shopping.extension.addOnPageSelectedListener
import com.example.shopping.extension.toByte
import com.example.shopping.presenter.MainPresenter
import com.example.shopping.presenter.MainView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_alert.*
import kotlinx.android.synthetic.main.custom_alert.view.*
import kotlinx.android.synthetic.main.custom_alert.view.input_layout
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    companion object {
        const val NEW_FRAGMENT_INDEX = 0
        const val ARCHIVE_FRAGMENT_INDEX = 1

        const val REQUEST_IMAGE_CAPTURE = 1234

        const val REQUEST_READ_PERMISSION_CODE = 1235
        const val REQUEST_OPEN_IMAGE_CODE = 2345
    }

    private lateinit var alertDialogView: View


    private val pagerAdapter: PurchasePagerAdapter by lazy {
        PurchasePagerAdapter(supportFragmentManager)
    }

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        presenter.onAttach(this)

        pagerAdapter.apply {
            addViewPagerFragment(
                    PurchaseListFragment.newPurchaseInstance(),
                    getString(R.string.list_new_item)
            )
            addViewPagerFragment(
                    PurchaseListFragment.newArchivePurchaseInstance(),
                    getString(R.string.list_archive_item)
            )
        }
        main_viewpager.adapter = pagerAdapter
        main_tab.setupWithViewPager(main_viewpager)

        main_viewpager.addOnPageSelectedListener { position ->
            when (position) {
                NEW_FRAGMENT_INDEX -> main_fab.show()
                ARCHIVE_FRAGMENT_INDEX -> main_fab.hide()
            }
        }

        main_fab.setOnClickListener {
            addNewPurchaseDialog()
        }

    }

    private fun addNewPurchaseDialog() {
        alertDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert, null).apply {
            this.add_new_photo.setOnClickListener {
                selectWhereTakePhotoFrom()
            }
        }
        val alertDialog = AlertDialog.Builder(this)
        alertDialog
                .setView(alertDialogView)
                .setPositiveButton(getString(R.string.alert_dialog_add), null)
                .setNegativeButton(getString(R.string.alert_dialog_cancel)) { dialog, _ -> dialog.dismiss() }
                .setTitle(getString(R.string.alert_dialog_add_title))
        val dialog = alertDialog.create()
        dialog.setOnShowListener { dialog ->
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val drawable = alertDialogView.add_new_photo?.drawable
                val bitmap = drawable?.toBitmap(width = 100, height = 100)
                val bitmapByteArray = bitmap?.toByte()
                val text = alertDialogView.add_description?.text?.toString() ?: ""

                if (text.isNotBlank()){
                    bitmapByteArray?.let { presenter.addPurchase(bitmapByteArray, text) }
                    dialog.dismiss()
                } else {
                    alertDialogView.input_layout.error = getString(R.string.validation_error)
                }
            }
        }
        dialog.show()
    }


    private fun selectWhereTakePhotoFrom() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog
                .setTitle(getString(R.string.alert_dialog_select_title))
                .setMessage(getString(R.string.alert_dialog_select_msg))
                .setPositiveButton(getString(R.string.alert_dialog_take_photo)) { _, _ -> dispatchTakePictureIntent() }
                .setNeutralButton(getString(R.string.alert_dialog_cancel)) { _, _ -> }
                .setNegativeButton(getString(R.string.alert_dialog_select_from_storage)) { _, _ ->
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                REQUEST_READ_PERMISSION_CODE
                        )
                    } else {
                        navigateToStorage()
                    }
                }
                .show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun navigateToStorage() {
        val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, REQUEST_OPEN_IMAGE_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_READ_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateToStorage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = (data?.extras?.get("data") as Bitmap)
                    Glide
                            .with(alertDialogView.add_new_photo)
                            .load(bitmap)
                            .into(alertDialogView.add_new_photo)
                }
            }
            REQUEST_OPEN_IMAGE_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    Glide
                            .with(alertDialogView.add_new_photo)
                            .load(data?.data)
                            .into(alertDialogView.add_new_photo)
                }
            }
        }

    }

    override fun showNewItem(purchase: Purchase) {
        pagerAdapter.addPurchase(listOf(purchase))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun showArchive(data: List<Purchase>) {
        pagerAdapter.addAllPurchaseToArchive(data)
    }

    private fun archiveAll() {
        presenter.archiveAll()
        pagerAdapter.archiveAll()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.archive -> {
                archiveAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDettach()
    }
}