package com.roix.mapchat.ui.map.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.roix.mapchat.R
import com.roix.mapchat.data.models.MarkerItem
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.databinding.DialogCreateMarkerBinding
import com.roix.mapchat.databinding.FragmentMapBinding
import com.roix.mapchat.databinding.IconItemBinding
import com.roix.mapchat.ui.common.adapters.BaseObservableAdapter
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.map.viewmodels.MapViewModel
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.utils.ui.DateTimeUtils
import com.roix.mapchat.utils.ui.GeneralUtils
import com.roix.mapchat.utils.ui.ItemClickSupport
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@RuntimePermissions
class MapFragment : BaseDatabindingFragment<MapViewModel, FragmentMapBinding>(),
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    var touchMarker: Marker? = null
    var map: GoogleMap? = null
    val markerPairs: MutableList<Pair<MarkerItem, Marker>> = mutableListOf()

    override fun getLayoutId(): Int = R.layout.fragment_map

    override fun setupUi() {
        super.setupUi()
        val rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.activeGroup.sub {
            if (it != null) viewModel.onGetCurrentGroup(it)
        }
        viewModel.touchMarkerPos.sub {
            handleTouchMarker(it)
        }
        viewModel.markers.sub {
            if (it != null) showMarkers(it)
        }
        viewModel.focusLocation.sub {
            if (it != null) moveMapToLocation(it, {})
        }
    }

    override fun setupBinding() {
        super.setupBinding()
        binding.fab.setOnClickListener {
            moveMapToLocation(touchMarker?.position!!, {
                onClickedMarkerFabAndAnimationEnd()
            })
        }

        binding.mapView.onCreate(arguments)
        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.mapView.getMapAsync(this)

    }

    override fun onMapClick(latLng: LatLng?) {
        viewModel.touchMarkerPos.value = latLng
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private fun onClickedMarkerFabAndAnimationEnd() {
        val dialogBinding = DialogCreateMarkerBinding.inflate(LayoutInflater.from(activity),
                null,
                false)
        dialogBinding.viewmodel = viewModel
        dialogBinding.rv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = BaseObservableAdapter<IconItem, IconItemBinding>(viewModel.markerIcons, R
                    .layout.icon_item)
        }
        ItemClickSupport.addTo(dialogBinding.rv).setOnItemClickListener { recyclerView, i, view ->
            viewModel.onClickedIconInCreateDialog(i)
            //TODO very strange bug
            dialogBinding.ivIcon.setImageResource(viewModel.choosenIcon.value!!.resId)
        }
        AlertDialog.Builder(activity).setView(dialogBinding.root).setTitle(R.string
                .create_marker_dialog_title).setPositiveButton(R.string.text_dialog_ok,
                { d, i ->
                    viewModel.onClickedCreateMarkerAndAnimatedToMap()
                }).show()

    }

    private fun showMarkers(markers: List<MarkerItem>) {
        for (marker in markers) {
            val found = markerPairs.find { pair -> pair.first == marker }
            if (found == null) {
                val iconRes = viewModel.markerIcons.find { it.pos == marker.iconPos }!!.resId
                val descriptor = GeneralUtils.vectorToBitmap(activity, iconRes, Color.BLACK)
                val mapMarker = map?.addMarker(MarkerOptions()
                        .position(marker.latLng)
                        .title(marker.name)
                        .icon(descriptor)
                        .snippet(DateTimeUtils.convertToDateFormat(marker.time)))
                val pair = Pair(marker, mapMarker!!)
                markerPairs.add(pair)

            }
        }

    }

    private fun handleTouchMarker(location: LatLng?) {
        if (location == null) {
            hideFab()
            touchMarker?.remove()
            touchMarker = null
            return
        }
        if (touchMarker == null) {
            showFab()
            touchMarker = map?.addMarker(MarkerOptions().position(location))
        } else {
            touchMarker?.setPosition(location)
        }

    }

    private fun moveMapToLocation(location: LatLng, func: () -> Unit) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f), object : GoogleMap
        .CancelableCallback {
            override fun onCancel() {}
            override fun onFinish() {
                func.invoke()
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        MapFragmentPermissionsDispatcher.setupLocationWithCheck(this, map)
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMapClickListener(this)
        googleMap.setOnMyLocationClickListener { location ->

        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(-31.90, 115.86), 4f))

    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun setupLocation(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
    }

    override fun onDestroyView() {
        viewModel.clearState()
        map?.clear()
        map = null
        touchMarker = null
        binding.mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        MapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    override fun handleProgress(isProgress: Boolean) {}

    private var isFabShowing = true

    private fun showFab() {
        if (!isFabShowing) {
            isFabShowing = true
            binding.fab.animate().translationY(0f).start()
        }
    }

    private fun hideFab() {
        if (isFabShowing) {
            isFabShowing = false
            val point = Point()
            activity.getWindow().getWindowManager().getDefaultDisplay().getSize(point)
            val translation = binding.fab.y - point.y
            binding.fab.animate().translationYBy(-translation).start()
        }
    }

}

