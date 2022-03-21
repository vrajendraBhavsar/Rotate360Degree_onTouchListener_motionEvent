package com.erdemtsynduev.rotate360degree.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erdemtsynduev.rotate360degree.data.DataProvider
import com.erdemtsynduev.rotate360degree.databinding.FragmentProductDetailBinding
import com.erdemtsynduev.rotate360degree.model.Product
import com.erdemtsynduev.rotate360degree.ui.common.BaseFragment


class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    private val TAG: String = ProductDetailFragment::class.java.simpleName

    private val productDetailFragmentArgs: ProductDetailFragmentArgs by navArgs()

    override fun getClassName(): String = ProductDetailFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        Log.d(TAG, "HASTALAVISTA initViews: ")
        init360Image(product = productDetailFragmentArgs.product)
        binding.tbProductDetails.ibBack.setOnClickListener(::navigateToProductFragment)
        setToolbarTitle("Product Detail")
        bindData()
    }

    @SuppressLint("LogNotTimber")
    private fun init360Image(product: Product) {
        with(this.binding) {
            when (product.title) {
                "car" -> {
                    customSeekBar.setImageList(DataProvider.getCarImageList())   //Testing custom view
                }
                "bottle" -> {
                    customSeekBar.setImageList(DataProvider.getBottleImageList())   //Testing custom view
                }
                "shoes" -> {
                    customSeekBar.setImageList(DataProvider.getShoesImageList())   //Testing custom view
                }
            }
        }
    }

    private fun navigateToProductFragment(view: View?) {
        findNavController().popBackStack()
    }

    private fun bindData() {
        binding.tvProductName.text = productDetailFragmentArgs.product.title
        binding.tvProductDescription.text =
            requireContext().getText(productDetailFragmentArgs.product.description)
    }
}