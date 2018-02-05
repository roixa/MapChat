package com.roix.mapchat.ui.map.views

import android.Manifest
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentMapBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.map.viewmodels.MapViewModel
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
    var cameraLocation= LatLng(-31.90, 115.86)

    override fun setupBinding() {
        super.setupBinding()
        binding.fab.setOnClickListener {

        }

        binding.mapView.onCreate(arguments)
        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.mapView.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        MapFragmentPermissionsDispatcher.setupLocationWithCheck(this, googleMap)
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMapClickListener(this)
        googleMap.setOnMyLocationClickListener { location ->
            cameraLocation=LatLng(location.latitude,location.longitude)
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                cameraLocation, 4f))

    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun setupLocation(googleMap: GoogleMap) {
        googleMap.setMyLocationEnabled(true)
        map = googleMap
    }

    override fun onMapClick(latLng: LatLng?) {
        if (touchMarker == null) {
            touchMarker = map!!.addMarker(MarkerOptions().position(latLng!!))
        } else
            touchMarker!!.setPosition(latLng!!)


    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    override fun getLayoutId(): Int = R.layout.fragment_map


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        map?.clear()
        map=null
        touchMarker=null
        binding.mapView.onDestroy()
        super.onDestroyView()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        MapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }


}

