package com.erdemtsynduev.rotate360degree.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.databinding.ProductImageBinding
import com.erdemtsynduev.rotate360degree.model.Product
import kotlinx.android.synthetic.main.simple_image_360.view.*

class ProductAdapter(
    private var productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val productBinding: ProductImageBinding) :
        RecyclerView.ViewHolder(productBinding.root) {

        fun bind(product: Product) {
            when(product.title) {
                "car" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding){
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
                "bottle" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding){
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
                "shoes" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding){
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
            }
        }
    }

}