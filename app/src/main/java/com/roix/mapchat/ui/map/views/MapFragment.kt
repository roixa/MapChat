package com.roix.mapchat.ui.map.views

import android.Manifest
import android.app.AlertDialog
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
import com.roix.mapchat.data.repositories.icons.models.IconItem
import com.roix.mapchat.databinding.DialogCreateMarkerBinding
import com.roix.mapchat.databinding.FragmentMapBinding
import com.roix.mapchat.databinding.IconItemBinding
import com.roix.mapchat.ui.common.adapters.BaseObservableAdapter
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.map.viewmodels.MapViewModel
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
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

    override fun getLayoutId(): Int = R.layout.fragment_map

    override fun setupUi() {
        super.setupUi()
        val rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.activeGroup.sub {
            viewModel.onGetCurrentGroup(it)
        }
        viewModel.touchMarkerPos.sub {
            if(it==null)return@sub
            if (touchMarker == null) {
                touchMarker = map?.addMarker(MarkerOptions().position(it))
            } else {
                touchMarker?.setPosition(it)
            }
        }
    }

    override fun setupBinding() {
        super.setupBinding()
        binding.fab.setOnClickListener {
            showCreateMarkerDialog()
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
        return true
    }

    private fun showCreateMarkerDialog() {
        val dialogBinding = DialogCreateMarkerBinding.inflate(LayoutInflater.from(activity),
                null,
                false)
        dialogBinding.viewmodel = viewModel
        dialogBinding.rv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = BaseObservableAdapter<IconItem, IconItemBinding>(viewModel.icons, R
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
                    viewModel.onClickedCreateMarker()
                }).show()

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

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun setupLocation(googleMap: GoogleMap) {
        googleMap.setMyLocationEnabled(true)
    }


    override fun onDestroyView() {
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

    //TODO maybe remove this code
    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        MapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    override fun handleProgress(isProgress: Boolean) {}

}

