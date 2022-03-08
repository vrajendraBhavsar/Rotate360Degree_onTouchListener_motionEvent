package com.example.product360view.presentation.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.product360view.R
import com.example.product360view.common.utils.KeyUtils
import com.example.product360view.presentation.common.Product360BaseFragment

class ProductDetailFragment : Product360BaseFragment() {
    private var imageList: ArrayList<String> = ArrayList()

    override fun getContentResource(): Int = R.layout.fragment_product_detail

    override fun initViews() {
        super.initViews()
    }

    override fun getBundle() {
        super.getBundle()
        arguments.let {
            imageList = it?.getParcelable(KeyUtils.IMAGE_LIST)!!    //???
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}