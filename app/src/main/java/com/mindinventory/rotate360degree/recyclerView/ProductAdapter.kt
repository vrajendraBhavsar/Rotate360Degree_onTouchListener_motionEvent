package com.mindinventory.rotate360degree.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindinventory.rotate360degree.common.extensions.toBinding
import com.mindinventory.rotate360degree.databinding.ProductImageBinding
import com.mindinventory.rotate360degree.model.Product
import com.mindinventory.rotate360degree.ui.common.BaseRecyclerViewAdapter

class ProductAdapter(val onItemClicked: (product: Product) -> Unit) : BaseRecyclerViewAdapter<Product, ProductAdapter.ProductViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ProductViewHolder(private val productBinding: ProductImageBinding) :
        RecyclerView.ViewHolder(productBinding.root), ItemClickListener {

        fun bind(product: Product) {
            /*On click of individual item, it's data will be sent to the detail page*/
            productBinding.cv1.setOnClickListener {
                this.itemClicked(product = product)
            }
            when (product.title) {
                "car" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding) {
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
                "bottle" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding) {
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
                "shoes" -> {
                    productBinding.tvItemLabel.text = product.title
                    with(productBinding) {
                        Glide.with(root.context)
                            .asBitmap()
                            .load(product.imageList[0])
                            .placeholder(ivItem.drawable)
                            .into(ivItem)
                    }
                }
            }
//            productBinding.tvItemLabel.setText(product.description)
        }

        override fun itemClicked(product: Product) {
            onItemClicked.invoke(product)
        }
    }

    interface ItemClickListener {
        fun itemClicked(product: Product)
    }
}