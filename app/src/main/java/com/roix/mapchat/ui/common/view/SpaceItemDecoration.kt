package com.roix.mapchat.ui.common.view

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.roix.mapchat.R


/**
 * Created by roix on 27.01.2018.
 */
class SpaceItemDecoration : RecyclerView.ItemDecoration {

    private val mHeight: Int
    private val mWidht: Int

    @DimenRes
    private val DEFAULT_HEIGHT = R.dimen.nano
    private val DEFAULT_WIDGHT = R.dimen.nano

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = this.mHeight
        outRect.left=this.mWidht
        outRect.right=this.mWidht

    }

    constructor( context: Context) {
        this.mHeight = context.getResources().getDimensionPixelSize(DEFAULT_HEIGHT)
        this.mWidht=context.getResources().getDimensionPixelSize(DEFAULT_WIDGHT)
    }

    constructor( context: Context, @DimenRes resId: Int) {
        this.mHeight = context.getResources().getDimensionPixelSize(resId)
        this.mWidht=context.getResources().getDimensionPixelSize(resId)

    }

}
