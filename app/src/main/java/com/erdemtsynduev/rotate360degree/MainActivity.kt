package com.erdemtsynduev.rotate360degree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdemtsynduev.rotate360degree.databinding.ActivityMainBinding
import com.erdemtsynduev.rotate360degree.model.DataProvider
import com.erdemtsynduev.rotate360degree.recyclerView.Image360Adapter
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productAdapter: Image360Adapter by lazy {
        Image360Adapter(DataProvider.getProductList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val myLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        // pass it to rvLists layoutManager
        binding.rv360List.apply {
            layoutManager = myLayoutManager
            adapter = productAdapter
        }
    }
}