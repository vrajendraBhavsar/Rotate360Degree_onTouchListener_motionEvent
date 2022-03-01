package com.erdemtsynduev.rotate360degree.common.extensions

import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Throws(Exception::class)
fun RecyclerView.loadMore(loadMoreCondition: () -> Boolean, loadMore: (() -> Unit)) {
    val lm = this.layoutManager ?: throw Throwable("Please bind the layout manage")

    if (lm is LinearLayoutManager) {
        lm.let {
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = lm.childCount
                    val totalItemCount = lm.itemCount
                    val firstVisibleItemPosition = lm.findFirstVisibleItemPosition()

                    if (!loadMoreCondition.invoke())
                        return

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        loadMore.invoke()
                    }
                }
            })
        }
    } else {
        throw Throwable("Layout manager not supported.")
    }
}

fun ScrollView.loadMoreWithCoOrdinate(loadMoreCondition: () -> Boolean, loadMore: (() -> Unit)) {
    this.viewTreeObserver?.addOnScrollChangedListener {
        val view = this.getChildAt(this.childCount - 1) as View
        val diff: Int = view.bottom - (this.height + this
            .scrollY)
        if (diff == 0 && loadMoreCondition.invoke()) {
            loadMore.invoke()
        }
    }
}
