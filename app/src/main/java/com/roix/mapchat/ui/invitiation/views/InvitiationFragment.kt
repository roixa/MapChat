package com.roix.mapchat.ui.invitiation.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentInvitiationBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.invitiation.viewmodels.InvitiationViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class InvitiationFragment : BaseDatabindingFragment<InvitiationViewModel, FragmentInvitiationBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_invitiation

}

