package com.roix.mapchat.utils.binding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.ui.common.viewmodels.BasePaginationListViewModel

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
    Log.d("boux", "setSrcVector " + res)
    imageView.setImageResource(res)
    imageView.refreshDrawableState()
}

@BindingAdapter("bind:icon")
fun setIcon(imageView: ImageView, icon: IconItem) {
    Log.d("boux", "setIcon " + icon.resId)
    imageView.setImageResource(icon.resId)
    imageView.refreshDrawableState()
}


@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
}

@BindingAdapter("bind:refreshing")
fun setSrcCompatRefreshing(layout: SwipeRefreshLayout, state: BasePaginationListViewModel.StateList) {
    layout.isRefreshing = state == BasePaginationListViewModel.StateList.REFRESH
}


@BindingAdapter("bind:tint")
fun setTint(view: ImageView, @ColorRes colorRes: Int) {
    view.setColorFilter(ContextCompat.getColor(view.context, colorRes))
}

@BindingAdapter("bind:errorText")
fun handleErrorText(view: TextInputEditText, isErrorCommand: Boolean?) {
    if (isErrorCommand == true) {
        view.error = view.context.getString(R.string.error_edit_text_common)
    } else {
        view.setError(null)
    }
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (isValidTextCommon(p0)) {
                view.setError(null)
            } else {
                view.error = view.context.getString(R.string.error_edit_text_common)
            }
        }

    })
}

fun isValidTextCommon(p0: CharSequence?): Boolean = p0 != null && p0.length > 3


@BindingAdapter("bind:tintColor")
fun setTintColor(view: ImageView, color: Int) {
    view.setColorFilter(color)
}

@BindingAdapter("bind:statusTextViewState")
fun setTintColor(view: TextView, item: GroupItem) {
    if (item.status == GroupItem.Status.OWNER) {
        view.setTextColor(view.resources.getColor(R.color.colorAccent))
        view.setText(R.string.status_owner)
    } else if (item.status == GroupItem.Status.MEMBER) {
        view.setTextColor(view.resources.getColor(R.color.colorPrimary))
        view.setText(R.string.status_member)
    } else {
        view.setText(R.string.status_not_member)
    }
}


