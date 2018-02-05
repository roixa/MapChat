package com.roix.mapchat.data.repositories.icons

import com.roix.mapchat.R
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class IconsRepository : IIconsRepository {

    @Inject constructor() {}

    override fun getMarkersIcons(): Single<List<IconItem>> = Single.create { e ->
        e.onSuccess(
                arrayListOf(
                        IconItem("plus", R.drawable.ic_add_black, 0),
                        IconItem("trash", R.drawable.ic_delete_black, 1),
                        IconItem("heart", R.drawable.ic_favorite_black, 2),
                        IconItem("hand", R.drawable.ic_pan_tool_black, 3),
                        IconItem("flag", R.drawable.ic_flag_black, 4),
                        IconItem("pet", R.drawable.ic_pets_black, 5),
                        IconItem("send", R.drawable.ic_send_black, 6),
                        IconItem("shop", R.drawable.ic_shopping_cart_black, 7)
                ))
    }

    override fun getUsersIcons(): Single<List<IconItem>> = Single.create { e ->
        e.onSuccess(
                arrayListOf(
                        IconItem("walk", R.drawable.ic_directions_walk_black, 0),
                        IconItem("bike", R.drawable.ic_directions_bike_black, 1),
                        IconItem("car", R.drawable.ic_directions_car_black, 2),
                        IconItem("truck", R.drawable.ic_local_shipping_black, 3),
                        IconItem("group", R.drawable.ic_group_black, 4),
                        IconItem("cross", R.drawable.ic_accessibility_black, 5),
                        IconItem("android", R.drawable.ic_android_black, 6),
                        IconItem("male", R.drawable.ic_face_black, 7),
                        IconItem("female", R.drawable.ic_tag_faces_black, 8)
                ))
    }

}
