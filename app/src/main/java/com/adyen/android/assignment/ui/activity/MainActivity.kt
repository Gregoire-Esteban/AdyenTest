package com.adyen.android.assignment.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.ui.ErrorType
import com.adyen.android.assignment.ui.ReorderDialog
import com.adyen.android.assignment.ui.adapter.AstronomyPictureAdapter
import com.adyen.android.assignment.ui.adapter.AstronomyPictureItemCallback
import com.adyen.android.assignment.ui.viewmodel.AstronomyListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AstronomyPictureItemCallback {

    private val viewModel: AstronomyListViewModel by viewModel()
    lateinit var binding: ActivityMainBinding
    private var adapter: AstronomyPictureAdapter? = null
    private val dialog : ReorderDialog by lazy {
        ReorderDialog.newInstance(
            onApplyClicked = { isSortingByDate ->
                viewModel.applySorting(isSortingByDate)
                dialog.dismiss()
            },
            onResetClicked = {
                viewModel.applySorting(true)
                dialog.dismiss()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        setContentView(binding.root)
        initUI()
        initObservables()
        if(viewModel.displayElements.value == null){
            viewModel.getPictureList()
        }
    }

    private fun initUI() {
        binding.picturesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.errorLayout.errorButton.setOnClickListener {
            viewModel.onErrorButtonClicked(binding.errorLayout.errorType ?: ErrorType.NO_ERROR)
        }
    }

    private fun initObservables() {
        viewModel.displayElements.observe(this) {
            it?.let { validItemList ->
                if (adapter == null) {
                    adapter = AstronomyPictureAdapter(validItemList, this)
                    binding.picturesList.adapter = this.adapter
                } else {
                    adapter?.updateList(validItemList)
                }
            }
        }
    }

    override fun onItemClicked(astronomyPicture: AstronomyPicture) {
        startActivity(Intent(this, PictureDetailsActivity::class.java).apply {
            putExtra(EXTRA__PICTURE, astronomyPicture)
        })
    }

    fun onReorderClicked() {
        dialog.show(supportFragmentManager, REORDER_DIALOG)
    }

    companion object {
        private const val REORDER_DIALOG = "REORDER_DIALOG"
        private const val EXTRA__PICTURE = "EXTRA__PICTURE"
    }
}