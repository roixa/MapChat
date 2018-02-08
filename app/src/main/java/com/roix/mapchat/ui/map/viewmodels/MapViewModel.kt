package com.roix.mapchat.ui.map.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.google.android.gms.maps.model.LatLng
import com.roix.mapchat.buissness.map.IMapInteractor
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.data.models.MarkerItem
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

    val focusLocation = MutableLiveData<LatLng>()

    //touch marker data
    val touchMarkerPos = MutableLiveData<LatLng>()

    //markers data
    val markers = MutableLiveData<List<MarkerItem>>()

    //new marker dialog data
    val markerText = MutableLiveData<String>()
    val choosenIcon = MutableLiveData<IconItem>()
    val markerIcons: ObservableList<IconItem> = ObservableArrayList<IconItem>()


    //from root viewmodel need for interactor
    lateinit var currentGroup: GroupItem

    override fun getModule(): Module = MapModule()

    override fun onBindFirstView() {
        super.onBindFirstView()
        touchMarkerPos.value = null
        interactor.getIcons().sub {
            choosenIcon.value = it[0]
            markerIcons.addAll(it)
        }
    }

    fun onGetCurrentGroup(groupItem: GroupItem) {
        currentGroup = groupItem
        interactor.getMarkers(currentGroup.ownerUUid).toObservable().sub {
            if (!it.isEmpty() && markers.value == null) {
                focusLocation.value = it.last().latLng
            }
            markers.value = it
        }

    }

    fun onClickedCreateMarkerAndAnimatedToMap() {
        interactor.addMarker(currentGroup.ownerUUid, markerText.value!!, touchMarkerPos.value!!,
                            choosenIcon.value!!.pos, currentGroup.client!!.name, currentGroup.client!!.uid).sub {
                    touchMarkerPos.value = null
                }
    }

    fun onClickedIconInCreateDialog(pos: Int) {
        choosenIcon.value = markerIcons[pos]
    }

    fun clearState(){
        focusLocation.value=null
        touchMarkerPos.value=null
        markers.value=null
        markerText.value=null
    }
}
