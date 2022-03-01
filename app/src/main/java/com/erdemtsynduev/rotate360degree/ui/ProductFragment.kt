package com.erdemtsynduev.rotate360degree.ui

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.app.miandroidbasestructure.common.extensions.safeNavigate
import com.erdemtsynduev.rotate360degree.databinding.FragmentProductBinding
import com.erdemtsynduev.rotate360degree.model.DataProvider
import com.erdemtsynduev.rotate360degree.model.Product
import com.erdemtsynduev.rotate360degree.recyclerView.ProductAdapter
import com.erdemtsynduev.rotate360degree.ui.common.BaseFragment

class ProductFragment : BaseFragment<FragmentProductBinding>() {

    override fun getClassName(): String = ProductFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProductBinding {
        return FragmentProductBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        initRecyclerView()
        productAdapter.addAll(DataProvider.getProductList())
    }

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(::onItemClicked)
    }

    private fun onItemClicked(product: Product) {
        findNavController().safeNavigate(
            ProductFragmentDirections.actionProductFragmentToProductDetailFragment(
                product = product
            )
        )
    }

    private fun initRecyclerView() {
        binding.rv360List.adapter = productAdapter
    }
}