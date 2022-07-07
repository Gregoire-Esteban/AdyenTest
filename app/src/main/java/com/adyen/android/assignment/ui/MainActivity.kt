package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.ui.adapter.AstronomyPictureAdapter
import com.adyen.android.assignment.ui.viewmodel.AstronomyListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AstronomyListViewModel by viewModel()
    lateinit var binding: ActivityMainBinding
    private var adapter: AstronomyPictureAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            activity = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        setContentView(binding.root)
        initUI()
        initObservables()
        viewModel.getPictureList()
    }

    private fun initUI() {
        binding.picturesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    private fun initObservables() {
        viewModel.elements.observe(this, Observer {
            it?.let { validItemList ->
                if (adapter == null) {
                    adapter = AstronomyPictureAdapter(validItemList)
                    binding.picturesList.adapter = this.adapter
                } else {
                    adapter?.updateList(validItemList)
                }
            }
        })
    }

    fun onReorderClicked() {
        // TODO : open dialog
    }
}