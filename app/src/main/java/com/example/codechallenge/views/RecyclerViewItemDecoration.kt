package com.example.codechallenge.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.codechallenge.views.GridLayoutViewItemDecoration.getItemOffsets
import com.example.codechallenge.views.LinearLayoutViewItemDecoration.getItemOffsets

class RecyclerViewItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            getItemOffsets(
                outRect,
                (layoutManager as GridLayoutManager?)!!,
                parent.getChildViewHolder(view).adapterPosition,
                state.itemCount,
                space
            )
        } else if (layoutManager is LinearLayoutManager) {
            getItemOffsets(
                outRect,
                (layoutManager as LinearLayoutManager?)!!,
                parent.getChildViewHolder(view).adapterPosition,
                state.itemCount,
                space
            )
        }
    }
}

