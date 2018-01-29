package com.roix.mapchat.utils.binding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.ui.common.viewmodels.BaseListViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:srcVector")
fun setSrcVector(imageView: ImageView, @DrawableRes res: Int) {
    imageView.setImageResource(res)
}

@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
}

@BindingAdapter("bind:refreshing")
fun setSrcCompatRefreshing(layout: SwipeRefreshLayout, state: BaseListViewModel.StateList) {
    layout.isRefreshing = state == BaseListViewModel.StateList.REFRESH
}


@BindingAdapter("bind:tint")
fun setTint(view: ImageView, @ColorRes colorRes: Int) {
    view.setColorFilter(ContextCompat.getColor(view.context, colorRes))
}

@BindingAdapter("bind:tintColor")
fun setTintColor(view: ImageView, color: Int) {
    view.setColorFilter(color)
}

@BindingAdapter("bind:statusTextViewState")
fun setTintColor(view: TextView, item:GroupItem) {
    if(item.mStatus == GroupItem.Status.OWNER){
        view.setTextColor(view.resources.getColor(R.color.colorAccent))
        view.setText(R.string.status_owner)
    }else if(item.mStatus ==GroupItem.Status.MEMBER){
        view.setTextColor(view.resources.getColor(R.color.colorPrimary))
        view.setText(R.string.status_member)
    }else{
        view.setText(R.string.status_not_member)
    }
}


