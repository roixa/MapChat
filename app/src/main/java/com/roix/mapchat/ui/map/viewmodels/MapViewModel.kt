package com.roix.mapchat.ui.map.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.buissness.map.IMapInteractor
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.toothpick.map.MapModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import toothpick.config.Module
import javax.inject.Inject

/**
 * Created by roix on 06.01.2018.
 */
class MapViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IMapInteractor

    val markerText = MutableLiveData<String>()
    val choosenIcon = MutableLiveData<IconItem>()
    val icons: ObservableList<IconItem> = ObservableArrayList<IconItem>()


    override fun getModule(): Module = MapModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        interactor.getItems().sub {
            choosenIcon.value = it[0]
            icons.addAll(it)
        }
    }

    fun onClickedCreateMarker(latLng: LatLng) {

    }

    fun onClickedIconInCreateDialog(pos: Int) {
        choosenIcon.value = icons[pos]
    }
}
