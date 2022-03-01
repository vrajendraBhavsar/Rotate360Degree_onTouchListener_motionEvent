package com.erdemtsynduev.rotate360degree.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erdemtsynduev.rotate360degree.R
import com.erdemtsynduev.rotate360degree.databinding.FragmentProductDetailBinding
import com.erdemtsynduev.rotate360degree.ui.common.BaseFragment

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val ProductDetailFragmentArgs: ProductDetailFragmentArgs by navArgs()

    override fun getClassName(): String = ProductDetailFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.tbProductDetails.ibBack.setOnClickListener(::navigateToProductFragment)
        setToolbarTitle("Product Detail")
    }

    private fun navigateToProductFragment(view: View?) {
        findNavController().popBackStack()
    }

}