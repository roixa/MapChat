package com.roix.mapchat.ui.map.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentMapBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.map.viewmodels.MapViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class MapFragment : BaseDatabindingFragment<MapViewModel, FragmentMapBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_map

}

