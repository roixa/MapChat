package com.roix.mapchat.ui.new_group.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentNewGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.new_group.viewmodels.NewGroupViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class NewGroupFragment : BaseDatabindingFragment<NewGroupViewModel, FragmentNewGroupBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_new_group

}

