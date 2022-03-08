package com.example.product360view.presentation.productDetail

import android.os.Bundle
import com.example.product360view.R
import com.example.product360view.presentation.common.Product360BaseActivity
import com.livefront.bridge.Bridge
import com.livefront.bridge.SavedStateHandler
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

class Product360Activity : Product360BaseActivity() {

    override fun getContentResource() = R.layout.activity_product_360

    override fun initViews() {
        super.initViews()
        Bridge.initialize(applicationContext, object : SavedStateHandler {
            override fun saveInstanceState(@NonNull target: Any, @NonNull state: Bundle) {
            }

            override fun restoreInstanceState(@NonNull target: Any, @Nullable state: Bundle?) {
            }
        })
        initiateFragment()
    }

    private fun initiateFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.ftContainer,
                ProductDetailFragment()
            )
    }
}
